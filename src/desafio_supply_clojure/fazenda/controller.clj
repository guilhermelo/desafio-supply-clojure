(ns desafio-supply-clojure.fazenda.controller
  (:require [desafio-supply-clojure.fazenda.db :as db]
            [desafio-supply-clojure.fazenda.model :refer :all]))

(def fazenda-controller
  ["/fazendas"
   {:swagger {:tags ["Fazendas"]}}

   [""
    {:get  {:summary    "Busca todas as fazendas"
            :parameters {:query [:map [:filtro {:optional ""} string?]]}
            :responses  {200 {:body [:vector FazendaResponse]}}
            :handler    (fn [{{{:keys [filtro]} :query} :parameters}]
                          {:status 200
                           :body   (db/fazendas-cadastradas filtro)})}

     :post {:summary    "Insere uma nova fazenda"
            :parameters {:body FazendaRequest}
            :responses  {201 {:body FazendaResponse}}
            :handler    (fn [{{:keys [body]} :parameters}]
                          {:status 201
                           :body   (db/cadastrar! body)})}}]

   ["/:id"
    {:put    {:summary    "Atualiza uma fazenda"
              :parameters {:path [:map [:id string?]]
                           :body UpdateFazendaRequest}
              :handler    (fn [{{:keys [path body]} :parameters}]
                            {:status 200
                             :body   (db/atualizar! (:id path) body)})}

     :delete {:summary    "Exclui uma fazenda"
              :parameters {:path [:map [:id string?]]}
              :handler    (fn [{{{:keys [id]} :path} :parameters}]
                            (db/excluir! id)
                            {:status 204})}}]])
