(ns schema.server
  (:require [schema.api :refer [app]]
            [org.httpkit.server :refer [run-server]])
  (:gen-class))

(defn -main [& args]
  (let [port 3000]
    (println "Starting server at port " port)
    (run-server app {:port port})))
