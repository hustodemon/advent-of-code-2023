(ns day-03-test
  (:require [day-03 :as sut]
            [clojure.test :as t :refer [deftest is testing]]))



(deftest day-03

  (testing "marking"
    (is (= "..123..---.---.-----.123.--------" ;; Y is any original symbol, X is shaked one
           (sut/mark-relevant-numbers "..123..Y11.22Y.11X22.123.Y123X45Y"))))
  (testing "marking corner case"
    (is (= "....-----...."
           (sut/mark-relevant-numbers "....939Y7...."))))
  (testing "single number"
    (is (= 0 (sut/compute (sut/prepare-input ["4"])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input ["*4"])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input ["*4*"])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input ["4*"])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input ["*." "4."])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input ["4" "*"])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input ["4." ".*"])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input [".4" "*."])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input ["*." ".4"])))))
  (testing "single number"
    (is (= 4 (sut/compute (sut/prepare-input [".*" "4."])))))
  (testing "single number"
    (is (= 20 (sut/compute (sut/prepare-input ["4*5"])))))

  )

(sut/shake-symbols
 (sut/prepare-input ["*" "4"]))
