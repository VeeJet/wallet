(ns main.database
  (:require [db.measures :as queries]))


;Mesures
(defn get-all-measures [data]
  (queries/get-all-measures-sql data))

(defn get-measure [data]
  (queries/get-measure-sql data))

(defn create-measure [data]
  (let [measure (first (queries/create-measure-sql data))]
    (queries/get-measure-sql measure)))

(defn update-measure [data]
  (queries/update-measure-sql data)
  (queries/get-measure-sql data))

(defn delete-measure [data]
  (queries/delete-measure-sql data))
