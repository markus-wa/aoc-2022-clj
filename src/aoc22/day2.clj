(ns aoc22.day2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn points-win-draw-loss [op me]
  (if (= op me)
    3
    (case op
      1 (case me 2 6 0)
      2 (case me 3 6 0)
      3 (case me 1 6 0))))

(defn points [[op me]]
  (+ (points-win-draw-loss op me) me))

(defn points-2 [[op result]]
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

(defn run [s]
  (let [strategy (->> (str/split-lines s)
                      (map #(str/split % #" ")))]
    [(->> (map #(map {"A" 1 "B" 2 "C" 3 "X" 1 "Y" 2 "Z" 3} %) strategy)
          (map points)
          (reduce +))
     (->> (map points-2 strategy)
          (reduce +))]))

(run (slurp (io/resource "day2.txt")))

(comment
  (def test-input "A Y
B X
C Z")

  (run test-input) ; want [15 12]
  )
