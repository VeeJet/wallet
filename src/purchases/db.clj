(ns purchases.db
  (:require
    [environ.core :refer [env]]
    [purchases.pg :as pg]
    [clj-time.format :as f]
    [clj-time.coerce :as c]
    [clojure.java.jdbc :as jdbc])
  (:import (java.sql Timestamp)))
(pg/extend-db-driver)
(def db-spec (env :database-url))

(defn timestamp->date-string [timestamp formater]
  "Простенький форматер чтоб возвращать строковое представление"
  (let [date-time-yoda (c/from-long timestamp)
        date-string (f/unparse formater date-time-yoda)]

    date-string))

(def date-formatter (f/formatter "yyyy-MM-dd HH:mm:ss"))

(defn get-purchase-sql [record]
  (let [id (record :id)]
    (first (jdbc/query db-spec ["select id,list,date from purchases where id=?::integer" id]))))

(defn get-all-purchases-sql [data]
  (jdbc/query db-spec ["select id,list,date from purchases"]))

(defn create-purchase-sql [record]
  (let [record (assoc record :date (new Timestamp (c/to-long (record :date))))]
    (jdbc/insert! db-spec :purchases record)))

(defn update-purchase-sql [record]
  (let [id (record :id)
        list (record :list)
        date (new Timestamp (c/to-long (record :date)))]
    (jdbc/update! db-spec :purchases {:list list :date date} ["id = ?::integer" id])))

(defn delete-purchase-sql [record]
  (let [id (record :id)]
    (jdbc/delete! db-spec :purchases ["id = ?::integer" id])))