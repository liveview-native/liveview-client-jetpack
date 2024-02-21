lexer grammar StylesheetLexer;

OPEN_MODIFIER          : '{:';
RIGHT_ARROW            : '=>';
PERCENT                : '%';
CURLY_BRACE_OPEN       : '{';
CURLY_BRACE_CLOSE      : '}';
BRACKET_OPEN           : '[';
BRACKET_CLOSE          : ']';
COLON                  : ':';
COMMA                  : ',';
DOT                    : '.';
NUMBER                 : DIGITS+ ;
STRING                 : '"' .*? '"';
ID                     : [a-zA-Z0-9\-]+ ;
WS                     : [ \t\r\n] -> skip ;

fragment LETTERS       : [a-zA-Z] ;
fragment DIGITS        : [0-9] ;