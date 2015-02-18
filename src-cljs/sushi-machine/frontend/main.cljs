(ns sushi-machine.frontend.main
  (:require [figwheel.client :as fw]))

(enable-console-print!)

(fw/start {:on-jsload (fn [] (print "reloaded"))})
