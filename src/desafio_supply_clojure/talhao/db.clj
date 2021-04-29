(ns desafio-supply-clojure.talhao.db
  (:require [desafio-supply-clojure.config.db :refer [db]]
            [next.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as honey]))

(defn- talhao-formatado [talhao]
  (-> talhao
      (assoc :fazenda-id (:fazenda_id talhao))
      (assoc :estimativa-produtividade (:estimativa_produtividade talhao))
      (assoc :area-em-hectare (:area_em_hectare talhao))
      (dissoc :fazenda_id)
      (dissoc :estimativa_produtividade)
      (dissoc :area_em_hectare)))

(defn talhoes-cadastrados [filtro]
  (->> (jdbc/execute! db (sql/format {:select [:*] :from [:talhao]})
                      {:builder-fn next.jdbc.result-set/as-unqualified-maps})
       (map talhao-formatado)))

(defn talhao-por-id [id]
  (if (uuid? id)
    (->> (jdbc/execute! db (sql/format {:select [:*]
                                        :from   [:talhao]
                                        :where  [:= :id (java.util.UUID/fromString id)]})
                        {:builder-fn next.jdbc.result-set/as-unqualified-maps})
         (map talhao-formatado))
    {:message "ID deve ser um UUID"}))

(defn cadastrar! [talhao]
  (try
    (jdbc/execute! db (sql/format
                        {:insert-into :talhao
                         :columns     [:id :codigo :area-em-hectare :safra :estimativa-produtividade :fazenda-id]
                         :values      [[(java.util.UUID/randomUUID) (:codigo talhao) (:area-em-hectare talhao) (:safra talhao) (:estimativa-produtividade talhao) (java.util.UUID/fromString (:fazenda-id talhao))]]}))
    (catch Exception ex
      (.printStackTrace ex)
      (str "caught exception: " (.getMessage ex)))))



