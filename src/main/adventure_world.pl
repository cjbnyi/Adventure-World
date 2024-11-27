:- dynamic is_winner/4.
:- dynamic pit/2.
:- dynamic gold/2.
:- dynamic breeze/2.
:- dynamic grab/2.
:- dynamic numRows/1.
:- dynamic numCols/1.

% Sensors
is_safe(X, Y) :-
    \+ pit(X, Y).

is_home(X, Y) :-
    \+ gold(X, Y),
    \+ breeze(X, Y),
    is_safe(X, Y).

is_pit(X, Y) :-
    pit(X, Y).

has_glitter(X1, Y1) :-
    gold(X1, Y1).

has_breeze(X1, Y1) :-
    numRows(SizeX),
    numCols(SizeY),
    ( Y1 > 0,             Up is Y1 - 1,    pit(X1, Up)
    ; Y1 < SizeY - 1,     Down is Y1 + 1,  pit(X1, Down)
    ; X1 > 0,             Left is X1 - 1,  pit(Left, Y1)
    ; X1 < SizeX - 1,     Right is X1 + 1, pit(Right, Y1)
    ).

% Determinants
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