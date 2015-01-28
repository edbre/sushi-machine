(ns sushi-machine.server-test
  (:require [sushi-machine.server :refer :all]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]))

(defmacro req [& args]
  `(test-handler (mock/request ~@args)))

(deftest brille-test
  (is (= (:body (req :get "/"))
         "This is an example")))
