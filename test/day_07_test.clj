(ns day-07-test
  (:require [day-07 :as sut]
            [clojure.test :as t :refer [deftest testing is]]))


(deftest day-7
  (testing "Card strength"
    (is (= 7 (sut/hand-strength "AAAAA")))
    (is (= 6 (sut/hand-strength "AA8AA")))
    (is (= 5 (sut/hand-strength "23332")))
    (is (= 4 (sut/hand-strength "TTT98")))
    (is (= 3 (sut/hand-strength "23432")))
    (is (= 2 (sut/hand-strength "A23A4")))
    (is (= 1 (sut/hand-strength "23456"))))


  (testing "Comparison"
    (is (=
         [["2AAAA" "_"] ["33332" "_"]]
         (sort-by sut/extract-comparison-values [["33332" "_"] ["2AAAA" "_"]]))))


  (testing "Computation"
    (is (= 6440 (sut/part-1 sut/example-input))))


  (testing "Card strength, part 2"
    (is (= 7 (sut/hand-strength-2 "AAAAA")))
    (is (= 6 (sut/hand-strength-2 "AA8AA")))
    (is (= 5 (sut/hand-strength-2 "23332")))
    (is (= 4 (sut/hand-strength-2 "TTT98")))
    (is (= 3 (sut/hand-strength-2 "23432")))
    (is (= 2 (sut/hand-strength-2 "A23A4")))
    (is (= 1 (sut/hand-strength-2 "23456")))

    (is (= 7 (sut/hand-strength-2 "JJJJJ")))
    (is (= 7 (sut/hand-strength-2 "AAAAJ")))
    (is (= 7 (sut/hand-strength-2 "AAJAA")))
    (is (= 6 (sut/hand-strength-2 "J3332")))
    (is (= 5 (sut/hand-strength-2 "23J32")))

    (is (= 6 (sut/hand-strength-2 "AJJJT")))
    (is (= 7 (sut/hand-strength-2 "AJJJJ")))
    (is (= 7 (sut/hand-strength-2 "AAJJJ")))

    (is (= 6 (sut/hand-strength-2 "QJJQ2")))

    (is (= 2 (sut/hand-strength-2 "32T3K")))
    (is (= 6 (sut/hand-strength-2 "T55J5")))
    (is (= 3 (sut/hand-strength-2 "KK677")))
    (is (= 6 (sut/hand-strength-2 "KTJJT")))
    (is (= 6 (sut/hand-strength-2 "QQQJA"))))

  (testing "Comparison, part 2"
    (is (=
         [["JKKK2" "_"] ["QQQQ2" "_"]]
         (sort-by sut/extract-comparison-values-2 [["QQQQ2" "_"] ["JKKK2" "_"]])))))
