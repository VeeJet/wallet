(ns purchases.pg
  (:require [cheshire.core :refer [parse-string generate-string]]
			[clojure.java.jdbc :as jdbc])
  (:import [org.postgresql.util PGobject]
           [java.sql]))

(defn extend-db-driver []
  (extend-protocol jdbc/ISQLValue
	clojure.lang.IPersistentMap
	(sql-value [value]
	  (doto (PGobject.)
		(.setType "json")
		(.setValue (generate-string value)))))

  (extend-protocol jdbc/IResultSetReadColumn
    java.sql.Array
    (result-set-read-column [val _ _]
      (into [] (.getArray val)))

    org.postgresql.util.PGobject
      (result-set-read-column [val rsmeta idx]
        (let [colType (.getColumnTypeName rsmeta idx)]
          (if (or (= colType "json")
            (= colType "jsonb"))
            (parse-string (.getValue val) true) val)))))
