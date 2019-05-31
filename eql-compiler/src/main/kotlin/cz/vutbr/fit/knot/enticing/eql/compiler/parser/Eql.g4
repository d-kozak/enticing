grammar Eql;

/**
 * Parser rules
 */
/** start rule of the grammar, represents the whole search query with constraints */
root: query (QUERY_CONSTRAINT_SEPARATOR constraint)? EOF;


/**
* Serch query rules
*/

/** search query without constraints */
query: (assignment? queryExpression alignOperator? limitation? )+;

queryExpression
    : assignment? QUOTATION query QUOTATION alignOperator? limitation? # sequence
    | assignment? indexOperator? queryLiteral # literal
    | assignment? PAREN_LEFT query PAREN_RIGHT alignOperator? limitation? #paren
    | queryExpression LT queryExpression # order
    | assignment queryExpression alignOperator? limitation? LT assignment? queryExpression alignOperator? limitation? # order
    | assignment queryExpression alignOperator? limitation? binaryOperator assignment? queryExpression alignOperator? limitation? # binaryOperation
    | queryExpression alignOperator? limitation? binaryOperator assignment? queryExpression alignOperator? limitation? # binaryOperation
    | unaryOperator queryExpression alignOperator? limitation? # unaryOperation
    ;

/** align operator to express queries over multiple indexes in the same document position */
alignOperator : EXPONENT query;

/** assignment of part of the query to be used in global constraints */
assignment: WORD ARROW;

/** to express limitations with respect to position in the document */
limitation
    : MINUS PAR # par
    | MINUS SENT # sent
    | SIMILARITY NUMBER # proximity
    ;

/** for accessing a different index */
indexOperator: (WORD DOT)* WORD COLON;

/** literals of the query language */
queryLiteral: WORD | NUMBER;

/**
* Constraints rules
*/

/** global contraints */
constraint
    : PAREN_LEFT constraint PAREN_RIGHT
    | reference comparisonOperator reference
    | reference comparisonOperator WORD
    | constraint binaryOperator constraint
    | unaryOperator constraint
    ;

/** reference to a part of the search query */
reference : WORD DOT WORD;

/**
* Shared rules
*/

/** comparison operators */
comparisonOperator
    : EQ
    | NEQ
    | LT
    | LE
    | GT
    | GE
    ;

/** binary operators */
binaryOperator
    : AND
    | OR
    ;

/** unary operators */
unaryOperator
   : NOT
   ;


/**
 * Lexer rules
 */
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


NUMBER : [0-9]+;
WORD : ANY_VALID_CHAR+ WILDCARD?;

fragment ANY_VALID_CHAR : [a-zA-Z0-9_];
fragment WILDCARD:'*';

/** ignore whitespace */
WS : [ \t] -> skip;