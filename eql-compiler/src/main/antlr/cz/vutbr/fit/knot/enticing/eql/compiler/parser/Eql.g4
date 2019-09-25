grammar Eql;

@header {
    package cz.vutbr.fit.knot.enticing.eql.compiler.parser;
}

root: query (CONSTRAINT_SEPARATOR globalConstraint)? EOF;

query: queryElem+ context? ;

queryElem:
    NOT queryElem #not
    | IDENTIFIER COLON EQ queryElem #assign
    |(IDENTIFIER | ANY_TEXT | interval) (EXPONENT queryElem)? #simpleText
    | IDENTIFIER COLON queryElem #index
    | IDENTIFIER DOT IDENTIFIER COLON queryElem #attribute
    | PAREN_OPEN query PAREN_CLOSE (proximity | context)? #paren
    | queryElem booleanOperator queryElem #boolean
    | queryElem LT queryElem #order
    | QUOTATION queryElem QUOTATION #sequence
    ;

interval: BRACKET_OPEN (ANY_TEXT|IDENTIFIER) DOUBLE_DOT (ANY_TEXT|IDENTIFIER) BRACKET_CLOSE; // don't forget that it actually has to be a number or date!

proximity: SIMILARITY IDENTIFIER ; // don't forget that it actually has to be a number!

context: MINUS (queryElem | PAR | SENT);

globalConstraint: booleanExpression;

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
ANY_TEXT: ~[ \u005B\u005D\t\r&|=<>:.()*^-]+[*]?;

/** ignore whitespace */
WS : [ \t\r] -> skip;


