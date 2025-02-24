(ns clojure.string-test.upper-case
  (:require [clojure.string :as str]
            [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer)  [when-var-exists]]))

(when-var-exists str/upper-case
  (deftest test-upper-case
    (is (thrown? #?(:clj Exception) (str/upper-case nil)))
    (is (= "" (str/upper-case "")))
    (is (= "ASDF" (str/upper-case "aSDf")))
    (is (= "ASDF" (str/upper-case "ASDF")))
    (let [s "asdf"]
      (is (= "ASDF" (str/upper-case "asdf")))
      (is (= "asdf" s) "original string mutated"))
    (is (= ":ASDF" (str/upper-case :asdf)))
    (is (= ":ASDF/ASDF" (str/upper-case :asdf/asdf)))
    (is (= "ASDF" (str/upper-case 'asdf)))))
