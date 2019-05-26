grammar Mg4jEql;


query: queryElem* (SEPARATOR constraint)? EOF;

queryElem
    : QUOTATION queryElem+ QUOTATION # sequence
    | (WORD COLON)? WORD # word
    | (WORD COLON)? WORD COLON WORD (EXPONENT attribute)* # nertag
    | PAREN_LEFT queryElem PAREN_RIGHT limitation? # paren
    | queryElem LT queryElem # orderOperation
    | queryElem binaryOp queryElem # binaryOperation
    | unaryOp queryElem # unaryOperation
    ;

limitation
    : MINUS PAR # par
    | MINUS SENT # sent
    | SIMILARITY NUMBER # proximity
    ;

attribute
    : PAREN_LEFT WORD DOT WORD COLON (WORD|NUMBER) PAREN_RIGHT
    ;

constraint
    : PAREN_LEFT constraint PAREN_RIGHT
    | reference relOp reference
    | reference relOp WORD
    | constraint binaryOp constraint
    | unaryOp constraint
    ;

reference : WORD DOT WORD;

relOp
    : EQ
    | NEQ
    | LT
    | LE
    | GT
    | GE
    ;

binaryOp
    : AND
    | OR
    ;

unaryOp
    : NOT
    ;



MINUS:'-';
COLON:':';
EXPONENT: '^';
SIMILARITY:'~';
SENT: '_SENT_';
PAR: '_PAR_';
QUOTATION: '"';

SEPARATOR:'&&';

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
WORD : [a-zA-Z0-9_]+;

WS : [ \t] -> skip;
