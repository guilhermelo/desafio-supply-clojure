(defproject desafio-supply-clojure "0.1.0-SNAPSHOT"
  :description "Desafio Supply em Clojure"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [metosin/jsonista "0.2.6"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [metosin/reitit "0.5.12"]
                 [org.postgresql/postgresql "42.1.4"]
                 [seancorfield/next.jdbc "1.1.613"]
                 [honeysql "1.0.444"]
                 [borkdude/sci "0.2.4"]]
  :repl-options {:init-ns desafio_supply_clojure.server}
  :profiles {:dev {:dependencies [[ring/ring-mock "0.3.2"]]}})
