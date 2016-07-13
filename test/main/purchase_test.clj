(ns main.purchase-test
  (:require [clojure.test :refer :all]
            [main.fixtures :as fixtures]
            [ring.mock.request :as mock]
            [main.api :refer [app]]
            [clojure.data.json :as json]
            [purchases.queries :as queries]
            [cheshire.core :refer [generate-string]]))

(use-fixtures :each fixtures/purchases-test-fixture)

(def first-purchase {:id   1
                     :list {
                            :1 {:product 1 :cost 100}}
                     :date "2999-01-01 01:01:01"})
(def second-purchase {:id   2
                      :list {
                             :2 {:product 1 :cost 99}
                             :3 {:product 2 :cost 50}}
                      :date "2000-02-02 01:01:01"})


(deftest purchase-read
  (testing "CREATE"
    (let [response (app
                     (mock/content-type
                       (mock/body
                         (mock/request :post "/purchases")
                         (generate-string first-purchase))
                       "application/json"
                       ))]
      (is (= (:status response) 201))
      (is (= (json/read-str (:body response) :key-fn keyword)
             (queries/get-purchase first-purchase))))))

(deftest purchase-read
  (testing "READ"
    (queries/create-purchase first-purchase)
    (queries/create-purchase second-purchase)
    (let [response (app (mock/request :get "/purchases"))]
      (is (= (:status response) 200))
      (is (= (json/read-str (:body response) :key-fn keyword) [first-purchase second-purchase])))))

(deftest purchase-update
  (testing "UPDATE"
    (queries/create-purchase first-purchase)
    (let [response (app
                     (mock/content-type
                       (mock/body
                         (mock/request :put "/purchases/1")
                         (generate-string second-purchase))
                       "application/json"
                       ))]
      (is (= (:status response) 200))
      (is (= (dissoc (json/read-str (:body response) :key-fn keyword) :date)
             (dissoc (assoc second-purchase :id 1) :date))))))

(deftest purchase-delete
  (testing "DELETE"
    (queries/create-purchase first-purchase)
    (let [response (app
                     (mock/request :delete "/purchases/1"))]
      (is (= (:status response) 204))
      (is (= (:body response) "null")))))

