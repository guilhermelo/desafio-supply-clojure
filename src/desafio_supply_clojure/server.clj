(ns desafio_supply_clojure.server
  (:require [reitit.ring :as ring]
            [reitit.coercion.malli]
            [reitit.ring.malli]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.coercion :as coercion]
            [reitit.dev.pretty :as pretty]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.parameters :as parameters]
            [ring.adapter.jetty :as jetty]
            [muuntaja.core :as m]
            [malli.util :as mu]
            [sci.core]
            [desafio-supply-clojure.fazenda.controller :refer [fazenda-controller]]
            [desafio-supply-clojure.talhao.controller :refer [talhao-controller]]))

(def app
  (ring/ring-handler
    (ring/router

      [["/swagger.json"
        {:get {:no-doc  true
               :swagger {:info {:title "Desafio Supply em Clojure"}
                         :tags [{:name "Fazendas", :description "Api de Fazendas"}
                                {:name "Talhões", :description "Api de Talhões"}]}
               :handler (swagger/create-swagger-handler)}}]

       fazenda-controller
       talhao-controller]

      {:exception pretty/exception
       :data      {:coercion   (reitit.coercion.malli/create
                                 {:error-keys       #{#_:type :coercion :in :schema :value :errors :humanized #_:transformed}
                                  :compile          mu/closed-schema
                                  :strip-extra-keys true
                                  :default-values   true
                                  :options          nil})
                   :muuntaja   m/instance
                   :middleware [swagger/swagger-feature
                                parameters/parameters-middleware
                                muuntaja/format-negotiate-middleware
                                muuntaja/format-response-middleware
                                exception/exception-middleware
                                muuntaja/format-request-middleware
                                coercion/coerce-response-middleware
                                coercion/coerce-request-middleware]}})
    (ring/routes
      (swagger-ui/create-swagger-ui-handler
        {:path   "/"
         :config {:validatorUrl     nil
                  :operationsSorter "alpha"}})
      (ring/create-default-handler))))

(defn start []
  (jetty/run-jetty #'app {:port 3000, :join? false})
  (println "Servidor rodando"))

(comment
  (start))