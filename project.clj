(defproject sushi-machine "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.9.1"]
            [lein-environ "1.0.0"]
            [lein-cljsbuild "1.0.4"]
            [lein-figwheel "0.2.3-SNAPSHOT"]]
  :min-lein-version "2.0.0"
  :ring {:handler sushi-machine.server/handler
         :port 3003}
  :uberjar-name "sushi-machine.jar"
  :main sushi-machine.server
  :profiles {:uberjar {:aot :all}
             :production {:env {:production true}}
             :dev {:env {:database-url "postgres://myapp:dbpass@127.0.0.1:15432/sushi"}
                   :dependencies [[ring/ring-mock "0.2.0"]]}
             :test {:env {:database-url "postgres://myapp:dbpass@127.0.0.1:15432/sushitest"}}}
  :source-paths ["src" "src-cljs"]
  :cljsbuild {:builds [{:id "frontend"
                        :source-paths ["src-cljs"]
                        :compiler {:output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js/out"
                                   :preamble ["reagent/react.js"]
                                   :optimizations :none
                                   :source-map true
                                   :pretty-print true}}]}
  :figwheel {:http-server-root "public"
             :css-dirs ["resources/public/css"]
             :ring-handler sushi-machine.server/handler
             :nrepl-port 7888}
  :dependencies [[cljs-http "0.1.26"]
                 [compojure "1.3.1"]
                 [enlive "1.1.5"]
                 [environ "1.0.0"]
                 [figwheel "0.2.3-SNAPSHOT"]
                 [liberator "0.12.2"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2850"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.postgresql/postgresql "9.3-1102-jdbc41"]
                 [reagent "0.5.0-alpha3"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [yesql "0.4.0"]])
