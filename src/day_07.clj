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


(mx/defn hand-strength :- :int
  [card :- [:string {:min 5, :max 5}]]
  (let [same-card-partitions   (partition-by identity (sort card))
        partition-sizes-sorted (sort-by - (map count same-card-partitions))]
    (cond
      (= 5 (first partition-sizes-sorted))      7    ;; 5-of-kind
      (= 4 (first partition-sizes-sorted))      6    ;; 4-of-kind
      (= [3 2] (take 2 partition-sizes-sorted)) 5    ;; Full house
      (= 3 (first partition-sizes-sorted))      4    ;; 3-of-kind
      (= [2 2] (take 2 partition-sizes-sorted)) 3    ;; 2 pairs
      (= 2 (first partition-sizes-sorted))      2    ;; 1 pair
      :else                                     1))) ;; high card


(def card->strength
  (zipmap
   "23456789TJQKA"
   (iterate inc 1)))


(defn extract-comparison-values [[hand _]]
  [(hand-strength hand)
   (mapv #(card->strength %) hand)])


(mx/defn part-1 :- :int
  [parsed-input :- [:sequential [:tuple :string :string]]]
  (let [sorted-hands (sort-by extract-comparison-values parsed-input)
        bids         (map second sorted-hands)
        scores       (map-indexed (fn [idx bid] (* (inc idx) (parse-long bid))) bids)]
    (apply + scores)))


(part-1 example-input)
(part-1 input)


;; part 2
(def joker-pimp ;; how much can a single joker pimp the hand
  {1 2 ;; high card -> 1 pair
   2 4 ;; 1 pair -> 3-of-kind
   3 5 ;; ...
   4 6
   5 6
   6 7
   7 7})


(mx/defn hand-strength-2 :- :int
  [hand :- [:string {:min 5, :max 5}]]
  (let [hand-no-Js               (string/replace hand #"J" "")
        count-Js                 (count (re-seq #"J" hand))
        hand-strength-with-no-Js (hand-strength hand-no-Js)
        pimped-hands             (iterate joker-pimp hand-strength-with-no-Js)]
    (min
     7
     (nth pimped-hands count-Js)))) ;; pimp my hand with "count-Js" jokers))


(def card->strength-2
  (zipmap
   "J23456789TQKA"
   (iterate inc 1)))


(defn extract-comparison-values-2 [[hand _]]
  [(hand-strength-2 hand)
   (mapv #(card->strength-2 %) hand)])


(mx/defn part-2 :- :int
  [parsed-input :- [:sequential [:tuple :string :string]]]
  (let [sorted-hands (sort-by extract-comparison-values-2 parsed-input)
        bids         (map second sorted-hands)
        scores       (map-indexed (fn [idx bid] (* (inc idx) (parse-long bid))) bids)]
    (apply + scores)))


(part-2 example-input)
;; takes some time (~20 s)
; (part-2 input)
;; => 249356515
