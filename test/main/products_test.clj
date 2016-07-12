(ns main.products-test
  (:require [clojure.test :refer :all]
            [main.fixtures :as fixtures]
            [ring.mock.request :as mock]
            [main.api :refer [app]]
            [main.measure-test :as measure-test]
            [products.queries :as queries]
            [cheshire.core :refer [generate-string]]))

(use-fixtures :each fixtures/products-test-fixture)

(def first-product {:id 1 :name "Млеко" :measure 2})
(def second-product {:id 2 :name "Яйко" :measure 1})

(deftest product-create
  (testing "CREATE"
    (let [response (app
                     (mock/content-type
                       (mock/body
                         (mock/request :post "/products")
                         (generate-string first-product))
                       "application/json"
                       ))]
      (is (= (:status response) 201))
      (is (= (:body response) (generate-string (queries/get-product first-product)))))))

(deftest product-read
  (testing "READ"
    (queries/create-product first-product)
    (queries/create-product second-product)
    (let [response (app (mock/request :get "/products"))]
      (is (= (:status response) 200))
      (is (= (:body response) (generate-string [first-product second-product]))))))

(deftest product-update
  (testing "UPDATE"
    (queries/create-product first-product)
    (let [response (app
                     (mock/content-type
                       (mock/body
                         (mock/request :put "/products/1")
                         (generate-string {:name "Яйко" :measure 1}))
                       "application/json"
                       ))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"id\":1,\"name\":\"Яйко\",\"measure\":1}")))))

(deftest measures-delete
  (testing "DELETE"
    (queries/create-product first-product)
    (let [response (app
                     (mock/request :delete "/products/1"))]
      (is (= (:status response) 204))
      (is (= (:body response) "null")))))

