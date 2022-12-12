(ns aoc22.day7
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.java.io :as io]
            [clojure.walk :refer [postwalk]]
            [medley.core :as m]))

(def test-input "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(def dir-structure
  {:name "/"
   :children
   [{:name "a"
     :children
     [{:name "e"
       :children
       [{:name "i"
         :size 584}]}]}]})

(def expression-tree
  {:children
   [{:size 10}
    {:children
     [{:size 20}
      {:size 30}]}]})

(defn evaluate [node]
  (if (and (map? node) (not (:size node)))
    (assoc node :size
           (->> (:children node)
                (map :size)
                (reduce +)))
    node))

(postwalk evaluate expression-tree)

'(("$ cd /")
  ("$ ls" "dir a" "14848514 b.txt" "8504156 c.dat" "dir d")
  ("$ cd a")
  ("$ ls" "dir e" "29116 f" "2557 g" "62596 h.lst")
  ("$ cd e")
  ("$ ls" "584 i")
  ("$ cd .." "$ cd .." "$ cd d")
  ("$ ls" "4060174 j" "8033020 d.log" "5626152 d.ext" "7214296 k"))



(defn part-1 [s]
  (loop [[cmd & next] #_(partition-by #(and (not= % "$ ls") (str/starts-with? % "$"))) (str/split-lines s)
         stack '()
         size-under-100k 0]
    (cond
      (nil? cmd)
      size-under-100k
      (str/starts-with? cmd "$ cd")
      (if (str/ends-with? cmd "..")
        (let [[head parent & rest] stack
              head-size (reduce + (vals (:files head)))]
          (recur next
                 (conj rest (assoc-in parent [:files (:name head)] head-size))
                 (if (<= head-size 100000)
                   (+ size-under-100k head-size)
                   size-under-100k)))
        (recur next
               (conj stack {:name (second (str/split cmd #" cd "))})
               size-under-100k))
      (= cmd "$ ls")
      (let [[head & rest] stack
            files+dirs (take-while #(not (str/starts-with? % "$")) next)
            files (->> files+dirs
                       (map #(str/split % #" "))
                       (filter #(not= (first %) "dir"))
                       (map (fn [[k v]] [v (read-string k)]))
                       (into {}))]
        (recur (drop (count files+dirs) next)
               (conj rest (assoc head :files files))
               size-under-100k)))))

(part-1 test-input) ; want 95437

(def input (slurp (io/resource "day7.txt")))

(part-1 input)

(defn part-2 [s]
  )

(part-2 test-input) ; want ?

(part-2 input)
