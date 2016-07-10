(ns main.fixtures
  (:require
    [environ.core :refer [env]]
    [clojure.java.jdbc :as jdbc]))

(def db-spec (env :database-url))

(defn measure-test-fixture [f]
  (jdbc/db-do-commands db-spec
                       (jdbc/create-table-ddl :measures
                                              [:name "varchar(32)"]
                                              [:id :serial]))
  (f)
  (jdbc/db-do-commands db-spec
                       (jdbc/drop-table-ddl :measures))
  )