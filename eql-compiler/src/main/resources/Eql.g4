grammar Eql;

/**
 * Parser rules
 */
/** start rule of the grammar, represents the whole search query with global constraints */
root: query  (QUERY_CONSTRAINT_SEPARATOR constraint)? EOF;


/**
* Serch query rules
*/
query: queryElem+ proximity? contextConstraint? ;

queryElem
    : assignment? QUOTATION queryElem+ QUOTATION #sequence
    | assignment? indexOperator? queryLiteral alignOperator? #indexWithSingleValue
    | assignment? indexOperator PAREN_LEFT WORD (OR WORD)* PAREN_RIGHT alignOperator? #indexWithMultipleValues
    | assignment? PAREN_LEFT queryElem+ PAREN_RIGHT proximity? #paren
    | assignment queryElem LT queryElem proximity? # order
    | queryElem LT queryElem proximity? # order
    | queryElem binaryOperator queryElem # binaryOperation
    | assignment? unaryOperator queryElem #unaryOperation
    ;

proximity: SIMILARITY NUMBER ;

/** align operator to express queries over multiple indexes in the same document position */
alignOperator : (EXPONENT alignElem)+;

alignElem
    : WORD COLON queryLiteral # index
    | PAREN_LEFT WORD COLON queryLiteral PAREN_RIGHT # index
    | WORD DOT WORD COLON  queryLiteral # nertag
    | PAREN_LEFT WORD DOT WORD COLON  queryLiteral PAREN_RIGHT # nertag
    ;

/** assignment of part of the query to be used in global constraints */
assignment: (NUMBER | WORD) ASSIGN;

/** to express limitations with respect to position in the document */
contextConstraint
    : MINUS PAR # par
    | MINUS SENT # sent
    ;

/** for accessing a different index */
indexOperator: WORD COLON;

/** literals of the query language */
queryLiteral: WORD | NUMBER | interval;

interval
    : BRACKET_LEFT NUMBER RANGE NUMBER BRACKET_RIGHT #numberRange
    | BRACKET_LEFT DATE? RANGE DATE? BRACKET_RIGHT #dateRange
    ;

/**
* Constraints rules
*/

/** global contraints */
constraint
    : PAREN_LEFT constraint PAREN_RIGHT
    | reference comparisonOperator reference
    | constraint binaryOperator constraint
    | unaryOperator constraint
    ;

/** reference to a part of the search query */
reference : (WORD|NUMBER) DOT WORD;

/**
* Shared rules
*/

/** comparison operators */
comparisonOperator
    : EQ
    | NEQ
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
ASSIGN: ':=';
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
PAREN_LEFT : '(';
PAREN_RIGHT : ')';
BRACKET_LEFT: '[';
BRACKET_RIGHT: ']';
RANGE: '..';
DOT: '.';
OR: '|';
AND: '&';
NOT: '!';


NUMBER : [0-9]+;
DATE: [0-9]+'/'[0-9]+'/'[0-9]+;
WORD : ANY_VALID_CHAR+ WILDCARD?;

fragment ANY_VALID_CHAR : [a-zA-Z0-9_];
fragment WILDCARD:'*';

/** ignore whitespace */
WS : [ \t] -> skip;