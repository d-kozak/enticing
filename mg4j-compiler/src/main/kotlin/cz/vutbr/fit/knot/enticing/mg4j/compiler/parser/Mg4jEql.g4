grammar Mg4jEql;

/**
 * Parser rules
 */
/** start rule of the grammar, represents the whole search query with constraints */
root: query (QUERY_CONSTRAINT_SEPARATOR constraint)? EOF;


/**
* Serch query rules
*/

/** search query without constraints */
query: queryElem+;

/** one element in the search query */
queryElem: assignment? queryCore alignOperator? limitation?;

/** by core it is meant that this element does not contain any 'decorations' (identifier,limitation) */
queryCore
    : QUOTATION queryElem+ QUOTATION # sequence
    | indexOperator? literal # lit
    | PAREN_LEFT queryElem+ PAREN_RIGHT # paren
    | queryCore LT queryElem # order
    | queryCore binaryOperator queryElem # binaryOperation
    | unaryOperator queryElem # unaryOperation
    ;



/** align operator to express queries over multiple indexes in the same document position */
alignOperator : EXPONENT queryElem;

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
literal: WORD | NUMBER;

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