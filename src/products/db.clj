(ns products.db
  (:require
    [environ.core :refer [env]]
    [clojure.java.jdbc :as jdbc]))

(def db-spec (env :database-url))

(defn get-product-sql [record]
  (let [id (record :id)]
    (first
      (jdbc/query db-spec ["select id,name,measure from products where id=?::integer" id]))))

(defn get-all-product-sql [data]
  (jdbc/query db-spec ["select id,name,measure from products"]))

(defn create-product-sql [record]
  (jdbc/insert! db-spec :products record))

(defn update-product-sql [record]
  (let [id (record :id)
        name (record :name)
        measure (record :measure)]
    (jdbc/update! db-spec :products {:name name :measure measure} ["id = ?::integer" id])))

(defn delete-product-sql [record]
  (let [id (record :id)]
    (jdbc/delete! db-spec :products ["id = ?::integer" id])))