(ns desafio-supply-clojure.fazenda.model
  (:require [reitit.coercion.malli]
            [malli.core :as m]
            [malli.error :as me]))


(def validador-nome [:fn {:error/fn '(fn [{:keys [valor]} _] "Nome deve ser um texto e menor que 200 caracteres")}
                     '(fn [nome] (and (string? nome) (< nome 200)))])

(defn- somatorio
  ([pesos numeros-cnpj] (somadora pesos numeros-cnpj 0))
  ([pesos numeros-cnpj total]
   (if (empty? pesos)
     total
     (somatorio (rest pesos) (rest numeros-cnpj) (+ total (first pesos) (first numeros-cnpj))))))

(defn- digito-verificador [pesos digitos-cnpj]
  (let [soma (somatorio pesos digitos-cnpj)
        resto-divisao (rem soma 11)]
    (prn soma)
    (if (< resto-divisao 2)
      0
      (- 11 resto-divisao))))

(defn- descobre-primeiro-digito [digitos-cnpj]
  (let [pesos [5 4 3 2 9 8 7 6 5 4 3 2]]
    (digito-verificador pesos digitos-cnpj)))

(descobre-primeiro-digito [1 1 4 4 4 7 7 7 0 0 0 1 6 1])

(defn- descobre-segundo-digito [digitos-cnpj]
  (let [pesos [6 5 4 3 2 9 8 7 6 5 4 3 2]]
    (digito-verificador pesos digitos-cnpj)))

(defn cnpj? [cnpj]
  (let [numeros-cnpj (map #(Integer/parseInt %) (re-seq #"\d" cnpj))
        primeiro-digito (descobre-primeiro-digito numeros-cnpj)
        segundo-digito (descobre-segundo-digito (conj numeros-cnpj primeiro-digito))
        primeiro-digito-cnpj (first (reverse numeros-cnpj))
        segundo-digito-cnpj (second (reverse numeros-cnpj))]
    (= [primeiro-digito segundo-digito] [primeiro-digito-cnpj segundo-digito-cnpj])))

(cnpj? "11.444.777/0001-61")

(def FazendaRequest [:map
                     [:nome validador-nome]
                     [:cnpj string?]
                     [:cidade string?]
                     [:estado string?]
                     [:logradouro string?]])

(def UpdateFazendaRequest [:map
                           [:nome string?]])

(def FazendaResponse [:map
                      [:id string?]
                      [:nome string?]
                      [:cnpj string?]
                      [:cidade string?]
                      [:estado string?]
                      [:logradouro string?]])






