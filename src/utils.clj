(ns utils
  (:require
   [clojure.string :as string]))

(defmacro print-solution [part out]
  `(println (string/upper-case (ns-name *ns*)) "Part" ~part ":" ~out))
