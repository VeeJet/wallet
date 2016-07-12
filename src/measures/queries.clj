(ns measures.queries
  (:require [measures.db :as db]))


;Mesures
(defn get-all-measures [data]
  (db/get-all-measures-sql data))

(defn get-measure [data]
  (db/get-measure-sql data))

(defn create-measure [data]
  (let [measure (first (db/create-measure-sql data))]
    (db/get-measure-sql measure)))

(defn update-measure [data]
  (db/update-measure-sql data)
  (db/get-measure-sql data))

(defn delete-measure [data]
  (db/delete-measure-sql data))
