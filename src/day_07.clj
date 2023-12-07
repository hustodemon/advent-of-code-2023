(ns day-07
  (:require
   [clojure.string :as string]
   [malli.experimental :as mx]
   [utils]))


(mx/defn parse-input :- [:sequential [:tuple :string :string]]
  [input :- :string]
  (->> input
       (string/split-lines)
       (map #(string/split % #"\s+"))))


(def example-input
  (parse-input
   "32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"))


(def input
  (parse-input (slurp "data/day_07.txt")))


(mx/defn group-strength
  [card :- [:string {:min 5, :max 5}]]
  (let [same-card-partitions   (partition-by identity (sort card))
        partition-sizes-sorted (sort-by - (map count same-card-partitions))]
    (cond
      (= 5 (first partition-sizes-sorted))      7    ;; 5-of-kind
      (= 4 (first partition-sizes-sorted))      6    ;; 4-of-kind
      (= [3 2] (take 2 partition-sizes-sorted)) 5    ;; Full house
      (= 3 (first partition-sizes-sorted))      4    ;; 3-of-kind
      (= [2 2] (take 2 partition-sizes-sorted)) 3    ;; 2 pairs
      (= [2 1] (take 2 partition-sizes-sorted)) 2    ;; 1 pair
      :else                                     1))) ;; high card


(def card->strength
  (zipmap
   "23456789TJQKA"
   (iterate inc 1)))


(defn extract-comparison-values [[card-group _]]
  [(group-strength card-group)
   (mapv #(card->strength %) card-group)])


(mx/defn part-1 :- :int
  [parsed-input :- [:sequential [:tuple :string :string]]]
  (let [sorted-hands (sort-by extract-comparison-values parsed-input)
        bids         (map second sorted-hands)
        scores       (map-indexed (fn [idx bid] (* (inc idx) (parse-long bid))) bids)]
    (apply + scores)))


(part-1 example-input)
(part-1 input)
