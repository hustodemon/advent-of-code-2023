(ns day-14
  (:require
   [clojure.string :as string]
   [utils :refer [print-solution]]))


(def example-input
  "O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....
")

(def input (slurp "data/day_14.txt"))


(defn parse-input [input] (string/split-lines input))


(defn sort-column [column]
  (->> (string/split column #"#") ;; groups divided by #
       (map sort)                 ;; sort the groups
       (map reverse)              ;; reverse
       (#(interleave % (repeat "#")))  ;; bring the # back
       (flatten)
       (#(concat % (repeat "#")))
       (take (count column))
       (apply str)))


(defn count-Os [line] (count (re-seq #"O" line)))


(defn part-1 [input]
  (apply +
         (map *
              (->> input
                   parse-input
                   (apply map vector)  ;; transpose
                   (map (partial apply str)) ;; back to strings
                   (map sort-column) ;; shake the bubbles
                   (apply map vector)  ;; transpose back
                   (map (partial apply str)) ;; back to strings
                   (map count-Os)) ;; count the Os per line
              (iterate dec 100))))


;; (print-solution 1 (part-1 example-input))
(print-solution 1 (part-1 input))
