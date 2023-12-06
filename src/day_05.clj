(ns day-05
  (:require
   [clojure.string :as string]
   [malli.experimental :as mx]
   [utils]))


(def example-input
  (string/split
   "seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4
"
   #"\n\n"))


(def input
  (string/split (slurp "data/day_05.txt") #"\n\n"))


(mx/defn parse-group :- [:sequential [:tuple :int :int :int]]
  [lines :- :string]
  (mapv #(mapv parse-long (string/split % #"\s"))
        (rest (string/split-lines lines))))


(mx/defn parse-seeds :- [:sequential int?]
  [seeds-line :- :string]
  (->> seeds-line
       (re-seq #"\d+")
       (map parse-long)))


(defn get-target [seed [end start cnt]]
  (if (and (>= seed start) (< seed (+ start cnt)))
    (+ (- seed start) end)
    nil))


(defn traverse-groups [cur-pos [fst-grp & rst-grps]]
  (if fst-grp
    (let [targets (map #(get-target cur-pos %) fst-grp)
          target  (or (first (remove nil? targets)) cur-pos)]
      (recur target rst-grps))
    cur-pos))


(defn part-1 [input]
  (let [seeds  (parse-seeds (first input))
        groups (map parse-group (rest input))]
    (apply
     min
     (map #(traverse-groups % groups) seeds))))


(part-1 example-input)
(part-1 input)

;; part 2
;; brute-force is not enough for the part 2
;;(defn get-seeds-2 [seeds-line]
;;  (->> seeds-line
;;       (re-seq #"\d+")
;;       (partition 2)
;;       (map (fn [[start cnt]] (range (parse-long start) (+ (parse-long start) (parse-long cnt)))))))
