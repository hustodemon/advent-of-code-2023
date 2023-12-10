(ns day-01
  (:require
   [clojure.string :as string]
   [utils :refer [print-solution]]))

(def example-input-1
  ["1abc2"
   "pqr3stu8vwx"
   "a1b2c3d4e5f"
   "treb7uchet"])


(def input
  (->  "data/day_01.txt"
      slurp
      string/split-lines))


;; part 1
(defn part-1 [input]
  (->> input
       (map (partial re-seq #"\d"))
       (map (fn [ds] (parse-long (str (first ds) (last ds)))))
       (reduce +)))

(print-solution 1 (part-1 input))

;; part 2, quite clunky, lol
(def example-input-2
  ["two1nine"
   "eightwothree"
   "abcone2threexyz"
   "xtwone3four"
   "4nineeightseven2"
   "zoneight234"
   "7pqrstsixteen"])


(defn find-first-digit [s]
  (first
   (re-seq #"one|two|three|four|five|six|seven|eight|nine|ten|\d" s)))


(defn find-last-digit [s]
  (string/reverse
    (first (re-seq
            (re-pattern (str (string/reverse "one|two|three|four|five|six|seven|eight|nine|ten") "|\\d"))
            (string/reverse s)))))


(def digit-mapping
  (zipmap ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]
          (iterate inc 1)))


(defn find-digits
  "Find the first and last digit (both 'numeric' and 'word' ones like 'three')."
  [s]
  (vector (find-first-digit s) (find-last-digit s)))


(defn process-line-2 [s]
  (->> s
       (find-digits)
       (map #(get digit-mapping % %))
       (reduce str)
       (read-string)))


(defn part-2 [input]
  (->> input
       (map process-line-2)
       (reduce +)))

;(print-solution 2 (part-2 example-input-2))
(print-solution 2 (part-2 input))
