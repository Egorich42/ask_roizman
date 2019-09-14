(ns ask-roizman.utils
(:require
  [clojure.string :as str]))

(def current-dir (.getCanonicalPath (clojure.java.io/file ".")))

(defn extract-from-file
  [file-name]
  (slurp file-name))

(defn text-to-sentences-vector
  [text]
  (str/split text #"\.|\?|\!|\..."))

(defn get-random-sentence
  [sentences-vector]
  (repeatedly 1 #(rand-nth sentences-vector)))


;; - вытащить текст интевью/стихов/поста
;; - вытащить из него только слова ройзмана.
;; - разбить текст на предложения и загнать их в массив.
;; - массив хранть в памяти, потом,  в базе - кауч либо монго.
;; - по тригеру выдается рандомная фраза из массива
