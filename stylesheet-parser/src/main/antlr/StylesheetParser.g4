parser grammar StylesheetParser;

options { tokenVocab=StylesheetLexer; }

file        : style;

// %{"my-class" => [<list of modifiers>] }
style       : PERCENT CURLY_BRACE_OPEN STRING RIGHT_ARROW BRACKET_OPEN  modifier (COMMA modifier)* BRACKET_CLOSE CURLY_BRACE_CLOSE;

// {:modifierName, [<list of meta data>], [<list of arguments>]}
modifier    : OPEN_MODIFIER (ID | DOT) (COMMA BRACKET_OPEN (meta (COMMA meta)*)? BRACKET_CLOSE)? (COMMA BRACKET_OPEN (arg (COMMA arg)*)? BRACKET_CLOSE)? CURLY_BRACE_CLOSE;

// each argument can be: a string, a number, another modifier, or a member
arg         : (STRING | NUMBER | modifier | member);

// :MemberName
member      : COLON ID;

// metaData: <value>: <meta value>
meta        : ID COLON metaValue;

metaValue   : (STRING | NUMBER | ID);
