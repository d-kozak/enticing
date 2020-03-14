grammar Eql;

@header {
    package cz.vutbr.fit.knot.enticing.eql.compiler.parser;
}

root: queryElem (CONSTRAINT_SEPARATOR constraint)? EOF;

queryElem:
    IDENTIFIER COLON EQ queryElem #assign
    | NOT queryElem #notQuery
    |(RAW |IDENTIFIER | ANY_TEXT | interval) #simpleQuery
    | IDENTIFIER COLON queryElem #index
    | IDENTIFIER DOT IDENTIFIER COLON queryElem #attribute
    | queryElem EXPONENT queryElem #align
    | PAREN_OPEN queryElem PAREN_CLOSE proximity? #parenQuery
    | queryElem booleanOperator queryElem proximity? #booleanQuery
    | queryElem LT queryElem proximity? #order
    | QUOTATION queryElem+ QUOTATION #sequence
    | queryElem queryElem proximity? #tuple
    ;

proximity : SIMILARITY IDENTIFIER ; // don't forget that it actually has to be a number!

interval: BRACKET_OPEN (ANY_TEXT|IDENTIFIER) DOUBLE_DOT (ANY_TEXT|IDENTIFIER) BRACKET_CLOSE; // don't forget that it actually has to be a number or date!

constraint: booleanExpression;

booleanExpression:
    comparison #simpleComparison
    | NOT booleanExpression #notExpression
    | PAREN_OPEN booleanExpression PAREN_CLOSE #parenExpression
    | booleanExpression booleanOperator booleanExpression #binaryExpression
    ;

comparison: reference comparisonOperator referenceOrValue;

referenceOrValue: reference | nestedReference;

reference: IDENTIFIER (DOT nestedReference)?;

nestedReference: IDENTIFIER | RAW;

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



IDENTIFIER: [_]?[a-zA-Z0-9][a-zA-Z0-9_]*;
ANY_TEXT: ~[ "'\u005B\u005D\t\r&|=<>:.()*^-]+[*]? ;


/** ignore whitespace */
WS : [ \t\r] -> skip;


