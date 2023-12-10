(ns day-04
  (:require [clojure.string :as string]
            [clojure.set :as cset]
            [clojure.math :refer [pow]]
            [utils :refer [print-solution]]))

(def example-input
  ["Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
   "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
   "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
   "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"
   "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
   "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"])


(def input
  (-> "data/day_04.txt"
      slurp
      string/split-lines))

(defn eval-line [s]
  (let [count-common (->> s
                          (re-find #".*: ([^|]*)\-*\|\s*(.*)")
                          (drop 1)
                          (map #(into #{} ( string/split % #"\s+")))
                          (apply cset/intersection)
                          (count))]
    (if (= count-common 0)
      0
      (int (pow 2 (dec count-common))))))


(defn solve [input]
  (apply + (map eval-line input)))


;; (print-solution 1 (solve example-input))
(print-solution 1 (solve input))
