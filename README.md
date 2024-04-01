# Sane Math

Write Clojure/Script mathematical expressions in infix notation, like a normal person!

## Using

Add this to deps.edn:

```clojure
io.github.tonsky/sane-math {:mvn/version "0.1.0"}
```

Require:

```clojure
(require '[sane-math.core :as sane])
```

Use:

```clojure
; Values
(sane/math 1 + 2)             ;; => (+ 1 2)

; Variables
(sane/math x + y * z - t)     ;; => (- (+ x (* y z)) t)

; Parentheses
(sane/math (x + y) * (z - t)) ;; => (* (+ x y) (- z t))

; Power
(sane/math (a + b) ** 2)      ;; => (clojure.math/pow (+ a b) 2)

; Unary minus
(sane/math a + - b)           ;; => (+ a (- b))
```

Same works with reader tag:

```clojure
#sane-math (1 + 2)             ;; => (+ 1 2)

#sane-math (x + y * z - t)     ;; => (- (+ x (* y z)) t)

#sane-math ((x + y) * (z - t)) ;; => (* (+ x y) (- z t))

#sane-math ((a + b) ** 2)      ;; => (clojure.math/pow (+ a b) 2)

#sane-math (a + - b)           ;; => (+ a (- b))
```

## License

Copyright © 2024 Nikita Prokopov

Licensed under [MIT](LICENSE).
