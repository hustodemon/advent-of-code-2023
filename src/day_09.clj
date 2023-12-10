(ns day-09
  (:require
   [clojure.string :as string]
   [utils :refer [print-solution]]))

(def example-input
  "0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45")


(def input (slurp "data/day_09.txt"))


(defn parse-line [line]
  (->> (string/split line #"\s")
       (mapv parse-long)))


(defn parse-input [input]
  (->> input
       (string/split-lines)
       (mapv parse-line)))



(defn next-iteration [dataset]
  (mapv - (rest dataset) dataset))


(defn compute-last [dataset]
  (if (every? zero? dataset)
    0
    (+ (last dataset) (compute-last (next-iteration dataset)))))


(defn compute-all [parsed-input]
  (apply + (map compute-last parsed-input)))


;;(println (compute-all (parse-input example-input)))
(print-solution 1 (compute-all (parse-input input)))
;;(println (compute-all (mapv reverse (parse-input example-input))))
(print-solution 2 (compute-all (mapv reverse (parse-input input))))
