(ns measures.db
  (:require
    [environ.core :refer [env]]
    [clojure.java.jdbc :as jdbc]))

(def db-spec (env :database-url))

(defn get-measure-sql [record]
  (let [id (record :id)]
    (first
      (jdbc/query db-spec ["select id,name from measures where id=?::integer" id]))))

(defn get-all-measures-sql [data]
  (jdbc/query db-spec ["select id,name from measures"]))

(defn create-measure-sql [record]
  (jdbc/insert! db-spec :measures record))

(defn update-measure-sql [record]
  (let [id (record :id)
        name (record :name)]
    (jdbc/update! db-spec :measures {:name name} ["id = ?::integer" id])))

(defn delete-measure-sql [record]
  (let [id (record :id)]
    (jdbc/delete! db-spec :measures ["id = ?::integer" id])))