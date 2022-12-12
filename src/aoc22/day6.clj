(ns aoc22.day6
  (:require [clojure.java.io :as io]))

(def test-input "mjqjpqmgbljsphdztnvjfqwrcgsmlb")

(defn find-marker [n s]
  (loop [i 0
         s s]
    (if (apply distinct? (take n s))
      (+ i n)
      (recur (inc i) (rest s)))))

(find-marker 4 test-input) ; want 7

(def input (slurp (io/resource "day6.txt")))

(find-marker 4 input)

(find-marker 14 test-input) ; want ?

(find-marker 14 input)
