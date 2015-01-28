(ns sushi-machine.server
  (:require [compojure.core :refer [routes defroutes ANY]]
            [liberator.core :refer [resource defresource]]
            [compojure.handler :refer [site]]
            [liberator.dev :refer [wrap-trace]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]))

(def system      {:db (env :database-url)
                  :port (or (env :port) 3002)})
(def test-system {:db "postgres://myapp:dbpass@127.0.0.1:15432/sushitest"})

(defresource example [system]
  :available-media-types ["text/plain"]
  :handle-ok "Hello world!")

(defn ->router [system]
  (routes (ANY "/" [] (example system))))

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
