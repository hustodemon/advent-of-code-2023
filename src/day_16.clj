(ns day-16
  (:require
   [clojure.string :as string]
   [malli.experimental :as mx]
   [utils :refer [print-solution]]))

(def example-input
  ".|...\\....
|.-.\\.....
.....|-...
........|.
..........
.........\\
..../.\\\\..
.-.-/..|..
.|....-|.\\
..//.|....")


(def input (slurp "data/day_16.txt"))


(def Coord [:tuple :int :int])

(def Map [:sequential [:sequential char?]])


(mx/defn step-rel :- [:sequential Coord]
  [start-pos-rel :- Coord
   tile :- [:maybe char?]]

  ;; tile -> relative pos of the previous tile -> where to go (rel.)
  (get-in
   {\. {[-1 0] [[1 0]]
        [0 -1] [[0 1]]
        [1 0]  [[-1 0]]
        [0 1]  [[0 -1]]}

    \/ {[-1 0] [[0 -1]]
        [0 -1] [[-1 0]]
        [0 1]  [[1 0]]
        [1 0]  [[0 1]]}

    \\ {[-1 0] [[0 1]]
        [0 1]  [[-1 0]]
        [0 -1] [[1 0]]
        [1 0]  [[0 -1]]}

    \- {[0 -1] [[0 1]]
        [0 1]  [[0 -1]]
        [-1 0] [[0 -1] [0 1]]
        [1 0]  [[0 -1] [0 1]]}

    \| {[-1 0] [[1 0]]
        [1 0]  [[-1 0]]
        [0 -1] [[-1 0] [1 0]]
        [0 1]  [[-1 0] [1 0]]}}
   [tile start-pos-rel]
   []))


(mx/defn step :- [:sequential Coord]
  [start-pos :- Coord
   cur-pos :- Coord
   tile-map :- Map]
  (let [start-pos-rel (mapv - start-pos cur-pos)]
    (filterv (fn [new-coord] (get-in tile-map new-coord))
             (mapv #(mapv + % cur-pos)
                   (step-rel start-pos-rel (get-in tile-map cur-pos))))))


(mx/defn traverse
  [[[fst-src fst-current :as fst] & rst] :- [:sequential [:tuple Coord Coord]]
   tile-map :- Map
   visited :- [:set [:tuple Coord Coord]]]

  (if fst
    (if (contains? visited fst)
      (recur rst
             tile-map
             visited)
      (recur (concat
              rst
              (map (fn [c] [fst-current c]) (step fst-src fst-current tile-map)))
             tile-map
             (conj visited fst)))
    visited))


(mx/defn parse-input :- [:sequential [:sequential char?]]
  [input :- :string]
  (mapv #(mapv first (string/split % #""))
       (string/split-lines input)))


(defn solve [parsed-input]
  (->> (traverse [[[0 -1] [0 0]]] parsed-input #{})
       (map second)
       (into #{})
       count))


;; for some reason this doesn't work anymore. no power to debug
;; (print-solution 1 (solve (parse-input input)))
