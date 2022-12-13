(ns aoc22.day10
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def test-input "addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop")

(defn signal-strengths [s]
  (loop [instructions (str/split-lines s)
         cycle 1
         x 1
         signal-strengths '(0)]
    (if (seq instructions)
      (let [[cmd param] (str/split (first instructions) #" ")]
        (case cmd
          "noop"
          (recur (rest instructions)
                 (inc cycle)
                 x
                 (conj signal-strengths (* cycle x)))
          "addx"
          (recur (rest instructions)
                 (+ cycle 2)
                 (+ x (read-string param))
                 (-> (conj signal-strengths (* cycle x))
                     (conj (* (inc cycle) x))))))
      (reverse signal-strengths))))

(defn part-1 [s]
  (->> (signal-strengths s)
       ((apply juxt (map (fn [x] #(nth % x)) [20 60 100 140 180 220])))
       (reduce +)))

(part-1 test-input) ; want 13140

(def input (slurp (io/resource "day10.txt")))

(part-1 input)

(defn render-cycle [cycle x]
  (let [col (mod (dec cycle) 40)]
    (when (zero? col)
      (print "\n"))
    (if (and (< (- x col) 2)
             (> (- x col) -2))
      (print "#")
      (print "."))))

(defn render [s]
  (loop [instructions (str/split-lines s)
         cycle 1
         x 1]
    (when (seq instructions)
      (render-cycle cycle x)
      (let [[cmd param] (str/split (first instructions) #" ")]
        (case cmd
          "noop"
          (recur (rest instructions)
                 (inc cycle)
                 x)
          "addx"
          (do
            (render-cycle (inc cycle) x)
            (recur (rest instructions)
                   (+ cycle 2)
                   (+ x (read-string param)))))))))

(render test-input) ;; want below ascii art

;; ##..##..##..##..##..##..##..##..##..##..
;; ###...###...###...###...###...###...###.
;; ####....####....####....####....####....
;; #####.....#####.....#####.....#####.....
;; ######......######......######......####
;; #######.......#######.......#######.....

(render input)
