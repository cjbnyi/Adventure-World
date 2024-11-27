:- dynamic pit/2.
:- dynamic gold/2.
:- dynamic breeze/2.
:- dynamic grab/2.
:- dynamic numRows/1.
:- dynamic numCols/1.
:- dynamic adjacent_tiles/3.
:- dynamic visited/2.
:- dynamic is_winner/1.

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

adjacent_tile(X, Y, AdjX, AdjY) :-
    (   AdjX is X + 1, AdjY is Y ;  % right
        AdjX is X - 1, AdjY is Y ;  % left
        AdjX is X, AdjY is Y + 1 ;  % up
        AdjX is X, AdjY is Y - 1     % down
    ).

is_unsafe(X1, Y1) :-
    numRows(SizeX),
    numCols(SizeY),
    (Y1 > 0, Up is Y1 - 1, has_breeze(X1, Up), visited(X1, Up)),
    (Y1 < SizeY - 1, Down is Y1 + 1, has_breeze(X1, Down), visited(X1, Down)),
    (X1 > 0, Left is X1 - 1, has_breeze(Left, Y1), visited(Left, Y1)),
    (X1 < SizeX - 1, Right is X1 + 1, has_breeze(Right, Y1), visited(Right, Y1)).


is_winner(X) :-
    X > 2.

% Determinants
ungrabbed_gold(X1, Y1) :-
    gold(X1, Y1),
    \+ grab(X1, Y1).