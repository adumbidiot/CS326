; HW3
; Nathaniel Daniel
; CS 326
; Load: (load "hw3.scm")
; Test: (test)

(define x 0)

(define (print-x)
    (display x)(newline)
)

(define (print-x-shadowed f)
    (define x 1)
    (print-x)
)

(define (test) 
    (display "X: ")
    (print-x)
    
    (display "Shadowed X: ")
    (print-x-shadowed print-x)
    
    '()
)