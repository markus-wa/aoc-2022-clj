(ns aoc22.day8
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.java.io :as io]
            [medley.core :as m]))

(def test-input "30373
25512
65332
33549
35390")

(defn visible-trees [rows n-rows n-cols]
  (for [x (range 1 (dec n-cols))
        y (range 1 (dec n-rows))
        :let [tree (get-in rows [y x])
              up (for [i (range y)] (get-in rows [i x]))
              down (for [i (range (inc y) n-rows)] (get-in rows [i x]))
              left (for [i (range x)] (get-in rows [y i]))
              right (for [i (range (inc x) n-cols)] (get-in rows [y i]))]
        :when
        (or (every? #(> tree %) up)
            (every? #(> tree %) down)
            (every? #(> tree %) left)
            (every? #(> tree %) right))]
    [x y]))

(defn ->tree-map [s]
  (->> (str/split-lines s)
       (map (fn [line] (map #(read-string (str %)) line)))
       (map vec)
       vec))

(defn part-1 [s]
  (let [rows (->tree-map s)
        n-rows (count rows)
        n-cols (count (first rows))]
    (+ (* 2 (- n-rows 2))
       (* 2 n-cols)
       (count (visible-trees rows n-rows n-cols)))))

(part-1 test-input) ; want 21

(def input (slurp (io/resource "day8.txt")))

(part-1 input)

(defn scenic-score [tree others]
  (if (seq others)
    (let [n-others (count (take-while #(> tree %) others))]
      (if (= n-others (count others))
        n-others
        (inc n-others)))
    0))

(defn scenic-scores [rows n-rows n-cols]
  (for [x (range 1 (dec n-cols))
        y (range 1 (dec n-rows))
        :let [tree (get-in rows [y x])
              up (for [i (reverse (range y))] (get-in rows [i x]))
              down (for [i (range (inc y) n-rows)] (get-in rows [i x]))
              left (for [i (reverse (range x))] (get-in rows [y i]))
              right (for [i (range (inc x) n-cols)] (get-in rows [y i]))]]
    (* (scenic-score tree up)
       (scenic-score tree down)
       (scenic-score tree left)
       (scenic-score tree right))))

(defn part-2 [s]
  (let [rows (->tree-map s)
        n-rows (count rows)
        n-cols (count (first rows))]
    (->> (scenic-scores rows n-rows n-cols)
         (reduce max))))

(part-2 test-input) ; want ?

(part-2 input)
