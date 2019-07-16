grammar Eql;

/**
 * Parser rules
 */

/** start rule of the grammar, represents the whole search query with global constraints */
root: query  (GLOBAL_CONSTRAINT_SEPARATOR globalConstraint)? EOF;


/**
* Serch query rules
*/

/** seach query */
query: queryElem+ proximity? contextConstraint? ;

/** single element in the query*/
queryElem
    : assignment? QUOTATION queryElem+ QUOTATION # sequence
    | assignment? queryLiteral alignOperator? # literal
    | assignment? indexOperator singleValueOrMultiple alignOperator? # indexElem
    | assignment? PAREN_LEFT queryElem+ PAREN_RIGHT proximity? # paren
    | assignment queryElem LT queryElem proximity? # order
    | queryElem LT queryElem proximity? # order
    | queryElem logicBinaryOperator queryElem # logicBinaryOperation
    | assignment? logicUnaryOperator queryElem # logicUnaryOperation
    ;

/** specify required distance between two words */
proximity: SIMILARITY NUMBER ;

/** align operator to express queries over multiple indexes in the same document position */
alignOperator : (EXPONENT alignElem)+;

/** the other index to query */
alignElem
    : WORD COLON singleValueOrMultiple # index
    | PAREN_LEFT WORD COLON singleValueOrMultiple PAREN_RIGHT # index
    | WORD DOT WORD COLON  singleValueOrMultiple # entityAttribute
    | PAREN_LEFT WORD DOT WORD COLON  singleValueOrMultiple PAREN_RIGHT # entityAttribute
    ;

/** single value or a list of alternatives */
singleValueOrMultiple
    : literalOrInterval # singleValue
    | PAREN_LEFT literalOrInterval (OR literalOrInterval)* PAREN_RIGHT #multipleValues
    ;

/** either a literal or an interval */
literalOrInterval: queryLiteral | interval;

/** assignment to identify part of the query to be used in global constraints */
assignment: (NUMBER | WORD) ASSIGN;

/** to limit context */
contextConstraint
    : PAR # paragraph
    | SENT # sentence
    ;

/** acess a different index */
indexOperator: WORD COLON;

/** literals of the query language */
queryLiteral: WORD | NUMBER;

/** interval, can be used on indexes with enumerable values */
interval
    : BRACKET_LEFT NUMBER RANGE NUMBER BRACKET_RIGHT #numberRange
    | BRACKET_LEFT DATE? RANGE DATE? BRACKET_RIGHT #dateRange
    ;

/**
* Global constraints rules
*/

/** global contraints */
globalConstraint
    : PAREN_LEFT globalConstraint PAREN_RIGHT # parens
    | reference comparisonOperator reference # comparison
    | globalConstraint logicBinaryOperator globalConstraint # constraintLogicBinaryOperation
    | logicUnaryOperator globalConstraint # constraintLogicUnaryOperation
    ;

/** reference to a part of the search query */
reference : ((WORD|NUMBER) DOT)? WORD;

/**
* Shared rules
*/

/** comparison operators */
comparisonOperator
    : EQ
    | NEQ
    ;

/** binary operators */
logicBinaryOperator
    : AND
    | OR
    ;

/** unary operators */
logicUnaryOperator
   : NOT
   ;


/**
 * Lexer rules
 */


ASSIGN: ':=';
COLON:':';
EXPONENT: '^';
SIMILARITY:'~';
SENT: '- _SENT_';
PAR: '- _PAR_';
QUOTATION: '"';

GLOBAL_CONSTRAINT_SEPARATOR:'&&';

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