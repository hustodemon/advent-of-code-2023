(ns day-06-test
  (:require [day-06 :as sut]
            [clojure.test :as t :refer [deftest testing is]]))



(comment deftest day-06
  (testing "Parse games"
    (is (= [[7 9] [15 40] [30 200]]
           (sut/parse-games sut/example-input)))))
