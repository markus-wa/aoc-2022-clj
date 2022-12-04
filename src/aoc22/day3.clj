(ns aoc22.day3
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.java.io :as io]))

(def item->priority
  (->> (concat (range (int \a) (inc (int \z))) (range (int \A) (inc (int \Z))))
       vec
       (zipmap (iterate inc 1))
       set/map-invert))

(defn part-1 [s]
  (->> (str/split-lines s)
       (map #(split-at (/ (count %) 2) %))
       (map #(set/intersection (first %) (second %)))
       (map #(item->priority (first %)))
       (reduce +)))

(defn part-2 [s]
  (->> (str/split-lines s)
       (map set)
       (partition 3)
       (map (fn [[a b c]] (set/intersection a b c)))
       (map #(item->priority (first %)))
       (reduce +)))

(def input (slurp (io/resource "day3.txt")))

(part-1 input)
(part-2 input)

(comment
  (def test-input "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

  (part-1 test-input) ; want 157
  (part-2 test-input) ; want 70
  )
