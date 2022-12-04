(ns aoc22.day4
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.java.io :as io]))

(def test-input "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(defn ->pair [s]
  (let [[[a b] [x y]]
        (->> (str/split s #",")
             (map #(map read-string (str/split % #"-"))))]
    [(set (range a (inc b)))
     (set (range x (inc y)))]))

(defn part-1 [s]
  (->> (str/split s #"\n")
       (map ->pair)
       (map (fn [[ab xy]]
              (let [intersect (set/intersection ab xy)]
                (if (or (= intersect ab)
                        (= intersect xy))
                  1
                  0))))
       (reduce +)))

(part-1 test-input) ; want 2

(def input
  (-> (io/resource "day4.txt")
      slurp))

(part-1 input)

(defn part-2 [s]
  (->> (str/split s #"\n")
       (map ->pair)
       (map (fn [[ab xy]]
              (if (not-empty (set/intersection ab xy))
                1
                0)))
       (reduce +)))

(part-2 test-input) ; want 4

(part-2 input)
