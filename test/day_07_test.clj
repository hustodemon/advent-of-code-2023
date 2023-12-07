(ns day-07-test
  (:require [day-07 :as sut]
            [clojure.test :as t :refer [deftest testing is]]))


(deftest day-7
  (testing "Card strength"
    (is (= 7 (sut/group-strength "AAAAA")))
    (is (= 6 (sut/group-strength "AA8AA")))
    (is (= 5 (sut/group-strength "23332")))
    (is (= 4 (sut/group-strength "TTT98")))
    (is (= 3 (sut/group-strength "23432")))
    (is (= 2 (sut/group-strength "A23A4")))
    (is (= 1 (sut/group-strength "23456"))))


  (testing "Comparison"
    (is (=
         [["2AAAA" "_"] ["33332" "_"]]
         (sort-by sut/extract-compare-values [["33332" "_"] ["2AAAA" "_"]]))))


  (testing "Computation"
    (is (= 6440 (sut/part-1 sut/example-input)))))
