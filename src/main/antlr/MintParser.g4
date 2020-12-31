parser grammar MintParser;

options {
    tokenVocab=MintLexer;
}

topLevel: (component | module_definition | record_definition | enum_ | provider | routes | store | suite)* EOF;

component: Global? Component type_id component_block;

component_block: LBrace (property | connect | constant | function | style | state | use | get)+ RBrace;

property: Property Variable Colon type Equals expression;

connect: Connect type_id Exposing connect_block;

connect_block: LBrace connect_variable (Comma connect_variable)* RBrace;

connect_variable: Variable As Variable;

style: Style (Variable | VariableWithDashes) (LParen arguments? RParen)? style_block;

style_block: LBrace css_body* RBrace;

css_body: case_expression | if_expression /*| css_media*/ | css_definition_or_selector;

css_media: Media ~LBrace* style_block;

css_definition_or_selector: css_definition;// | css_selector;

css_definition: (Variable | VariableWithDashes) Colon css_value Semi;

css_value: (Variable | VariableWithDashes | Modulo | LParen | RParen | Minus | Dot
            | Colon | Comma | Hash | LBrace | RBrace | NumberLiteral)+;

state: State Variable (Colon type)? Equals expression;

use: Use type_id tuple_literal_or_record (When expression_block)?;

get: Get Variable Colon type expression_block;

module_definition: Module type_id block;

// TODO not the same name as spec
type_id : TypeId (Dot TypeId)* ;

block: LBrace (function | constant)* RBrace;

function: Function variable parameter_list? Colon type_or_type_variable expression_block where?;

constant: Const ConstId Equals expression;

parameter_list: LParen (parameter (Comma parameter)*)? RParen;

parameter: variable Colon type_or_type_variable;

type_or_type_variable: type | Variable;

expression_block: LBrace expression RBrace;

// TODO there should be no whitespace after basic_expression
expression: basic_expression array_access_or_call? (operator basic_expression array_access_or_call?)*;

array_access_or_call: access | call | array_access;

access: (SafeAccess | Dot) Variable array_access_or_call?;

call: (SafeCall | LParen) (expression (Comma expression)*)? RParen;

array_access: LBracket (Digit+ | expression) RBracket;

operator:
            Compose
          | NotEq
          | EqEq
          | LesserEq
          | Lesser
          | GreaterEq
          | Greater
          | Minus
          | Plus
          | Power
          | Times
          | Divided
          | Modulo
          | And
          | Or
          | Not;

basic_expression:
//          format_directive
//        | documentation_directive
//        | svg_directive
          Env
        | StringLiteral
//        | RegexpLiteral
        | BoolLiteral
        | NumberLiteral
//        | unary_minus
        | array
        | record_update
        | tuple_literal_or_record
        | html_element
        | html_expression
//        | html_component
//        | html_fragment
        | member_access
        | constant_access
        | module_access
        | decode
        | encode
        | if_expression
        | for_expression
        | with_expression
        | next_call
        | sequence
        | parallel
        | try_expression
        | case_expression
        | inline_function_or_parenthesized_expression
        | starts_with_uppercase
        | negated_expression
        | enum_id
        | js
        | Void
        | variable
;

starts_with_uppercase: enum_id /* | TODO record_constructor */ | constant_variable;

constant_variable: ConstId;

array: LBracket (expression (Comma expression)*)? RBracket;

where: Where where_block;

where_block: LBrace where_statement RBrace;

where_statement: (variable | tuple_destructuring) Equals expression;

tuple_destructuring: LBrace variable (Comma variable)* RBrace;

record_update: LBrace Variable Pipe record_field RBrace;

record_field: variable Equals expression;

tuple_literal_or_record: tuple_literal | record;

tuple_literal: LBrace (expression (Comma expression)*)? RBrace;

record: LBrace (record_field (Comma record_field)*)? RBrace;

html_element: self_closing_html_element | (opening_html_element html_body closing_html_element);

self_closing_html_element: Lesser tag_name html_style? html_attribute* Divided Greater;

opening_html_element: Lesser tag_name? html_style? html_attribute* Greater;

