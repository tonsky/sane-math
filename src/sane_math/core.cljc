(ns sane-math.core
  (:require
    [clojure.math])
  #?(:cljs
     (:require-macros
       [sane-math.core :refer math])))

(def mappings
  '{** clojure.math/pow
    ×  *
    ÷  /
    −  -})

(def precedences
  '{<  1
    >  1
    <= 1
    >= 1
    -  2
    +  2
    *  3
    /  3
    clojure.math/pow 4})

(defn pack [result op]
  (let [x  (peek result)
        y  (peek (pop result))
        z  (list op x y)]
    (conj (pop (pop result)) z)))

(defn normalize [forms]
  (loop [result ()
         ops    []
         forms  (reverse forms)]
    (let [[form & forms'] forms
          form       (mappings form form)
          precedence (precedences form)]
      (cond
        ;; nothing left
        (and (empty? forms) (empty? ops))
        (first result)
        
        ;; no forms, extra ops left
        (empty? forms)
        (recur (pack result (peek ops)) (pop ops) forms)
      
        ;; unary minus/plus
        (and
          (contains? '#{- +} form)
          (contains? '#{- + * / clojure.math/pow nil} (first forms')))
        (recur (conj (pop result) (list '- (peek result))) ops forms')
        
        ;; parentheses
        (list? form)
        (recur (conj result (normalize form)) ops forms')
        
        ;; value (number/symbol)
        (nil? precedence)
        (recur (conj result form) ops forms')
      
        ;; operator with lower precedence
        (and
          (precedences (peek ops))
          (< precedence (precedences (peek ops))))
        (recur (pack result (peek ops)) (pop ops) forms)
      
        ;; operator with same/higher precedence
        :else
        (recur result (conj ops form) forms')))))

#?(:clj
   (defmacro math [& forms]
     (normalize forms)))
