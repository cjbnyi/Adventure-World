:- dynamic grab/2.

% Sensors
is_safe(X, Y) :-
    \+ pit(X, Y).

is_home(X, Y) :-
    \+ gold(X, Y),
    \+ breeze(X, Y),
    is_safe(X, Y).

is_pit(X, Y) :-
    pit(X, Y).

has_breeze(X1, Y1) :-
    SizeX = 5,
    SizeY = 5,
    ( Y1 < SizeY, Up is Y1 - 1,    pit(X1, Up)
    ; Y1 > 0,     Down is Y1 + 1,  pit(X1, Down)
    ; X1 < SizeX, Left is X1 - 1,  pit(Left, Y1)
    ; X1 > 0,     Right is X1 + 1, pit(Right, Y1)
    ).

has_glitter(X1, Y1) :-
    gold(X1, Y1).

ungrabbed_gold(X1, Y1) :-
    gold(X1, Y1),
    \+ grab(X1, Y1).

% Goals
is_gold(X1, Y1) :-
    has_glitter(X1, Y1).

% Unclassified
distance(X1, Y1, X2, Y2, D) :-
    abs(X1 - X2) + abs(Y1 - Y2) =:= D.

adjacent(X1, Y1, X2, Y2) :-
    distance(X1, Y1, X2, Y2, 1).

is_safe_tile(X, Y) :-
    is_safe(X, Y).