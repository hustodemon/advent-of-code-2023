(ns utils
  (:require [malli.dev :as dev]
            [clojure.string :as string]))

(dev/start!)
(print "profile start")


(defmacro print-solution [part out]
  `(when (= ~part 1) (prn)) ;; spacer
  `(println (string/upper-case (ns-name *ns*)) "Part" ~part ":" ~out))
