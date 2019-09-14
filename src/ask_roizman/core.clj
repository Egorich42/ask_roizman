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
 [clojurewerkz.elastisch.rest :as esr]
 [clojurewerkz.elastisch.rest.document :as esd]
 [clojurewerkz.elastisch.query :as query]
 [clojurewerkz.elastisch.rest.response :as esrsp]
 ))

;; LINK -http://clojureelasticsearch.info/articles/getting_started.html#updating-documents


(def index-info {:index-name "peoples_words"
                 :type "roizman"})

(def elastic-adress "http://127.0.0.1:9200")

(def conn (esr/connect elastic-adress))

(def doc {"username" "Egorich" "age" 25})

(def roizman-sentences
  (let [text (utils/extract-from-file "demo_text.txt")]
    {:sentences (utils/text-to-sentences-vector text)}  ))



;;GET SINGLE DOC FROM BASE (prn (get-single-doc "Roizman"))
(defn get-single-doc
  [{index :index-name type :type} doc-id]
    (esd/get conn index type (str doc-id)))


;;SEARCH SINGLE DOC FROM BASE ON NAME  (prn(search-doc "Roizman"))
(defn
  search-doc [name]
  (esd/search conn "peoples_words" "roizman" :query (query/term "name" (str/lower-case name))))


;; CREATE NEW DOC IN BASE  (create-doc index-info doc)
 (defn create-doc
   [{index :index-name type :type} id new-doc]
     (esd/put conn index type (str id) new-doc))


;; UPDATE SINGLE DOCUMENT (update-doc index-info doc)
(defn update-doc
  [{index :index-name type :type} id current-doc ]
  (esd/replace conn index type (str id) (assoc current-doc "name" "Evgeny")))



(defn extract-sentences [doc-id]
  (get-in (get-single-doc index-info (str doc-id)) [:_source :sentences]))



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
 
