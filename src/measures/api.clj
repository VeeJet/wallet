(ns measures.api
  (:require [compojure.core :refer :all]
            [main.database :as db]))

(defn create-measure [req] (db/create-measure (req :json-data)))
(defn update-measure [data] (db/update-measure data))
(defn delete-measure [data] (db/delete-measure data))
(defn get-all-measures [data] (db/get-all-measures data))
(defn get-param-from-request [request]
  {:name (get-in request [:json-data :name]) :id (get-in request [:params :id])})

(defroutes measures_routes
           (context "/measures" []
             (GET "/" req {:body (get-all-measures req)})
             (POST "/" req {:body (create-measure req) :status 201})
             (PUT "/:id" req {:body (update-measure (get-param-from-request req)) })
             (DELETE "/:id" req {:body (do (delete-measure (get-param-from-request req)) nil) :status 204})))




