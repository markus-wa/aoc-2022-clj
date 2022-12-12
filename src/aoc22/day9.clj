(ns aoc22.day9
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.java.io :as io]
            [medley.core :as m]))

(def test-input "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")

(defn update-head [[hx hy] direction distance]
  (case direction
    "R" [(+ hx distance) hy]
    "U" [hx (- hy distance)]
    "L" [(- hx distance) hy]
    "D" [hx (+ hy distance)]))

(defn update-tail [[hx hy] [tx ty :as tail] direction]
  (if (and (<= (Math/abs (- hx tx)) 1)
           (<= (Math/abs (- hy ty)) 1))
    tail
    (case direction
      "R" [(dec hx) hy]
      "L" [(inc hx) hy]
      "U" [hx (inc hy)]
      "D" [hx (dec hy)])))

(defn move
  [[head tail visited]
   [direction distance]]
  (let [head (update-head head direction distance)
        tail (update-tail head tail direction)]
    [head tail (conj visited tail)]))

(defn part-1 [s]
  (->> (str/split-lines s)
       (map #(str/split % #" "))
       (mapcat (fn [[dir dist]]
              (for [_ (range (read-string dist))]
                [dir 1])))
       (reduce move [[0 0] [0 0] #{}])
       last
       count))

(part-1 test-input) ; want 13

(def input (slurp (io/resource "day9.txt")))

(part-1 input)

(defn part-2 [s]
  )

(part-2 test-input) ; want ?

(part-2 input)
