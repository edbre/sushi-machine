(ns sushi-machine.frontend.main
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [figwheel.client :as fw]
            [reagent.core :as reagent :refer [atom]]
            [cljs-http.client :as http]
            [cljs.core.async :as async :refer [<!]]))

(defonce state (atom {:view :main}))

(enable-console-print!)

(defn hello-world []
  [:div {:class "row"}
    [:div {:class "col-lg-12"}
     [:h1 "Hello world!"]]])

(defn main-view []
  )

(defn app []
  [:div {:class "container"}
   (hello-world)
   (main-view)])

(defn fetch-food []
  (http/get "/food/isushi" {:accept "application/edn"}))

(go (prn (-> (<! (fetch-food)))))

(defn ^:export run []
  (reagent/render [app] (js/document.getElementById "app")))

(run)

(fw/start {:on-jsload (fn [] (print "reloaded"))})
