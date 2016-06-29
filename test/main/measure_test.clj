(ns main.measure-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [main.api :refer [app]]
            [cheshire.core :refer [generate-string]]))

(def first-measure {:id 1 :name "шт."})
(def second-measure {:id 2 :name "кг."})

(deftest test-app
  (testing "CREATE"
    (let [response (app (mock/request :post "/measures"))]
      (is (= (:status response) 201))
      (is (= (:body response) (generate-string first-measure)))))
  (testing "READ"
    (let [response (app (mock/request :get "/measures"))]
      (is (= (:status response) 200))
      (is (= (:body response) (generate-string [first-measure second-measure])))))
  (testing "UPDATE"
    (let [response (app (mock/request :put "/measures/1"))]
      (is (= (:status response) 200))
      (is (= (:body response) (generate-string (assoc first-measure :name (second-measure :name)))))))
  (testing "DELETE"
    (let [response (app (mock/request :delete "/measures/1"))]
      (is (= (:status response) 204))
      (is (= (:body response) "null")))))
