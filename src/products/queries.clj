(ns products.queries
  (:require
    [environ.core :refer [env]]
    [products.db :as db]))

(defn get-all-product [data]
(db/get-all-product-sql data))

(defn get-product [data]
  (db/get-product-sql data))

(defn create-product [data]
  (let [measure (first (db/create-product-sql data))]
    (db/get-product-sql measure)))

(defn update-product [data]
  (db/update-product-sql data)
  (db/get-product-sql data))

(defn delete-product [data]
  (db/delete-product-sql data))


