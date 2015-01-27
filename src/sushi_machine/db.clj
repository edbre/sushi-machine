(ns sushi-machine.db
  (:require [yesql.core :refer [defqueries]]))

(def db-conn (or (System/getenv "DATABASE_URL")
                 "postgresql://myapp:dbpass@127.0.0.1:15432/myapp"))

(defqueries "sql/queries.psql")

(defn create-tables! [db-conn]
  (try
    (drop-events-table! db-conn)
    (catch Exception e)
    (finally (create-events-table! db-conn))))

(comment (create-tables! db-conn))
