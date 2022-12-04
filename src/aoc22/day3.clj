(ns aoc22.day3
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.java.io :as io]
            [medley.core :as m]))

(def test-input "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(defn ->compartments [s]
  (as-> (vec s) v
    (partition (/ (count v) 2) v)
    (map set v)))

(def priority
  (->> (concat (range (int \a) (inc (int \z))) (range (int \A) (inc (int \Z))))
       vec
       (zipmap (iterate inc 1))
       (m/map-kv (fn [k v] [(char v) k]))))

(defn part-1 [s]
  (->> (str/split s #"\n")
       (map ->compartments)
       (map #(set/intersection (first %) (second %)))
       (map #(priority (first %)))
       (reduce +)))

(part-1 test-input) ; want 157

(def input
  (-> (io/resource "day3.txt")
      slurp))

(part-1 input)

(defn part-2 [s]
  (->> (str/split s #"\n")
       (map #(set (vec %)))
       (partition 3)
       (map #(set/intersection (first %) (second %) (last %)))
       (map #(priority (first %)))
       (reduce +)))

(part-2 test-input) ; want 70

(part-2 input)
