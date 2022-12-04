(ns aoc22.day4
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn ->pair [s]
  (->> (re-find #"(\d+)-(\d+),(\d+)-(\d+)" s)
       rest
       (map read-string)))

(defn run [s]
  (let [pairs
        (->> (str/split s #"\n")
             (map ->pair))]
    [(count (filter (fn [[a b x y]] (<= (* (- a x) (- b y)) 0)) pairs))
     (count (filter (fn [[a b x y]] (<= (* (- y a) (- x b)) 0)) pairs))]))

(def input
  (-> (io/resource "day4.txt")
      slurp))

(run input)

(comment
  (def test-input "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

  (run test-input) ;; want [2 4]
  )
