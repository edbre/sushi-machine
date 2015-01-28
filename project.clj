(defproject sushi-machine "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.9.1"]
            [lein-environ "1.0.0"]]
  :min-lein-version "2.0.0"
  :ring {:handler sushi-machine.server/handler
         :port 3001}
  :uberjar-name "sushi-machine.jar"
  :main sushi-machine.server
  :profiles {:uberjar {:aot :all}
             :production {:env {:production true}}
             :dev {:env {:database-url "postgres://myapp:dbpass@127.0.0.1:15432/sushi"}
                   :dependencies [[ring/ring-mock "0.2.0"]]}
             :test {:env {:database-url "postgres://myapp:dbpass@127.0.0.1:15432/sushitest"}}}
  :dependencies [[compojure "1.3.1"]
                 [enlive "1.1.5"]
                 [environ "1.0.0"]
                 [liberator "0.12.2"]
                 [org.clojure/clojure "1.6.0"]
                 [org.postgresql/postgresql "9.3-1102-jdbc41"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [yesql "0.4.0"]])
