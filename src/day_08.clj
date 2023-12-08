(ns day-08
  (:require
   [clojure.string :as string]
   [malli.experimental :as mx]
   [utils]))


(def example-input
  "RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)")

(def example-input-2
  "LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)")


(def example-input-3
  "LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)")

(def input (slurp "data/day_08.txt"))


(def Targets
  [:map {:closed? true} ["L" :string] ["R" :string]])

;; schema
(def Input
  [:tuple
   :string                     ;; instructions
   :string                     ;; start node
   :string                     ;; target node
   [:map-of :string Targets]]) ;; node -> (L/R) -> target


(mx/defn parse-node :- [:tuple :string [:map {:closed? true} ["L" :string] ["R" :string]]]
  [node :- :string]
  (let [[start tgt-left tgt-right] (re-seq #"[A-Z0-9]+" node)]
    [start {"L" tgt-left, "R" tgt-right}]))


(mx/defn parse-input :- Input
  [input :- :string]
  (let [[instructions nodes-str] (string/split input #"\n\n")
        node-lines               (string/split-lines nodes-str)
        start-node               (re-find #"[A-Z]+" (first node-lines))
        tgt-node                 (re-find #"[A-Z]+" (last node-lines))
        nodes                    (map parse-node node-lines)]
    [instructions start-node tgt-node (into {} nodes)]))


(defn traverse [node instruction nodes]
  (get-in nodes [node instruction]))


(defn part-1 [input-str]
  (let [[instructions start-node tgt-node nodes] (parse-input input-str)]
    (loop [steps 0
           node  "AAA"
           insts (flatten (repeat (seq instructions)))]
      (if (= node "ZZZ")
        steps
        (recur (inc steps) (traverse node (str (first insts)) nodes) (rest insts))))))

(take 10
      (flatten
       (repeat (seq "AB"))))


(part-1 example-input)
(part-1 example-input-2)
(part-1 input)

