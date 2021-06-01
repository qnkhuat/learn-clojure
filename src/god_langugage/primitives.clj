; Map
{
 :name "Ngoc"
 :sex "male"
 }

(get {:name "Ngoc" :sex "Male"} :sex)
(:a {:a 1 :b 2 :c 3}) ; => 1. Equivalent to (get {:a 1 :b 2 :c 3} :a)

; Vector
(get [3 2 1] 1) ; => 2
(def v (vector "creepy" "full" "moon"))
(conj [1 2 3] 4) ; => Append 4 to the vector

; List
'(1 2 3 4)
(nth '(:a :b :c) 0)
(list 1 "two" {3 4})
(conj '(1 2 3) 4)


; Sets
#{"Ngoc" 30} ; Yield error if duplicates key
(hash-set 1 1 2 2) ; Auto remove duplications
(contains? #{:a :b} :a)
(get #{:a :b} :a)

; Define function
(defn too-enthusiastic
  "Document about the god langugage"
  [name]
  (str "Holy cow I'm learning this " name " can you believe it"))

(too-enthusiastic "ngoc")

(defn multi-arity
  ([arg1 arg2 arg3]
   (str "This will run when 3 args is provided"))

  ([arg1 arg2]
   (str "Run when two proived insteads"))

  )

; Args as a list
(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))

(favorite-things "Doreen" "gum" "shoes" "kara-te")

; Destructing 
(defn my-first
  [[first-thing second-thing]] ; Notice that first-thing is within a vector
  (str first-thing " " second-thing))

(my-first ["oven"])

; Destruct map
(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

; Same result but different syntax
(defn announce-treasure-location
  [{:keys [lat lng] :as object}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))
  (println (str "The rest of keys" the-rest-of-keys))
  )

(announce-treasure-location {:lat "lat" :lng "lng"})


; Function Body
(defn illustrative-function
  []
  (+ 1 304)
  30
  "joe") ; => joe

(illustrative-function)

; Annonymous function
((fn [x] (* x x)) 3) ; => Square
(#(* % %) 2) ; => Square
(#(+ %1 %2) 2 3) ; => Sum 


((fn [x y] (+ x y)) 1 2)


; Take, drop
(take 3 [1 2 3 4 5 6 7 8 9 10]) ; => (1 2 3)
(drop 3 [1 2 3 4 5 6 7 8 9 10]) ; => (4 5 6 7 8 9 10)

(take-while #(< % 3) [ 1 2 3 4 5 6]) ; => (1 2)
(drop-while #(< % 3) [ 1 2 3 4 5 6]) ; => (3 4 5 6)

; Lazy seq
(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? false :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 1))
(time (def mapped-details (map vampire-related-details (range 0 1000000))))
(time (first mapped-details))
; Lazy is when you call for example : (map vampire-related-details (range 0 1000000)) it doesn't evaluate this => Unrealized seq
; Instead it creates a recipe for access
; But when you try to access it with (I.e first) then it will apply the recipe (vampire-related-details ) to the first elem
; So now the unrealized elem first will be evaluted and turn into a realized elem and return to you

; This ability come form lazy-seq which is a seq but it's only be evaluated when you access it
; With this we have the ability to create an infinite seq like the seq even-numbers below
; We can also create an infnite seq using repeat or repeatedly
(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 10 (even-numbers)) ; Take first 10 elem of the lazy-seq even-numbers

















