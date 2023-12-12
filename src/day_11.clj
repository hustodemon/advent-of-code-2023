(ns day-11
  (:require
   [clojure.string :as string]
   [utils :refer [print-solution]]))

(def example-input
  "...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....")


(def input (slurp "data/day_11.txt"))


(defn expand-lines [lines]
  (mapcat (fn [line] (if (every? #(= \. %) line)
                       [line line]
                       [line]))
          lines))

(defn transpose [lines]
  (apply map vector lines))


(defn expand-columns [lines]
  (-> lines
      transpose
      expand-lines
      transpose))


(defn expand-galaxy [lines]
  (-> lines
      expand-lines
      expand-columns))


(defn galaxy-coords [lines]
  (->> lines
       (expand-galaxy)  ;; duplicate needed columns and rows
       (map-indexed (fn [y row] (map-indexed (fn [x el] [y x el]) row)))  ;; map the coordinates over the structure
       (apply concat) ;; well, mapcat-indexed doesn't exist :)
       (filter (fn [[_ _ el]] ( = \# el))) ;; keep the galaxes
       (mapv (partial take 2)))) ;; keep the coords


(defn compute-distance [[y1 x1] [y2 x2]]
  (int
   (+
    (abs (- x1 x2))
    (abs (- y1 y2)))))



(defn deduped-coord-pairs [coords]
  ;; is there a better way?
  (for [coords1 coords
        coords2 coords
        :when (not= coords coords2)]
    (into #{} [coords1 coords2])))


(defn part-1 [input]
  (->> input
       (string/split-lines)
       (galaxy-coords)
       (deduped-coord-pairs)
       (into #{})
       (map (partial apply compute-distance))
       (apply +)))

;(part-1 example-input)
(defn -main [& args]
  (print-solution 1 (part-1 input)))
