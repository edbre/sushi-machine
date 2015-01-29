(ns sushi-machine.server-test
  (:require [sushi-machine.server :refer :all]
            [clojure.test :refer :all]
            [clojure.edn :as edn]
            [ring.mock.request :as mock]))

(defmacro req [& args]
  `(-> (mock/request ~@args)
       (mock/header "accept" "application/edn")
       (mock/content-type "application/edn")
       (test-handler)))

(deftest food-rest-resource

  (testing "food resource returns list of food items with name and price"
    (let [res (req :get "/food/isushi")
          body (edn/read-string (:body res))]
      (is (> (count body) 10))
      (is (contains? (first body) :name))
      (is (contains? (first body) :price))))

  (testing "food resource returns 404 on not found item"
    (let [res (req :get "/food/doesntexist")]
      (is (= (:status res) 404)))))
