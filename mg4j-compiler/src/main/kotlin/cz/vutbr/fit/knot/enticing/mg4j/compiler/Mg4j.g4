grammar Mg4j;

query: node EOF;

node
    : PAREN_LEFT node PAREN_RIGHT
    | node boolOp node
    | unaryOp node
    | constraint
    ;

constraint
    : reference relOp reference
    | reference relOp WORD
    ;

reference
    : NUMBER DOT WORD
    ;


boolOp
    : OR
    | AND
    ;

unaryOp
    : NOT
    ;

relOp
    : EQ
    | NEQ
    | LT
    | LE
    | GT
    | GE
    ;


EQ: '=';
NEQ: '!=';
LT: '<';
LE: '<=';
GT : '>';
GE :'>=';
PAREN_LEFT : '(';
PAREN_RIGHT : ')';
DOT: '.';
OR: '|' | 'or';
AND: '&' | 'and';
NOT: '!' | 'not';
NUMBER : [0-9]+;
WORD : [a-zA-Z0-9]+;

WS : [ \t] -> skip;
