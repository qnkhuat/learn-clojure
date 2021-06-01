(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards" " am" "I" str))

(def addition-list (list + 1 2))

(eval (read-string "(defn square [x] (* x x))"))

(eval (read-string "(square 2)"))


(eval addition-list)
; In clojure we can write code that evalute itself
; In others programming langugage often there is a compiler to compile code to AST and then have an engine to evalute the AST. But Clojure doesn't have to, it evalutes its own structure
; This referered to the homoiconic characteristic of Clojure (inerhited from Lisp) program
; The Clojure data structure is actually a lisp
; If you think of this you can totally write your own REPL in lisp which only call read-string then eval
(def add2 (list + 1 2))
(eval add2) ; => 3
(eval '(+ 1 2)) ; => 3
(eval (read-string "(+ 1 2)")); => 3


(defmacro infix
  "Enalbe expression like (1 + 2)"
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

(defmacro infix-2
  [[operand1 op operand2]]
  (list op operand1 operand2))

(macroexpand '(infix (1 + 1))) ; => (+ 1 1)


(defmacro and
  "Evaluates exprs one at a time, from left to right. If a form
  returns logical false (nil or false), and returns that value and
  doesn't evaluate any of the other expressions, otherwise it returns
  the value of the last expr. (and) returns true."
  {:added "1.0"}
  ([] true)
  ([x] x)
  ([x & next]
   `(let [and# ~x]
      (if and# (and ~@next) and#))))


(defmacro my-println
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        'result))


(defmacro code-critic
  [bad good]
  (list 'do
        (list 'println
              "This is bad code: "
              (list 'quote bad))
        (list 'pirntln "This is good code: "
              (list 'quote good))))

(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(do (println "Great squid of Madrid, this is bad code:"
                (quote ~bad))
       (println "Sweet gorilla of Manila, this is good code:"
                (quote ~good))))


(def message "Good job!")
(defmacro with-mischief
  [& stuff-to-do]
  (concat (list 'let ['message "Oh, big deal!"])
          stuff-to-do))
; Avoid Variable Capture with gensym
; variable capture happens when in your macro there is a variable that are not unique and might be mistaken with user variables
; Use geynsym ensure there is an unique 'message name, so you can avoid Variable capture problem
(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message (gensym 'message)]
    `(let [~macro-message "Oh, big deal!"]
       ~@stuff-to-do
       (println "I still need to say: " ~macro-message))))


(defmacro without-mischief
  [& stuff-to-do]
  `(let [macro-message# "Oh, big deal!"]
     ~@stuff-to-do
     (println "I still need to say: " macro-message#)
     )
  )
; message# is like gensym, it generate unique name

(without-mischief
  (println "Here's how I feel about that thing you did: " message))


; Double evaluation
(defmacro report
  [to-try]
  `(if ~to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was not successful:" ~to-try)))
(report (do (Thread/sleep 1000) (+ 1 1))); This will be evaluate at least 2 times which is redundant in this macro


; Use this instead
(defmacro report
  [to-try]
  `(let [result# ~to-try]
     (if result#
       (println (quote ~to-try) "was successful:" result#)
       (println (quote ~to-try) "was not successful:" result#))))


(def order-details
  {:name "Mitchard Blimmons"
   :email "mitchard.blimmonsgmail.com"})

(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty

    "Your email address doesn't look like an email address"
    #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))


(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))










