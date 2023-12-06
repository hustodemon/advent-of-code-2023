(ns day-06
  (:require
   [clojure.string :as string]
   [malli.core :as m]
   [malli.dev :as dev]
   [malli.experimental :as mx]
   [malli.instrument :as mi]))


(def example-input
  "Time:      7  15   30
Distance:  9  40  200")

; start the instrumentation and check for changes
(dev/start!)

; show us the function schemas
(m/function-schemas)

;(mi/instrument!) ; should not be needed, when dev/start! was called
;(mi/check)


; formula:
; 0 -> 0
; 1 -> time - 1
; n -> (time - n) * n
(mx/defn formula :- [:int {:min 0}]
  [total-time :- [:int {:min 0}]
   press-time :- [:int {:min 0}]] ;; :max total-time would be cool
  (* press-time (- total-time press-time)))


(mx/defn parse-games :- [:sequential [:tuple :int :int]]
  [lines-str :- :string]
  (->> (string/split-lines lines-str)
       (map (fn remove-description [s] (string/replace-first s #"\D+" "")))
       (map (fn line-to-numbers [s] (map parse-long (string/split s #"\s+"))))
       (apply map vector)))


(mx/defn count-winning-games :- :int
  [[total-time :- :int
    record :- :int]]
  (let [fst-losing-distances (filter (fn [i] (> (formula total-time i) record))
                                     (take (+ total-time 1) (iterate inc 0)))]
    (count fst-losing-distances)))


(mx/defn part-1 :- :int
  [input :- :string]
  (let [games         (parse-games input)
        winning-games (map count-winning-games games)]
    (apply * winning-games)))


(println (part-1 example-input))
(println (part-1 (slurp "data/day_06.txt")))


;; part 2
(mx/defn parse-game-2 :- [:tuple :int :int]
  [lines-str :- :string]
  (->> (string/split-lines lines-str)
       (map (fn remove-description [s] (string/replace s #"\D+" "")))
       (map parse-long)))


;; as expected, this is very slow, one possible improvement would be doing
;; "binary search"
(println "Brute force is quite slow. The solution is commented-out.")
;;( count-winning-games
;;  (parse-game-2 (slurp "data/day_06.txt")))
