; Nathaniel Daniel
; CS 326
; 9-23-2020
;
; load: 
; (load "hw2.scm")

; Util
(define (contains L val)
    (cond 
        ((null? L) #f)
        ((= 1 (length L)) (= (car L) val)) 
        (else (or 
            (contains (cdr L) val) 
            (= (car L) val)
        ))
    )
)

; test1
; (filter-list '(1 2 3 4 5) 3) ; (1 2 4 5)
;
(define (filter-list L V)
    (cond 
        ((null? L) '())
        ((= V (car L)) (filter-list (cdr L) V))
        (else (append (list (car L)) (filter-list (cdr L) V)))
    )
)

; 1A
;
; test1: 
; (is-set? '(1 2 5))   ; #t
;
; test2: 
; (is-set? '(1 5 2 5)) ; #f
;
(define (is-set? L) 
    (cond 
        ((null? L) #t)
        ((= 2 (length L)) 
            (not (= (car L) (car (cdr L))))
        )
        (else (and 
            (is-set (cdr L))
            (not (contains (cdr L) (car L)))
        ))
    )
)

; 1B
;
; test1: 
; (make-set '(4 3 5 3 4 1)) ; (5 3 4 1)
;
(define (make-set L) 
    (cond
        ((null? L) '())
        ((contains (cdr L) (car L)) (make-set (cdr L)))
        (else (cons (car L) (make-set (cdr L))))
    )
)

; 1C
;
; test1: 
; (subset? '(1 2 3 4 5) '()) ; #f
;
; test2: 
; (subset? '(1 2 3 4 5) '(1 2 3 4 5 6)) ; #t
;
; test3: 
; (subset? '() '(1 2 3 4 5 6)) ; #t
;
(define (subset? A S)
    (cond
        ((null? A) #t)
        (else (and 
            (contains S (car A))
            (subset? (cdr A) S))
        )
     )
)

; 1D
;
; test1:
; (union '(1 2 3) '(4 5 6)) ; '(1 2 3 4 5 6)
;
; test2:
; (union '(1 2 3) '(1 4 3)) ; '(2 1 4 3)
;
(define (union A B) 
    (make-set (append A B))
)

; 1E
;
; test1:
; (intersection '(1 2 3) '(4 5 6)) ; ()
;
; test2:
; (intersection '(1 2 3) '(1 5 6)) ; (1)
;
; test3:
; (intersection '(1 2 3) '(2 5 1)) ; (1 2)
;
(define (intersection A B)
    (cond
        ((or (null? A) (null? B)) '())
        ((contains B (car A)) (cons (car A) (intersection (cdr A) B)))
        (else (intersection (cdr A) B))
    )
)

; 2 Auxiallary Funcs

; test1:
; (val '(13 (5 (1 () ()) (8 () (9 () ())))(22 (17 () ()) (25 () ())))) ; 13
;
(define (val T)
    (car T)
)

; test1:
; (left '(13 (5 (1 () ()) (8 () (9 () ())))(22 (17 () ()) (25 () ())))) ; (5 (1 () ()) (8 () (9 () ())))
;
(define (left T)
    (car (cdr T))
)

; test1:
; (right '(13 (5 (1 () ()) (8 () (9 () ())))(22 (17 () ()) (25 () ())))) ; (22 (17 () ()) (25 () ())) 
;
(define (right T)
    (car (cdr (cdr T)))
)

; 2A
;
; test1
; (tree-member? 17 '(13 (5 (1 () ()) (8 () (9 () ())))(22 (17 () ()) (25 () ())))) ; #t
;
(define (tree-member? V T)
    (cond
        ((null? T) #f)
        (else 
            (or 
                (= V (val T))
                (tree-member? V (left T)) 
                (tree-member? V (right T))
            )
        )
    )
)

; 2B
;
; test1:
; (preorder '(13 (5 (1 () ()) (8 () (9 () ())))(22 (17 () ()) (25 () ())))) ; (13 5 1 8 9 22 17 25)
;
(define (preorder T)
    (cond 
        ((null? T) '())
        (else (append (cons (val T) (preorder (left T))) (preorder (right T))))
    )
)

; 2C
; 
; test1:
; (inorder '(13 (5 (1 () ()) (8 () (9 () ())))(22 (17 () ()) (25 () ())))) ; (1 5 8 9 13 17 22 25)
;
(define (inorder T)
    (cond 
        ((null? T) '())
        (else (append (inorder (left T)) (list (val T)) (inorder (right T))))
    )
)

; 3
;
; test1
; 
; (deep-delete 3 '(1 2 3 (4 3) 5 (6 (3 7)) 8)) ; (1 2 (4) 5 (6 (7)) 8)
;
(define (deep-delete V L)
    (cond
        ((null? L) '())
        ((list? L) 
            (append 
                (if (null? (deep-delete V (car L))) '() (list (deep-delete V (car L))))
                (deep-delete V (cdr L))
            )
        )
        ((= V L) '())
        (else L)
    )
)

