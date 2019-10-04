grammar Eql;

@header {
    package cz.vutbr.fit.knot.enticing.eql.compiler.parser;
}

root: query (CONSTRAINT_SEPARATOR globalConstraint)? EOF;

query: queryElem+ restrictionType?;

queryElem:
    NOT queryElem #notQuery
    |(RAW |IDENTIFIER | ANY_TEXT | interval) #simpleQuery
    | IDENTIFIER COLON queryElem #index
    | IDENTIFIER DOT IDENTIFIER COLON queryElem #attribute
    | queryElem EXPONENT queryElem #align
    | PAREN_OPEN query PAREN_CLOSE restrictionType? #parenQuery
    | queryElem booleanOperator queryElem #booleanQuery
    | queryElem LT queryElem #order
    | QUOTATION queryElem+ QUOTATION #sequence
    | IDENTIFIER COLON EQ queryElem #assign
    | queryElem queryElem restrictionType #restriction
    ;

restrictionType
    : SIMILARITY IDENTIFIER #proximity // don't forget that it actually has to be a number!
    | MINUS (queryElem | PAR | SENT) #context
    ;

interval: BRACKET_OPEN (ANY_TEXT|IDENTIFIER) DOUBLE_DOT (ANY_TEXT|IDENTIFIER) BRACKET_CLOSE; // don't forget that it actually has to be a number or date!

globalConstraint: booleanExpression;

booleanExpression:
    comparison #simpleComparison
    | NOT booleanExpression #notExpression
    | PAREN_OPEN booleanExpression PAREN_CLOSE #parenExpression
    | booleanExpression booleanOperator booleanExpression #binaryExpression
    ;

comparison: reference comparisonOperator reference;

reference: IDENTIFIER (DOT IDENTIFIER)?;

booleanOperator: AND | OR ;
comparisonOperator: EQ | NE | GT |  GE | LT | LE ;

RAW: [']~[']+['];

CONSTRAINT_SEPARATOR: '&&';

COLON:':';
DOUBLE_DOT:'..';
DOT: '.';
EQ: '=';
NE: '!=';
GT: '>';
GE: '>=';
LT: '<';
LE: '<=';
EXPONENT: '^';
SIMILARITY:'~';
SENT: '_SENT_';
PAR: '_PAR_';
NOT: '!';
AND: '&';
OR: '|';
PAREN_OPEN : '(';
PAREN_CLOSE : ')';
BRACKET_OPEN: '[';
BRACKET_CLOSE: ']';
MINUS:'-';
QUOTATION: '"';



IDENTIFIER: [a-zA-Z0-9][a-zA-Z0-9_]*;
ANY_TEXT: ~[ "'\u005B\u005D\t\r&|=<>:.()*^-]+[*]? ;


/** ignore whitespace */
WS : [ \t\r] -> skip;


