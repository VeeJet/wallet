(ns measures.api
  (:require [compojure.core :refer :all]
            [measures.queries :as queries]))

(defn create-measure [req] (queries/create-measure (req :body)))
(defn update-measure [data] (queries/update-measure data))
(defn delete-measure [data] (queries/delete-measure data))
(defn get-all-measures [data] (queries/get-all-measures data))
(defn get-param-from-request [request]
  {:name (get-in request [:body :name]) :id (get-in request [:params :id])})

(defroutes measures_routes
           (context "/measures" []
             (GET "/" req {:body (get-all-measures req)})
             (POST "/" req {:body (create-measure req) :status 201})
             (PUT "/:id" req {:body (update-measure (get-param-from-request req)) })
             (DELETE "/:id" req {:body (do (delete-measure (get-param-from-request req)) nil) :status 204})))




