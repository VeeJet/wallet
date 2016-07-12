(ns main.fixtures
  (:require
    [environ.core :refer [env]]
    [clojure.java.jdbc :as jdbc]))

(def db-spec (env :database-url))
;Mesures
(defn create-measures-table []
  (jdbc/db-do-commands db-spec
                       (jdbc/create-table-ddl :measures
                                              [:name "varchar(32)"]
                                              [:id :serial])))
(defn drop-measures-table []
  (jdbc/db-do-commands db-spec
                       (jdbc/drop-table-ddl :measures)))


(defn measure-test-fixture [f]
  (create-measures-table)
  (f)
  (drop-measures-table))

;Products

(defn create-products-table []
  (jdbc/db-do-commands db-spec
                       (jdbc/create-table-ddl :products
                                              [:name "varchar(32)"]
                                              [:measure :int]
                                              [:id :serial])))
(defn drop-products-table []
  (jdbc/db-do-commands db-spec
                       (jdbc/drop-table-ddl :products)))

(defn products-test-fixture [f]
  (create-measures-table)
  (create-products-table)
  (f)
  (drop-measures-table)
  (drop-products-table))