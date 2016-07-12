(ns main.api
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer [parse-string generate-string]]
            [measures.api :refer [measures_routes]]
            [products.api :refer [products_routes]]))


;; Middleware
(defn read-string* [string]
  (try
    (read-string string)
    (catch Exception e
      string)))

(defn string-to-keywords [req]
  (into {} (for [[_ k v] (re-seq #"([^&=]+)=([^&]+)" req)]
             [(keyword k) v])))

(defn typecheck [d]
  (into {} (map (fn [[k v :as e]]
                  (let [new-val (read-string* v)]
                    (if (-> new-val symbol?)
                      e [k new-val])))
                d)))

(defn extend-request [req]
  (let [method (:request-method req)
        body (:body req)
        query-string (:query-string req)
        json (cond
               (and (= method :get) query-string) (string-to-keywords query-string) ;квери стринг который трансформируем в JSON
               :else (merge (typecheck (:route-params req)) (or (and body (-> body slurp (parse-string true))) {})))]
    (assoc req :json-data json)))

(defn wrap-json [handler]
  (fn [request]
    (let [req (extend-request request)
          response (handler req)
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
      products_routes
      measures_routes
      main_routes)))
