(ns purchases.api
  (:require [compojure.core :refer :all]
            [purchases.queries :as purchases]))

(defn create-purchase [req] (purchases/create-purchase (req :body)))
(defn update-purchase [data] (purchases/update-purchase data))
(defn delete-purchase [data] (purchases/delete-purchase data))
(defn get-all-purchase [data] (purchases/get-all-purchases data))
(defn get-param-from-request [request]
  {:date (get-in request [:body :date])
   :list (get-in request [:body :list])
   :id (get-in request [:params :id])})

(defroutes purchases_routes
           (context "/purchases" []
             (GET "/" req {:body (get-all-purchase req)})
             (POST "/" req {:body (create-purchase req) :status 201})
             (PUT "/:id" req {:body (update-purchase (get-param-from-request req)) })
             (DELETE "/:id" req {:body (do (delete-purchase (get-param-from-request req)) nil) :status 204})))




