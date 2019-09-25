grammar New;

root: query (CONSTRAINT_SEPARATOR constraint)? EOF;

query: queryElem+ ;

queryElem:
    (IDENTIFIER | ANY_TEXT) #simpleText
    | IDENTIFIER COLON queryElem #index
    | IDENTIFIER DOT IDENTIFIER COLON queryElem #attribute
    | PAREN_OPEN query PAREN_CLOSE #paren
    | queryElem booleanOperator queryElem #boolean
    | QUOTATION queryElem QUOTATION #sequence
    ;

constraint: booleanExpression;

booleanExpression:
    NOT? comparison
    | PAREN_OPEN booleanExpression PAREN_CLOSE
    | comparison booleanOperator comparison;

comparison: reference comparisonOperator reference;

reference: IDENTIFIER (DOT IDENTIFIER)?;




booleanOperator: AND | OR ;
comparisonOperator: EQ | NE | GT |  GE | LT | LE ;

CONSTRAINT_SEPARATOR: '&&';

COLON:':';
DOT: '.';
EQ: '=';
NE: '!=';
GT: '>';
GE: '>=';
LT: '<';
LE: '<=';

NOT: '!';
AND: '&';
OR: '|';
PAREN_OPEN : '(';
PAREN_CLOSE : ')';

QUOTATION: '"';


IDENTIFIER: [a-zA-Z0-9][a-zA-Z0-9_]*;
ANY_TEXT: ~[ \t\r&|=<>:.()*]+[*]?;

/** ignore whitespace */
WS : [ \t\r] -> skip;


