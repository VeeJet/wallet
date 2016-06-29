(ns main.api
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer [parse-string generate-string]]
            [measures.api :refer [measures_routes]]))


(defn wrap-json [handler]
  (fn [request]
    (let [response (handler request)
          data (generate-string (:body response))]
      (assoc response
        :body data
        :headers {"Content-Type" "application/json; charset=utf-8"}))))

(defroutes main_routes
           (GET "/" [] {:body "Hello world"})
           (route/not-found {:body {:message "Not Found"}}))


;; Application
(def app
  (wrap-json
    (routes
      measures_routes
      main_routes)))
