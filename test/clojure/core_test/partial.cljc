(ns clojure.core-test.partial
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability :as p]))

 (defn test-fn [& args]
   (into [] args))

(p/when-var-exists clojure.core/partial
  (deftest test-partial
    (let [simple-use (partial inc 2)]
      (is (= 3 (simple-use))))
    (let [lazily-evaluated (partial inc 2 3)]
      #?(:clj  (is (thrown? Exception (lazily-evaluated)))
         :cljs (is (thrown? js/Error  (lazily-evaluated)))))
    (let [variadic (partial test-fn 1 2 3)]
      (is (= [1 2 3 4]   (variadic 4)))
      (is (= [1 2 3 4 5] (variadic 4 5))))
    (let [infinite-sequence (partial #(take %2 %1) (range))]
      (is (= '(0 1 2 3 4) (infinite-sequence 5)))
      (is (= '(0 1 2) (infinite-sequence 3))))
    (let [partial-partial ((partial partial) test-fn)
          pppartial (partial partial-partial :inner)]
      (is (= [:inner :outer] (partial-partial :inner :outer)))
      (is (= [:inner :outer] (pppartial :outer))))
    (let [seq-of-partials (map #(partial * %1 %2) (range) (range))]
      (is (= (map #(* % % %) (range 5))
             (map #(%1 %2) seq-of-partials (range 5)))))))
