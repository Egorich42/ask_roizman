(ns ask-roizman.core
(:require
 [ask-roizman.utils :as utils]
 [ask-roizman.handlers :as handlers]
 [org.httpkit.server :as server]
 [compojure.core :refer :all]
 [org.httpkit.client :as http]
 [clojure.string :as str]
 [clojure.data.json :as json]
 [compojure.route :as route]
 [ask-roizman.daq :as daq]
 ))

;; LINK -http://clojureelasticsearch.info/articles/getting_started.html#updating-documents


(defn extract-sentences [doc-id]
  (get-in (daq/get-single-doc daq/index-info (str doc-id)) [:_source :sentences]))


(def roizman-says (utils/get-random-sentence (extract-sentences "words")))

(prn roizman-says)

;; (create-doc index-info "words" roizman-sentences)

;; (update-doc index-info "words" roizman-sentences)



;; WORK WITH ELASTIC THOUGH HTTP-KIT

;; (def elastic-roizman "http://localhost:9200/peoples_words/roizman/1" )

;; (defn http-from-elastic
;;   [elastic-adress]
;;     (:body @(http/get elastic-adress)))


;; (defn data-from-elastic
;;   ;; (prn(get-in (json/read-str response) ["_source"]) )
;;   [elastic-response, keys-list]
;;   (get-in (json/read-str elastic-response) keys-list) )

;;(prn (data-from-elastic (http-from-elastic elastic-roizman) ["_source" "sentences"]))



;; (def roizman-says (utils/get-random-sentence roizman-sentences))

;; - вытащить текст интевью/стихов/поста
;; - вытащить из него только слова ройзмана.
;; - разбить текст на предложения и загнать их в массив.
;; - массив хранть в памяти, потом,  в базе - кауч либо монго.
;; - по тригеру выдается рандомная фраза из массива
;; https://medium.com/swlh/building-a-rest-api-in-clojure-3a1e1ae096e


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; (prn roizman-says)
  ;; (server/run-server small-app {:port 8080})
  ;;(server/run-server #'app-routes {:port 8080})
  (println "Hello, World!"))
 
