grammar Eql;

@header {
    package cz.vutbr.fit.knot.enticing.eql.compiler.parser;
}

root: queryElem (CONSTRAINT_SEPARATOR constraint)? EOF;

queryElem:
     NOT queryElem #notQuery
    | simpleQuery PLUS queryElem #next
    | IDENTIFIER COLON queryElem #index
    | IDENTIFIER DOT IDENTIFIER COLON queryElem #attribute
    | IDENTIFIER COLON EQ queryElem #assign
    | simpleQuery #simple
    | queryElem EXPONENT queryElem #align
    | PAREN_OPEN queryElem PAREN_CLOSE #parenQuery
    | queryElem LT queryElem  #order
    | queryElem OR queryElem  #or
    | queryElem AND? queryElem #and
    | QUOTATION simpleQuery+ QUOTATION #sequence
    | queryElem proximity #prox
    ;

simpleQuery: (RAW |IDENTIFIER | ANY_TEXT | interval) ;

proximity : SIMILARITY IDENTIFIER ; // it actually has to be a number!

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

PLUS:'+';
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
ANY_TEXT: ~[ +!"'\u005B\u005D\t\r&|=<>:.()*^-]+[*]? ;


/** ignore whitespace */
WS : [ \t\r] -> skip;


