(defproject wallet "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [environ "1.0.2"]
                 [yesql "0.5.2"]
                 [http-kit "2.1.19"]
                 [cheshire "5.6.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-environ "1.0.2"]]
  :main main.server
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]
         :env {:database-url "postgres://test:test@localhost:5432/test"}}})