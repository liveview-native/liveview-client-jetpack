// $antlr-format alignTrailingComments true, columnLimit 150, maxEmptyLinesToKeep 1, reflowComments false, useTab false
// $antlr-format allowShortRulesOnASingleLine true, allowShortBlocksOnASingleLine true, minEmptyLines 0, alignSemicolons ownLine
// $antlr-format alignColons trailing, singleLineOverrulesHangingColon true, alignLexerCommands true, alignLabels true, alignTrailers true

lexer grammar ElixirLexer;

COMMENT: '#' ~[\r\n]* -> skip;

NL: '\r'? '\n' | '\r';

SPACES: [ \t]+ -> channel(HIDDEN);

ATOM: ':' ( [\p{L}_] [\p{L}_0-9@]* [!?]? | OPERATOR | SINGLE_LINE_STRING | SINGLE_LINE_CHARLIST);
// Keywords
TRUE  : 'true';
FALSE : 'false';
NIL   : 'nil';

CODEPOINT: '?' ( '\\' . | ~[\\]);

SIGIL:
    '~' [a-zA-Z] (
        '/' ( ESCAPE | '\\/' | ~[/\\])* '/'
        | '|' ( ESCAPE | '\\|' | ~[|\\])* '|'
        | SINGLE_LINE_STRING
        | MULTI_LINE_STRING
        | SINGLE_LINE_CHARLIST
        | MULTI_LINE_CHARLIST
        | '\'' ( ESCAPE | '\\\'' | ~['\\])* '\''
        | '(' ( ESCAPE | '\\)' | ~[)\\])* ')'
        | '[' ( ESCAPE | '\\]' | ~[\]\\])* ']'
        | '{' ( ESCAPE | '\\}' | ~[}\\])* '}'
        | '<' ( ESCAPE | '\\>' | ~[>\\])* '>'
    ) [a-zA-Z]*
;

HEXADECIMAL : '0x' HEX+ ( '_' HEX+)*;
OCTAL       : '0o' [0-7]+ ( '_' [0-7]+)*;
BINARY      : '0b' [01]+ ( '_' [01]+)*;
INTEGER     : D+ ( '_' D+)*;
FLOAT       : D+ '.' D+ EXPONENT?;

SINGLE_LINE_STRING   : '"' ( ESCAPE | '\\"' | ~[\\"])* '"';
MULTI_LINE_STRING    : '"""' .*? '"""';
SINGLE_LINE_CHARLIST : '\'' ( ESCAPE | '\\\'' | ~[\\'])* '\'';
MULTI_LINE_CHARLIST  : '\'\'\'' .*? '\'\'\'';

ALIAS: [A-Z] [a-zA-Z_0-9]*;

VARIABLE: [\p{Ll}_] [\p{L}_0-9]* [!?]?;

DOT         : '.';
SUB         : '-';
DOT2        : '..';
EQ          : '=';
ARROW       : '=>';
PIPE        : '|';

OPAR   : '(';
CPAR   : ')';
OBRACK : '[';
CBRACK : ']';
OBRACE : '{';
CBRACE : '}';
OMAP   : '%' ( [a-zA-Z_.] [a-zA-Z_.0-9]*)? '{';
COMMA  : ',';
COL    : ':';
SCOL   : ';';

fragment OPERATOR:
    DOT
    | SUB
    | DOT2
    | EQ
    | PIPE
;

fragment D        : [0-9];
fragment HEX      : [0-9a-fA-F];
fragment EXPONENT : [eE] [+-]? D+;

fragment ESCAPE: '\\' ( [\\abdefnrstv0] | 'x' HEX HEX | 'u' HEX HEX HEX HEX | 'u{' HEX+ '}');