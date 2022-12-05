(ns aoc22.day5
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn ->move [s]
  (let [[_ n from to]
        (->> (re-find #"move (\d+) from (\d+) to (\d+)" s)
             (map read-string))]
    {:n n
     :from (dec from)
     :to (dec to)}))

(defn move-crates
  [reverse?
   start
   {:keys [n from to]}]
  (-> start
      (update to
              (fn [stack]
                (let [new (take n (nth start from))]
                  (if reverse?
                    (concat (reverse new) stack)
                    (concat new stack)))))
      (update from #(drop n %))))

(defn run [s]
  (let [[start moves] (str/split s #"\n\n")
        start
        (->> (str/split-lines start)
             (map #(map second (partition 3 4 %)))
             (apply mapv list)
             (map #(filter (complement #{\space}) %))
             (map butlast)
             vec)
        moves
        (->> (str/split-lines moves)
             (map ->move))]
    [(->> (reduce (partial move-crates true) start moves)
          (map first)
          (apply str))
     (->> (reduce (partial move-crates false) start moves)
          (map first)
          (apply str))]))

(def input (slurp (io/resource "day5.txt")))

(run input)

(comment
  (def test-input "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

  (run test-input) ; want [CMZ, MCD]
  )
