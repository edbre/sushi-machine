(ns other.isushi-scrape
  (:require [net.cgrand.enlive-html :as e])
  (:import java.net.URL))

(def urls ["http://www.isushi.no/mix/"
           "http://www.isushi.no/kombo-4/"
           "http://www.isushi.no/maki-box/"
           "http://www.isushi.no/maki/"
           "http://www.isushi.no/nigiri/"
           "http://www.isushi.no/sashimi/"
           "http://www.isushi.no/smaretter/"
           "http://www.isushi.no/ekstra/"])

(defn pp [x]
  (->> x
       (re-seq #"(\d+),-")
       (first)
       (second)))

(defn- parse-price [texts]
  (->> texts
       (map (partial filter string?))
       (mapcat (partial map pp))
       (filter identity)
       (map read-string)))

(defn parse-url [url]
  (let [food (-> (URL. url)
                 (e/html-resource)
                 (e/select [:div.one-quarter :div.wpb_wrapper]))
        names (->> (e/select food [:h4 :span])
                   (map :content)
                   (map first)
                   (map clojure.string/trim))
        prices (->> (e/select food [:p])
                    (map :content)
                    (parse-price))]
    (map (fn [n p] {:name n :price p}) names prices)))


(let [parsed (mapcat parse-url urls)
      sorted (sort-by :name parsed)
      pretty (with-out-str (clojure.pprint/pprint sorted))]
  (spit "isushi.edn" pretty))
