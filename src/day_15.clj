(ns day-15
  (:require
   [clojure.string :refer [split trim]]
   [utils :refer [print-solution]]))


(defn compute-hash [s]
  (reduce
   (fn [val-acc c] (rem (* 17 (+ val-acc (int c))) 256))
   0
   s))


(defn part-1 [input]
  (->> (split input #",")
   (map compute-hash)
   (apply +)))


(defn- read-input [path]
  (-> path slurp trim))


(print-solution 1 (part-1 (read-input "data/day_15.txt")))
