(defproject io.github.tonsky/sane-math "0.0.0"
  :description "Write math expressions in infix (normal) notation"
  :license     {:name "MIT" :url "https://github.com/tonsky/sane-math/blob/master/LICENSE"}
  :url         "https://github.com/tonsky/sane-math"
  :dependencies
  [[org.clojure/clojure "1.11.2"]]
  :deploy-repositories
  {"clojars"
   {:url "https://clojars.org/repo"
    :username "tonsky"
    :password :env/clojars_token
    :sign-releases false}})