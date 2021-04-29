(ns desafio-supply-clojure.talhao.model
  (:require [reitit.coercion.malli]
            [malli.core :as m]
            [malli.core :as me]))

(def TalhaoRequest [:map
                    [:codigo string?]
                    [:area-em-hectare decimal?]
                    [:safra int?]
                    [:estimativa-produtividade decimal?]
                    [:fazenda-id string?]])

(def UpdateTalhaoRequest [:map
                          [:area-em-hectare decimal?]
                          [:estimativa-produtividade decimal?]])

(def TalhaoResponse [:or [:map
                          [:id uuid?]
                          [:codigo string?]
                          [:area-em-hectare decimal?]
                          [:safra int?]
                          [:estimativa-produtividade decimal?]
                          [:fazenda-id uuid?]]
                     [:map [:message string?]]])