(ns day-15-test
  (:require
   [day-15 :as sut]
   [clojure.test :as t :refer [deftest testing is]]))


(deftest day-15
  (testing "HASH"
    (is (= 52 (sut/compute-hash "HASH"))))

  (testing "computation"
    (is (= 1320 (sut/part-1 "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7" )))))
