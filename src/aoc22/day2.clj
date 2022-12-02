(ns aoc22.day2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def test-input "A Y
B X
C Z")

(defn points-win-draw-loss
  [op me]
  (if (= op me)
    3
    (case op
      1 (case me 2 6 0)
      2 (case me 3 6 0)
      3 (case me 1 6 0))))

(defn points
  [[op me]]
  (+ (points-win-draw-loss op me) me))

(defn part-1 [s]
  (->> (str/split s #"\n")
       (map #(str/split % #" "))
       (map #(map {"A" 1 "B" 2 "C" 3 "X" 1 "Y" 2 "Z" 3} %))
       (map points)
       (reduce +)))

(part-1 test-input) ; want 15

(def input
  (-> (io/resource "day2.txt")
      slurp))

(part-1 input)

(defn points-2
  [[op result]]
  (+ ({"X" 0 "Y" 3 "Z" 6} result)
     (case op
       "A" (case result
             "X" 3
             "Y" 1
             "Z" 2)
       "B" (case result
             "X" 1
             "Y" 2
             "Z" 3)
       "C" (case result
             "X" 2
             "Y" 3
             "Z" 1))))

(defn part-2 [s]
  (->> (str/split s #"\n")
       (map #(str/split % #" "))
       (map points-2)
       (reduce +)))

(part-2 test-input) ; want 12

(part-2 input)
