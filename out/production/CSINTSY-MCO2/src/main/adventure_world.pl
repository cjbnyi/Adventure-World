% Knowledge Base


% Sensors
is_safe() :-
    todo.

has_breeze(X1, Y1) :-
    todo.


% Goals


% Unclassified
distance(X1, Y1, X2, Y2, D) :-
    abs(X1 - X2) + abs(Y1 - Y2) =:= D.

adjacent(X1, Y1, X2, Y2) :-
    distance(X1, Y1, X2, Y2, 1).

is_safe_tile() :-
    is_safe().



































