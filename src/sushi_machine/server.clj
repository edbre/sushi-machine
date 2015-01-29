(ns sushi-machine.server
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [compojure.core :refer [routes ANY]]
            [environ.core :refer [env]]
            [liberator.core :refer [resource defresource]]
            [liberator.dev :refer [wrap-trace]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]])
   (:gen-class :main true))

(def system {:db (env :database-url)
             :port (or (env :port) 3002)})

(def test-system {:db "postgres://myapp:dbpass@127.0.0.1:15432/sushitest"})

(def default-media-types ["text/html"
                          "text/csv"
                          "text/plain"
                          "application/json"
                          "application/edn"
                          "application/clojure"])

(def food-servers {:isushi (-> "isushi.edn"
                               io/resource
                               io/file
                               slurp
                               edn/read-string)})

(defresource food [system id]
  :available-media-types default-media-types
  :exists? (fn [_] (contains? food-servers (keyword id)))
  :handle-ok (fn [_] (food-servers (keyword id))))

(defresource example [system]
  :available-media-types default-media-types
  :handle-ok "Hello world!")

(defn ->router [system]
  (routes (ANY "/" [] (example system)))
  (routes (ANY "/food/:id" [id] (food system id))))

(defn ->handler [system]
  (let [router (->router system)]
    (-> router
        wrap-params)))

(def handler (-> (->handler system)
                 (wrap-trace :ui :header)))

(def test-handler (->handler test-system))

(defn -main [& [port]]
  (prn "Listening on port" (:port system))
  (let [port (Integer. (:port system))]
    (jetty/run-jetty #'handler {:port port :join? false})))