closing_html_element: Lesser Divided tag_name? Greater;

html_style: DoubleColon (Variable | VariableWithDashes) arguments? (As variable)?;

html_attribute: variable Equals (StringLiteral | array | expression_block);

html_body: (html_element | html_expression)*;

tag_name: Variable | VariableWithDashes | type_id;

// TODO only allowed inside non self-closing html_elements
html_expression: Lesser LBrace expression RBrace Greater;

//html_component: ;
//html_fragment: ;

// TODO is this right?
member_access: Dot variable;

module_access: type_id Dot variable;

decode: Decode expression As type_id;

encode: Encode expression;

// TODO for_css & for_html
if_expression: If LParen expression RParen expression_block
                Else (if_expression | expression_block);

for_expression: For LParen arguments Of expression RParen expression_block for_condition?;

for_condition: When expression_block;

arguments: Variable (Comma Variable)*;

with_expression: With type_id expression_block;

next_call: Next tuple_literal_or_record;

sequence: Sequence statements_block catch_* catch_all?;

statements_block: LBrace statement+ RBrace;

statement: ((Variable | tuple_destructuring) Equals)? expression;

catch_: Catch type_id Arrow Variable expression_block;

parallel: Parallel statements_block then_block catch_*;

then_block: Then expression_block;

try_expression: Try statements_block catch_* catch_all?;

catch_all: Catch expression_block;

case_expression: Case LParen expression RParen branches_block;

branches_block: LBrace case_branch+ RBrace;

// TODO css_definition

case_branch:
  (
      constant_access
    | enum_destructuring
    | bool_tuple_literal
    | tuple_destructuring
    | array_destructuring
    | expression
  )? Arrow expression;

array_destructuring: LBracket (variable | spread) (Comma (variable | spread))* RBracket;

spread: TripleDot variable;

bool_tuple_literal: LBrace BoolLiteral (Comma BoolLiteral)* RBrace;

constant_access: type_id Colon constant_variable;

inline_function_or_parenthesized_expression: parenthesized_expression | inline_function;

parenthesized_expression: LParen expression RParen;

inline_function: parameter_list (Colon type_or_type_variable)? expression_block;

negated_expression: Not expression;

enum_destructuring: TypeId DoubleColon TypeId Variable*;

enum_id: type_id DoubleColon TypeId expression_list?;

expression_list: LParen expression (Comma expression)* RParen;

js: JsStart (JsContent | interpolation)* JsEnd;

interpolation: JsInterpStart (Stuff | interpolation)+ JsInterpEnd;

record_definition: Record type_id record_definitions_block;

record_definitions_block: LBrace record_definition_field (Comma record_definition_field)* RBrace;

record_definition_field: variable Colon type (Using StringLiteral)?;

type: type_id (LParen type_or_variable_list? RParen)?;

type_or_variable_list : (type | Variable) (Comma (type | Variable))* ;

enum_: Enum type_id (LParen type_or_variable_list? RParen)? enum_block;

enum_block: LBrace enum_option* RBrace;

enum_option: type_id (LParen type_or_variable_list? RParen)?;

provider: Provider type_id Colon type_id provider_block;

provider_block: LBrace (function | state | constant)+ RBrace;

routes: Routes routes_block;

routes_block: LBrace route+ RBrace;

route: (Times | (Divided ~(LBrace | LParen)*)) parameter_list? expression_block;

store: Store TypeId store_block;

store_block: LBrace (state | function | get | constant)+ RBrace;

suite: Suite StringLiteral suite_block;

suite_block: LBrace test+ RBrace;

test: Test StringLiteral expression_block;

variable: As
          | Case
          | Catch
          | Component
          | Connect
          | Decode
          | Else
          | Encode
          | Enum
          | Exposing
          | For
          | Function
          | Get
          | Global
          //| If
          | Module
          | Next
          | Of
          | Parallel
          | Property
          | Provider
          | Record
          | Routes
          | Sequence
          | State
          | Store
          | Style
          | Suite
          | Test
          | Then
          | Try
          | Use
          | Void
          | When
          | Where
          | With
          | Variable
          ;