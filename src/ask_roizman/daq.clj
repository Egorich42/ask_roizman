(ns ask-roizman.daq
  (:require
   [clojurewerkz.elastisch.rest :as esr]
   [clojurewerkz.elastisch.rest.document :as esd]
   [clojurewerkz.elastisch.query :as query]

   [clojure.string :as str]
   [clojurewerkz.elastisch.rest.response :as esrsp]))




(def index-info {:index-name "peoples_words"
                 :type "roizman"})

(def elastic-adress "http://127.0.0.1:9200")

(def conn (esr/connect elastic-adress))


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

