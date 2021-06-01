(defn main
  []
  (do
    (println "User =>")
    (println (eval (read-string (read-line))))
    (main)))

(main)
