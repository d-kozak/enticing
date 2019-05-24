grammar Mg4j;

expression: query (SEP constraintNode)? EOF;

query
    : queryNode MINUS queryNode
    | queryNode MINUS diff
    | queryNode;

queryNode
    : PAREN_LEFT queryNode PAREN_RIGHT
    | queryNode binaryOp queryNode
    | queryNode queryNode
    | unaryOp queryNode
    | queryNode LT queryNode
    | queryReference
    | nertagSem
    | indexTerm
    | proximity
    | sequence
    | WORD
    ;

indexTerm
    : WORD COLON simpleNode;

simpleNode
    : PAREN_LEFT simpleNode PAREN_RIGHT
    | WORD
    ;

nertagSem
    : WORD COLON WORD EXPONENT sem
    ;

sem
    : PAREN_LEFT sem PAREN_RIGHT
    | PAREN_LEFT sem EXPONENT sem PAREN_RIGHT
    | WORD DOT indexTerm
    ;

proximity
    : PAREN_LEFT queryNode PAREN_RIGHT SIMILARITY NUMBER
    ;

index
    : PAREN_LEFT index PAREN_RIGHT
    | index OR index
    | unaryOp index
    | NUMBER; // todo figure out what the index should be


diff
    :
    | SENT
    | PAR
    ;

queryReference
    : NUMBER COLON PAREN_LEFT nodeWithoutReference PAREN_RIGHT
    ;

sequence
    : QUOTATION arbTerm arb QUOTATION
    ;

arb
    : arbTerm arb
    | arbTermLast
    ;

arbTerm
    : PAREN_LEFT arbTermWithOp PAREN_RIGHT
    | queryReference
    | nertagSem
    | indexTerm
    | WORD
    ;

arbTermLast
    : PAREN_LEFT arbTermWithOp PAREN_RIGHT
    | queryReference
    | nertagSem
    | indexTerm
    | WORD
    ;

arbTermWithOp
    :  arbTerm binaryOp arb
    | unaryOp arbTerm binaryOp arb
    | unaryOp arbTerm arb
    | unaryOp arb
    | arbTerm arb
    ;

nodeWithoutReference
    :  PAREN_LEFT nodeWithoutReference PAREN_RIGHT
    | nodeWithoutReference binaryOp nodeWithoutReference
    | nodeWithoutReference nodeWithoutReference
    | unaryOp nodeWithoutReference
    | nodeWithoutReference LT nodeWithoutReference
    | nertagSem
    | indexTerm
    | proximity
    | sequence
    | WORD
    ;

constraintNode
    : PAREN_LEFT constraintNode PAREN_RIGHT
    | constraintNode binaryOp constraintNode
    | unaryOp constraintNode
    | constraint
    ;

constraint
    : reference relOp reference
    | reference relOp WORD
    ;

reference
    : NUMBER DOT WORD
    ;


binaryOp
    : OR
    | AND
    ;

unaryOp
    : NOT
    ;

relOp
    : EQ
    | NEQ
    | LT
    | LE
    | GT
    | GE
    ;


MINUS:'-';
COLON:':';
EXPONENT: '^';
SIMILARITY:'~';
SENT: '_SENT_';
PAR: '_PAR_';
QUOTATION: '"';

SEP:'&&';

EQ: '=';
NEQ: '!=';
LT: '<';
LE: '<=';
GT : '>';
GE :'>=';
PAREN_LEFT : '(';
PAREN_RIGHT : ')';
DOT: '.';
OR: '|' | 'or';
AND: '&' | 'and';
NOT: '!' | 'not';
NUMBER : [0-9]+;
WORD : [a-zA-Z0-9_]+;

WS : [ \t] -> skip;
