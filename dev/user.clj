(ns user
  (:require
    [duti.core :as duti]))

(duti/set-dirs "src" "dev" "test")

(def reload
  duti/reload)

(defn -main [& args]
  (alter-var-root #'*command-line-args* (constantly args))
  (let [{port "--port"} args]
    (duti/start-socket-repl {:port (some-> port parse-long)})))

(defn test-all []
  (require 'sane-math.core-test)
  (reload)
  (duti/test #"sane-math\..*-test"))

(defn -test-main [_]
  (reload {:only #"sane-math\..*-test"})
  (duti/test-exit #"sane-math\..*-test"))
