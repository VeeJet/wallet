(ns products.api
  (:require [compojure.core :refer :all]
            [products.queries :as products]))

(defn create-product [req] (products/create-product (req :json-data)))
(defn update-product [data] (products/update-product data))
(defn delete-product [data] (products/delete-product data))
(defn get-all-product [data] (products/get-all-product data))
(defn get-param-from-request [request]
  {:name (get-in request [:json-data :name])
   :measure (get-in request [:json-data :measure])
   :id (get-in request [:params :id])})

(defroutes products_routes
           (context "/products" []
             (GET "/" req {:body (get-all-product req)})
             (POST "/" req {:body (create-product req) :status 201})
             (PUT "/:id" req {:body (update-product (get-param-from-request req)) })
             (DELETE "/:id" req {:body (do (delete-product (get-param-from-request req)) nil) :status 204})))




