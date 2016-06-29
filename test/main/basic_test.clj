(ns main.basic-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [main.api :refer [app]]
            [cheshire.core :refer [generate-string]]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) (generate-string "Hello world") ))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))
      (is (= (:body response) (generate-string {:message "Not Found"}) )))))

