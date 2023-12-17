(ns day-16-test
  (:require [day-16 :as sut]
            [clojure.test :as t :refer [deftest testing is]]))

(def test-map
  (sut/parse-input
   ".|..
.\\-|
.../"))

;; aka:
;; .|..
;; .\-|
;; .../

(deftest day-16
  (testing "step fn"
    (is (= [[1 1]] (sut/step [0 0] [0 1] test-map)))
    (is (= [[1 1] [1 3]] (sut/step [0 2] [1 2] test-map)))
    )

  (testing "traversal"
    (is (= #{
             [0 0]
             [0 1]
             [1 1]
             [1 2]
             [1 3]
             [0 3]
             [2 3]
             [2 2]
             [2 1]
             [2 0]
             }
           (into #{} (map second (sut/traverse [[[0 -1] [0 0]]] test-map #{})))))

    )

  )
