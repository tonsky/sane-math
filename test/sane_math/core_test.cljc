(ns sane-math.core-test
  (:require
    [sane-math.core :as core]
    [clojure.test :refer [is are deftest testing]]))

(deftest normalize-test
  (are [act exp] (= (quote exp) (core/normalize (quote act)))
    (a + b)             (+ a b)
    (a * b)             (* a b)
    (a + b * c)         (+ a (* b c))
    (a * b + c)         (+ (* a b) c)
    (a + b * c - d)     (- (+ a (* b c)) d)
    (a * (b + c))       (* a (+ b c))
    ((a + b) * c)       (* (+ a b) c)
    (a ** 2)            (clojure.math/pow a 2)
    (1 + a ** 2)        (+ 1 (clojure.math/pow a 2))
    (a ** 2 + 1)        (+ (clojure.math/pow a 2) 1)
    (x * y ** 2)        (* x (clojure.math/pow y 2))
    (x ** 2 * y)        (* (clojure.math/pow x 2) y)
    (x ** 2 ** 3)       (clojure.math/pow (clojure.math/pow x 2) 3)
    ((1 + 2) / (3 - 4)) (/ (+ 1 2) (- 3 4))
    
    ;; unaries
    (- 1)               (- 1)
    (1 + - 2)           (+ 1 (- 2))
    (- 1 + 2)           (+ (- 1) 2)
    (x * - (y + z))     (* x (- (+ y z)))
    (- (y + z))         (- (+ y z))
    
    ;; unicode
    (1 + 2 * 3 / 4 - 5) (- (+ 1 (/ (* 2 3) 4)) 5)
    (1 + 2 × 3 ÷ 4 − 5) (- (+ 1 (/ (* 2 3) 4)) 5)
    
    ;; all together
    (3 + 4 * 2 / (1 - 5) ** 2 ** 3) (+ 3 (/ (* 4 2) (clojure.math/pow (clojure.math/pow (- 1 5) 2) 3)))
    ))

(deftest macro-test
  (is (= 3 (core/math (1 + 2))))
  (is (= 3 (core/math 1 + 2)))
  (let [x 1
        y 2]
    (is (= 3 (core/math (x + y))))
    (is (= 3 (core/math x + y)))))

(deftest data-reader-test
  (is (= 3 #sane-math (1 + 2)))
  (let [x 1
        y 2]
    (is (= 3 #sane-math (x + y)))))

; (test/test-ns *ns*)
; (test/run-test-var #'var)
