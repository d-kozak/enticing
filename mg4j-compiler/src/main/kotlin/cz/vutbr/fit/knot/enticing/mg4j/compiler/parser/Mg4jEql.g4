grammar Mg4jEql;

/** start rule of the grammar, represents the whole search query with constraints */
root: query (QUERY_CONSTRAINT_SEPARATOR constraint)? EOF;

/** search query */
query: queryPart+;

/** one element in the query */
queryPart: identifier? queryCore alignOperator? limitation?;

/** by core it is meant that this element does not contain any 'decorations' (identifier,limitation)*/
queryCore
    : QUOTATION queryPart+ QUOTATION # sequence
    | indexOperator? literal # lit
    | PAREN_LEFT queryPart+ PAREN_RIGHT # paren
    | queryCore LT queryPart # order
    | queryCore binaryOperator queryPart # binaryOperation
    | unaryOperator queryPart # unaryOperation
    ;

literal: WORD | NUMBER;

alignOperator : EXPONENT queryPart;

identifier: WORD ARROW;

limitation
    : MINUS PAR # par
    | MINUS SENT # sent
    | SIMILARITY NUMBER # proximity
    ;

indexOperator: (WORD DOT)* WORD COLON;

constraint
    : PAREN_LEFT constraint PAREN_RIGHT
    | reference relOp reference
    | reference relOp WORD
    | constraint binaryOperator constraint
    | unaryOperator constraint
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

binaryOperator
    : AND
    | OR
    ;

unaryOperator
    : NOT
    ;


// tokens
ARROW: '<-';
MINUS:'-';
COLON:':';
EXPONENT: '^';
SIMILARITY:'~';
SENT: '_SENT_';
PAR: '_PAR_';
QUOTATION: '"';

QUERY_CONSTRAINT_SEPARATOR:'&&';

EQ: '=';
NEQ: '!=';
LT: '<';
LE: '<=';
GT : '>';
GE :'>=';
PAREN_LEFT : '(';
PAREN_RIGHT : ')';
DOT: '.';
OR: '|' | 'OR' | 'or';
AND: '&' | 'and';
NOT: '!' | 'not';

WS : [ \t] -> skip;

NUMBER : [0-9]+;
WORD : ANY_VALID_CHAR+ WILDCARD?;

fragment ANY_VALID_CHAR : [a-zA-Z0-9_];
fragment WILDCARD:'*';