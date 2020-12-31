lexer grammar MintLexer;

Comment: '/*' .*? '*/' -> channel(HIDDEN);

// TODO parse the contents
JsStart: '`' -> pushMode(IN_JS);

// TODO interpolation,
StringLiteral: StringPart (WS? '\\' WS? StringPart)*;
BoolLiteral: 'true' | 'false';
NumberLiteral: '-'? Digit+ ('.' Digit+)?;

fragment StringPart: '"' (EscSequence | ~["])* '"';
fragment EscSequence: '\\' [btnfr"'\\];
Digit: [0-9];

Compose: '|>';
NotEq: '!=';
EqEq: '==';

LesserEq: '<=';
Lesser: '<';
GreaterEq: '>=';
Greater: '>';

Minus: '-';
Plus: '+';

Power: '**';

Times: '*';
Divided: '/';
Modulo: '%';

And: '&&';
Or: '||';
Not: '!';

SafeAccess: '&.';
SafeCall: '&(';
TripleDot: '...';
Dot: '.';
LBrace: '{';
RBrace: '}';
LParen: '(';
RParen: ')';
LBracket: '[';
RBracket: ']';
Comma: ',';
DoubleColon: '::';
Colon: ':';
Semi: ';';
Pipe: '|';
Arrow: '=>';
Equals: '=';
Hash: '#';

As: 'as';
Case: 'case';
Catch: 'catch';
Component: 'component';
Connect: 'connect';
Const: 'const';
Decode: 'decode';
Else: 'else';
Encode: 'encode';
Enum: 'enum';
Exposing: 'exposing';
For: 'for';
Function: 'fun';
Get: 'get';
Global: 'global';
If: 'if';
Module: 'module';
Next: 'next';
Of: 'of';
Parallel: 'parallel';
Property: 'property';
Provider: 'provider';
Record: 'record';
Routes: 'routes';
Sequence: 'sequence';
State: 'state';
Store: 'store';
Style: 'style';// -> mode(IN_STYLE);
Suite: 'suite';
Test: 'test';
Then: 'then';
Try: 'try';
Use: 'use';
Using: 'using';
Void: 'void';
When: 'when';
Where: 'where';
With: 'with';

Env: '@' [A-Z]+;
ConstId: [A-Z][A-Z0-9_]*;
TypeId: [A-Z][a-zA-Z0-9]*;
Variable: [a-z][a-zA-Z0-9]*;
VariableWithDashes: [a-z-][a-zA-Z0-9-]*;

WS: [ \t\r\n]+ -> channel(HIDDEN);
ERRCHAR: . -> channel(HIDDEN);

mode IN_JS;

JsInterpStart: '#{' -> pushMode(IN_INTERPOLATION);

EscapedBacktick: '\\`' -> type(JsContent);

JsEnd: '`' -> popMode;

JsContent: (~[\\`#] | '\\' ~'`')+;

mode IN_INTERPOLATION;

InnerInterpStart: '#{' -> pushMode(IN_INTERPOLATION), type(JsInterpStart);

JsInterpEnd: '}' -> popMode;

Stuff: (~('}'|'#') | ('#' ~'{'))+;

//mode IN_STYLE;
//
//Media: '@media';
//MediaType: ~[{]+;