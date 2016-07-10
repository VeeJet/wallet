(ns main.measure-test
  (:require [clojure.test :refer :all]
            [main.fixtures :as fixtures]
            [ring.mock.request :as mock]
            [main.api :refer [app]]
            [main.database :as db]
            [cheshire.core :refer [generate-string]]))

(use-fixtures :each fixtures/measure-test-fixture)

(def first-measure {:id 1 :name "шт."})
(def second-measure {:id 2 :name "кг."})

(deftest test-app
  (testing "CREATE"
    (let [response (app
                     (mock/content-type
                       (mock/body
                         (mock/request :post "/measures")
                         (generate-string first-measure))
                       "application/json"
                       ))]
      (is (= (:status response) 201))
      (is (= (:body response) (generate-string (db/get-measure first-measure)))))))

(deftest measures-read
  (testing "READ"
    (db/create-measure first-measure)
    (db/create-measure second-measure)
    (let [response (app (mock/request :get "/measures"))]
      (is (= (:status response) 200))
      (is (= (:body response) (generate-string [first-measure second-measure]))))))

(deftest measures-update
  (testing "UPDATE"
    (db/create-measure first-measure)
    (let [response (app
                     (mock/content-type
                       (mock/body
                         (mock/request :put "/measures/1")
                         (generate-string {:name "кг."}))
                       "application/json"
                       ))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"id\":1,\"name\":\"кг.\"}")))))

(deftest measures-delete
  (testing "DELETE"
    (db/create-measure first-measure)
    (let [response (app
                     (mock/request :delete "/measures/1"))]
      (is (= (:status response) 204))
      (is (= (:body response) "null")))))

