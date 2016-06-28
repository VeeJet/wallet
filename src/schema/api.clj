(ns schema.api
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer [parse-string generate-string]]))



(defn wrap-json [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc response
        :headers {"Content-Type" "application/json; charset=utf-8"}))))


;; Application
(def app
  (wrap-json
    (routes
      (GET "/" req {:body "Hello world"})
      (route/not-found {:body {:message "Not Found"}}))))
