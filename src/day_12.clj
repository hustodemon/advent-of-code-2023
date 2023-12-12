(ns day-12
  (:require
   [clojure.string :as string]
   [clojure.math.combinatorics :as combo]
   [utils :refer [print-solution]]))


(def input (slurp "data/day_12.txt"))
(def example-input
  "???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1")


(defn permutations [num-good num-broken]
  (combo/permutations
   (concat
    (repeat num-good \.)
    (repeat num-broken \#))))


(defn lay-over [[fst-char-perm & rest-perm] st]
  (if fst-char-perm
    (lay-over rest-perm (string/replace-first st #"\?" (str fst-char-perm)))
    st))


(defn check-groups [s group-counts]
  (=
   (vec group-counts)
   (mapv count
         (re-seq #"[^\.]+" s))))


(defn count-occurences [coll x]
  (count (filter #(= x %) coll)))


(defn process-row [s broken-counts]
  (let [count-unknowns       (count-occurences s \?)
        count-broken-known   (count-occurences s \#)
        count-broken-total   (apply + broken-counts)
        count-broken-unknown (- count-broken-total count-broken-known)
        count-good-unknown   (- count-unknowns count-broken-unknown)
        good-broken-perms    (permutations count-good-unknown count-broken-unknown)]
    (->> good-broken-perms
         (map #(lay-over % s))
         (filter #(check-groups % broken-counts)))))


(defn parse-line [line]
  (let [[s groups-str] (string/split line #"\s+")]
    [s (mapv parse-long (string/split groups-str #","))]))


(defn compute-line [line]
  (->> line
       parse-line
       (apply process-row)
       count))


(defn part-1 [input]
  (apply + (map compute-line (string/split-lines input))))

;;(part-1 example-input)
(defn -main [& args]
  (print-solution 1 (part-1 input)))
