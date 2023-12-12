(ns day-08
  (:require
   [clojure.set :as cset]
   [clojure.string :as string]
   [malli.experimental :as mx]
   [utils :refer [print-solution]]))


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
  [:map {:closed? true} [\L :string] [\R :string]])

;; schema
(def Input
  [:tuple
   :string                     ;; instructions
   [:map-of :string Targets]]) ;; node -> (L/R) -> target


(mx/defn parse-node :- [:tuple :string [:map {:closed? true} [\L :string] [\R :string]]]
  [node :- :string]
  (let [[start tgt-left tgt-right] (re-seq #"[A-Z0-9]+" node)]
    [start {\L tgt-left, \R tgt-right}]))


(mx/defn parse-input :- Input
  [input :- :string]
  (let [[instructions nodes-str] (string/split input #"\n\n")
        node-lines               (string/split-lines nodes-str)
        nodes                    (map parse-node node-lines)]
    [instructions (into {} nodes)]))


(defn traverse [node instruction nodes]
  (get-in nodes [node instruction]))


(mx/defn part-1 :- :int

  ([parsed-input :- Input] (part-1 parsed-input "AAA" #{"ZZZ"}))

  ([[instructions nodes] :- Input
   start-node :- :string
   goal-nodes :- [:set :string]]
   (loop [steps 0
          node  start-node
          insts (flatten (repeat (seq instructions)))] ;; infinite seq of instructions]
     (if (contains? goal-nodes node)
       steps
       (recur (inc steps) (traverse node (first insts) nodes) (rest insts))))))


(defn- nodes-ending-with [s nodes]
  (filter #(string/ends-with? % s) (keys nodes)))


;; part 2
;; I read the spoiler about LCM
(defn- find-divisors [n]
  (->> (range 1 (/ (inc n) 2))
       (filter #(zero? (mod n %)))
       (into #{n})))

(defn- gcd [ns]
  (apply max
   (apply cset/intersection
          (map find-divisors ns))))

(defn- lcm [ns]
  (first
   (filter (fn [multiple] (every? #(zero? (mod multiple %)) ns))
           (rest (iterate (partial + (gcd ns)) 0)))))


(defn part-2-1 [[_ nodes :as parsed-input]]
  (let [start-nodes (nodes-ending-with "A" nodes)
        goal-nodes  (into #{} (nodes-ending-with "Z" nodes))
        lenghts     (map #(part-1 parsed-input % goal-nodes) start-nodes)]
    (lcm lenghts)))


(defn -main [& args]
 ;;(part-1 (parse-input example-input))
 ;;(part-1 (parse-input example-input-2))
 (print-solution 1 (part-1 (parse-input input))))
;; (part-2-1 (parse-input example-input-3))
;; DO NOT RUN! it'll blow up, but the correct answer is 20685524831999
;; we need more efficient implementation :)
;;(part-2-1 (parse-input input))
