(ns desafio-supply-clojure.fazenda.db
  (:require [desafio-supply-clojure.config.db :refer [db]]
            [next.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as honey]))

(defn fazendas-cadastradas [filtro]
  (->> (jdbc/execute! db (sql/format {:select [:*] :from [:fazenda]})
                      {:builder-fn next.jdbc.result-set/as-unqualified-maps})
       (map (fn [fazenda] (assoc fazenda :id (.toString (:id fazenda)))))))

(defn cadastrar! [fazenda]
  (jdbc/execute! db (sql/format
                      {:insert-into :fazenda
                       :columns     [:id :nome :cnpj :cidade :estado :logradouro]
                       :values      [[(java.util.UUID/randomUUID) (:nome fazenda) (:cnpj fazenda) (:cidade fazenda) (:estado fazenda) (:logradouro fazenda)]]}))

  fazenda)

(defn atualizar! [id fazenda]
  (let [query (-> (honey/update :fazenda)
                  (honey/sset {:nome (:nome fazenda)})
                  (honey/where [:= :id (java.util.UUID/fromString id)])
                  sql/format)]
    (jdbc/execute! db query)))

(defn excluir! [id]
  (let [query (-> (honey/delete-from :fazenda)
                  (honey/where [:= :id (java.util.UUID/fromString id)])
                  sql/format)]
    (jdbc/execute! db query)))