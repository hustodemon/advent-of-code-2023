(ns day-10
  (:require
   [clojure.string :as string]
   [malli.experimental :as mx]
   [utils :refer [print-solution]]))

(def example-input
  ".....
.S-7.
.|.|.
.L-J.
.....")

(def input (slurp "data/day_10.txt"))


(mx/defn parse-input :- [:vector [:vector char?]]
  [input :- :string]
  (mapv vec (string/split-lines input)))


(mx/defn pipe-target
  "Where does the pipe lead to given relative start position?"
  [from-pos :- [:tuple :int :int]
   pipe :- char?]
  (get-in
   {\| {[-1 0] [1 0]
        [1 0] [-1 0]}  ;; could even be simplified: these are inverse

    \- {[0 -1] [0 1]
        [0 1] [0 -1]}

    \L {[0 1] [-1 0]
        [-1 0] [0 1]}

    \J {[0 -1] [-1 0]
        [-1 0] [0 -1]}

    \7 {[0 -1] [1 0]
        [1 0] [0 -1]}

    \F {[0 1] [1 0]
        [1 0] [0 1]}}
   [pipe from-pos]))


(mx/defn next-step :- [:tuple :int :int]
  [from-pos cur-pos pipe]
  (mapv +
        cur-pos
        (pipe-target (mapv - from-pos cur-pos) pipe)))


(mx/defn traverse :- :int
  [from-pos cur-pos pipe-map len-acc]
  (let [pipe (get-in pipe-map cur-pos nil)]
    (if (= \S pipe)
      len-acc
      (recur cur-pos (next-step from-pos cur-pos pipe) pipe-map (inc len-acc)))))


(defn solve [input]
  ;; todo write a fn for finding \S and a valid initial direction
  (let [rtt-length (traverse [25 108] [26 108] (parse-input input) 0)]
    (quot (inc rtt-length) 2)))


(defn -main [& args]
 (print-solution 1 (solve input)))
