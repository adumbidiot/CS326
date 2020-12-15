% Question 1
%
% Test: 
% reverseList([1,2,3], L1).
% L1 = [3,2,1]
%
reverseList([], []).
reverseList([H | T], O) :- 
    reverseList(T, TR), 
    append(TR, [H], O).

% Question 2
%
% Test:
% take([5,1,2,7], 3, L1).
% L1 = [5,1,2]
% 
% Test: 
% take([5,1,2,7], 10, L1).
% L1 = [5,1,2,7]
%
take(_, 0, []).
take([], _, []).
take([H | T], N, O) :- 
    N1 is N - 1,
    take(T, N1, T1),
    append([H], T1, O).
    
% Question 3a
%
% Test:
% nleaves(node(1, node(2, node(3,nil,nil), node(4,nil,nil)), node(5,nil,nil)), N).
% N = 3
%
nleaves(nil, 0).
nleaves(node(_, nil, nil), 1).
nleaves(node(_, L, R), N) :-
    nleaves(L, NL),
    nleaves(R, NR),
    N is NL + NR.

% Question 3b
%
% Test:
% treeMember(3, node(1, node(2, node(3,nil,nil), node(4,nil,nil)), node(5,nil,nil))).
% Yes
%
treeMember(E, node(E, _, _)).
treeMember(E, node(_, L, _)) :- treeMember(E, L).
treeMember(E, node(_, _, R)) :- treeMember(E, R).

% Question 3c
%
% Test:
% preOrder(node(1, node(2, node(3,nil,nil), node(4,nil,nil)), node(5,nil,nil)), L).
% L = [1, 2, 3, 4, 5] 
%
preOrder(nil, []).
preOrder(node(V, R, L), O) :-
    preOrder(R, OL),
    preOrder(L, OR),
    append([V], OL, O1),
    append(O1, OR, O).
    
% Question 3c
%
% Test: 
% height(node(1, node(2, node(3,nil,nil), node(4,nil,nil)), node(5,nil,nil)), N).
% N = 3
%
height(nil, 0).
height(node(_, L, R), N) :-
    height(L, LN),
    height(R, RN),
    N1 is max(LN, RN),
    N is N1 + 1.
    
% Question 4
%
% Test:
% insert(5, [1,3,4,7], L1).
% L1 = [1,3,4,5,7]
%
insert(X,[],[X]).
insert(X, [H | T], L1) :- 
    H >= X,
    !,
    append([X, H], T, L1).
insert(X, [H | T], L1) :- 
    H < X,
    !,
    insert(X, T, I),
    append([H], I, L1).
    
% Question 5
%
% Test:
% flattenList([1, [2, [3, 4]], 5], L).
% L = [1, 2, 3, 4, 5]
%
flattenList([], []).
flattenList([A], B) :- flattenList(A, B).
flattenList([H | T], B) :- 
    flattenList(H, FH),
    flattenList(T, FT),
    append(FH, FT, B).
flattenList(A, [A]).
    
