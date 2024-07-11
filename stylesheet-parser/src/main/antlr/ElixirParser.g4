// $antlr-format alignTrailingComments true, columnLimit 150, minEmptyLines 1, maxEmptyLinesToKeep 1, reflowComments false, useTab false
// $antlr-format allowShortRulesOnASingleLine false, allowShortBlocksOnASingleLine true, alignSemicolons hanging, alignColons hanging

parser grammar ElixirParser;

options {
    tokenVocab = ElixirLexer;
}

parse
    : block EOF
    ;

block
    : eoe? expression (eoe expression)* eoe?
    ;

eoe
    : (';' | NL)+
    ;

expression
    : expression expression_tail                                   # dotExpr
    | '(' expression ')'                                           # nestedExpr
    | unaryOp expression                                           # unaryExpr
    | expression '=' expression                                    # patternExpr
    | list                                                         # listExpr
    | tuple                                                        # tupleExpr
    | map                                                          # mapExpr
    | bool_                                                        # boolExpr
    | ATOM                                                         # atomExpr
    | INTEGER                                                      # integerExpr
    | HEXADECIMAL                                                  # hexadecimalExpr
    | OCTAL                                                        # octalExpr
    | BINARY                                                       # binaryExpr
    | FLOAT                                                        # floatExpr
    | SIGIL                                                        # sigilExpr
    | SINGLE_LINE_STRING                                           # singleLineStringExpr
    | MULTI_LINE_STRING                                            # multiLineStringExpr
    | SINGLE_LINE_CHARLIST                                         # singleLineCharlistExpr
    | MULTI_LINE_CHARLIST                                          # multiLineCharlistExpr
    | ALIAS                                                        # aliasExpr
    | CODEPOINT                                                    # codepointExpr
    | NIL                                                          # nilExpr
    ;

unaryOp
    : '+'
    | '-'
    ;

expression_tail
    : '[' expression ']'
    | '.' expression
    ;

bool_
    : TRUE
    | FALSE
    ;

list
    : '[' NL* expressions_ ','? NL* ']'
    | '[' NL* ( expressions_ ','? NL*)? ']'
    | '[' NL* tuples ( ',' short_map_entries)? NL* ']'
    | '[' NL* short_map_entries NL* ']'
    ;

tuples
    : tuple (',' tuple)*
    ;

tuple
    : '{' (expressions_ ','?)? '}'
    ;

map
    : OMAP NL* '}'
    | OMAP NL* ( expression '|')? map_entries ','? NL* '}'
    | OMAP NL* ( expression '|')? short_map_entries ','? NL* '}'
    | OMAP NL* (expression '|')? map_entries (',' short_map_entries)? ','? NL* '}'
    ;

map_entries
    : map_entry (',' map_entry)*
    ;

map_entry
    : expression '=>' expression
    ;

short_map_entries
    : short_map_entry (',' NL* short_map_entry)*
    ;

short_map_entry
    : (variable | END | AFTER) ':' expression  // Change to allow named params uses "end" and "after"
    // variable ':' expression
    ;

options_
    : option (',' NL* option)*
    ;

option
    : variable ':' expression
    ;

expressions_
    : expression (',' NL* expression)*
    ;

variable
    : VARIABLE
    ;