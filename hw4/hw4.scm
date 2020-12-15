; HW4
; Nathaniel Daniel
; CS 326
; Load: (load "hw4.scm")

; Problem 4
;
; Test1: 
; (sum '(1 2 3 4 5 6 7 8 9)) ; 45
;
; Test2:
; (sum '()) ; 0
;
; Test3:
; (sum '(15 12 13 10)) ; 50
;
(define (sum L)
    (define (f L acc)
        (cond
            ((null? L) acc)
            (else 
                (f (cdr L) (+ acc (car L)))
            )
        )
    )
    (f L 0)
)