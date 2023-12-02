(ns day-02
  (:require [clojure.string :as string]))


(def example-input
  ["Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
   "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"
   "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
   "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
   "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"])


(def input
  (-> "../data/day_02.txt"
      slurp
      string/split-lines))


(defn find-max-color-usage
  "Find the maximal usage of given color in given game."
  [color game-str]
  (->> game-str
       (re-seq (re-pattern (str "(\\d+) " color)))
       (map (comp read-string second) )
       (apply max)))


(defn evaluate-1 [game-str]
  (let [max-r (find-max-color-usage "red" game-str)
        max-g (find-max-color-usage "green" game-str)
        max-b (find-max-color-usage "blue" game-str)]
    (and
     (>= 12 max-r)
     (>= 13 max-g)
     (>= 14 max-b))))


;; part 1
(defn part-1 [input]
  (->> input
       (map (partial evaluate-1))
       (zipmap (iterate inc 1))
       (filter second)
       (map first)
       (reduce +)))

(println (part-1 example-input))
(println (part-1 input))


;; part 2
(defn evaluate-2 [game-str]
  (let [max-r (find-max-color-usage "red" game-str)
        max-g (find-max-color-usage "green" game-str)
        max-b (find-max-color-usage "blue" game-str)]
    (* max-r max-g max-b)))

(defn part-2 [input]
  (->> input
       (map evaluate-2)
       (reduce +)))


(println (part-2 example-input))
(println (part-2 input))
