(ns desafio-supply-clojure.talhao.controller
  (:require [desafio-supply-clojure.talhao.model :refer [TalhaoResponse TalhaoRequest]]
            [desafio-supply-clojure.talhao.db :as db]))

(def talhao-controller
  ["/talhoes"
   {:swagger {:tags ["Talhões"]}}

   [""
    {:get  {:summary    "Busca todos os talhões"
            :parameters {:query [:map [:filtro {:optional ""} string?]]}
            :responses  {200 {:body [:vector TalhaoResponse]}}
            :handler    (fn [{{{:keys [filtro]} :query} :parameters}]
                          {:status 200
                           :body  (db/talhoes-cadastrados filtro) })}

     :post {:summary    "Insere um novo talhão"
            :parameters {:body TalhaoRequest}
            :responses  {201 {:body TalhaoResponse}}
            :handler    (fn [{{:keys [body]} :parameters}]
                          {:status 201
                           :body   (db/cadastrar! body)})}}]
   ["/:id"
    {:get {:summary "Busca talhão por id"
           :parameters {:path [:map [:id string?]]}
           :responses {200 {:body [:vector TalhaoResponse]}}
           :handler (fn [{{{:keys [id]} :path} :parameters}]
                      {:status 200
                       :body (db/talhao-por-id id)})}}]])
