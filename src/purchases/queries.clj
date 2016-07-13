(ns purchases.queries
  (:require
    [environ.core :refer [env]]
    [purchases.db :as db]))


(defn get-all-purchases [data]
  (let [purchases (db/get-all-purchases-sql data)]
    (map #(update % :date
                  (fn [timestamp]
                    (db/timestamp->date-string timestamp db/date-formatter))) purchases)))

(defn get-purchase [data]
  (db/get-purchase-sql data))

(defn create-purchase [data]
  (let [measure (first (db/create-purchase-sql data))]
    (db/get-purchase-sql measure)))

(defn update-purchase [data]
  (db/update-purchase-sql data)
  (db/get-purchase-sql data))

(defn delete-purchase [data]
  (db/delete-purchase-sql data))


