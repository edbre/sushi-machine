(ns sushi-machine.frontend.main
  (:require [figwheel.client :as fw]
            [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defn hello-world []
  [:h1 "Hello world!"])

(defn ^:export run []
  (reagent/render [hello-world] (js/document.getElementById "app")))

(run)

(fw/start {:on-jsload (fn [] (print "reloaded"))})
