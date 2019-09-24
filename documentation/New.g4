grammar New;

root: query (CONSTRAINT_SEPARATOR constraint)? EOF;

query: queryElem+  ;

queryElem:
    (IDENTIFIER | ANY_TEXT)
    | IDENTIFIER COLON queryElem
    | IDENTIFIER DOT IDENTIFIER COLON queryElem
    | PAREN_OPEN query PAREN_CLOSE
    | queryElem booleanOperator queryElem
    | QUOTATION queryElem QUOTATION;

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
// todo handle special chars e.g. those from Czech alphabet
ANY_TEXT: [\p{Symbol}]+;

/** ignore whitespace */
WS : [ \t] -> skip;


