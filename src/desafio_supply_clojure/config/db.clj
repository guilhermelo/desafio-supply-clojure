(ns desafio-supply-clojure.config.db
  (:require [next.jdbc :as jdbc]))

(def db-config
  {:dbtype   "postgresql"
   :dbname   "desafio"
   :host     "localhost"
   :user     "guilherme"
   :password "guilherme"})

(def db (jdbc/get-datasource db-config))
