(ns measures.api
  (:require [compojure.core :refer :all]
            ))


(defroutes measures_routes
           (context "/measures" []
             (GET "/" [] {:body [{:id 1 :name "шт."} {:id 2 :name "кг."}]})
             (POST "/" [] {:body {:id 1 :name "шт."} :status 201})
             (PUT "/:id" [] {:body {:id 1 :name "кг."}})
             (DELETE "/:id" [] {:body nil :status 204})))




