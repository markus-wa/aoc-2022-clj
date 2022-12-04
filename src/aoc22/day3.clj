(ns aoc22.day3
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.java.io :as io]
            [medley.core :as m]))

(defn ->compartments [s]
  (->> (partition (/ (count s) 2) s)
       (map set)))

(def item->priority
  (->> (concat (range (int \a) (inc (int \z))) (range (int \A) (inc (int \Z))))
       vec
       (zipmap (iterate inc 1))
       (m/map-kv (fn [k v] [(char v) k]))))

(defn part-1 [s]
  (->> (str/split s #"\n")
       (map ->compartments)
       (map #(set/intersection (first %) (second %)))
       (map #(item->priority (first %)))
       (reduce +)))

(defn part-2 [s]
  (->> (str/split s #"\n")
       (map set)
       (partition 3)
       (map #(set/intersection (first %) (second %) (last %)))
       (map #(item->priority (first %)))
       (reduce +)))

(def input
  (-> (io/resource "day3.txt")
      slurp))

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
