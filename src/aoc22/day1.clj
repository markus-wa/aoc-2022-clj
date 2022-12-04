(ns aoc22.day1
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn ->elf [s]
  (->> (str/split-lines s)
       (map #(Integer/parseInt %))
       (reduce +)))

(defn ->elves [s]
  (->> (str/split s #"\n\n")
       (map ->elf)))

(defn run [s]
  (let [elves (->elves s)]
    [(reduce max elves)
     (reduce + (take-last 3 (sort elves)))]))

(run (slurp (io/resource "day1.txt")))

(comment
  (def test-input "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

  (run test-input) ; want [24000 45000]
  )
