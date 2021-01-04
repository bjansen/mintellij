package com.github.bjansen.mintellij.lang

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.psi.MintParserDefinition.Companion.getTokenType
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.XmlHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor

class MintSyntaxHighlighter : SyntaxHighlighterBase() {

	override fun getHighlightingLexer() = ANTLRLexerAdaptor(MintLanguage, MintLexer(null))

	override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> =
			when {
				isKeyword(tokenType) ->
					pack(KEYWORD)
				OPERATORS.contains(tokenType) ->
					pack(OPERATOR)
				isBrace(tokenType) ->
					pack(BRACE)
				isParen(tokenType) ->
					pack(PAREN)
				isBracket(tokenType) ->
					pack(BRACKET)
				isDotLike(tokenType) ->
					pack(DOT)
				tokenType == getTokenType(MintLexer.Semi) ->
					pack(SEMICOLON)
				tokenType == getTokenType(MintLexer.Comment) ->
					pack(COMMENT)
				tokenType == getTokenType(MintLexer.TypeId) ->
					pack(TYPE)
				tokenType == getTokenType(MintLexer.StringLiteral) ->
					pack(STRING)
				isNumber(tokenType) ->
					pack(NUMBER)
				else ->
					emptyArray()
			}

	private fun isNumber(tokenType: IElementType) =
			tokenType == getTokenType(MintLexer.NumberLiteral)
					|| tokenType == getTokenType(MintLexer.Digit)

	private fun isKeyword(tokenType: IElementType) =
			KEYWORDS.contains(tokenType)
					|| tokenType == getTokenType(MintLexer.BoolLiteral)

	private fun isDotLike(tokenType: IElementType) =
			tokenType == getTokenType(MintLexer.SafeAccess)
					|| tokenType == getTokenType(MintLexer.TripleDot)
					|| tokenType == getTokenType(MintLexer.Dot)

	private fun isBracket(tokenType: IElementType) =
			tokenType == getTokenType(MintLexer.LBracket)
					|| tokenType == getTokenType(MintLexer.RBracket)

	private fun isBrace(tokenType: IElementType) =
			tokenType == getTokenType(MintLexer.LBrace)
					|| tokenType == getTokenType(MintLexer.RBrace)

	private fun isParen(tokenType: IElementType) =
			tokenType == getTokenType(MintLexer.LParen)
					|| tokenType == getTokenType(MintLexer.SafeCall)
					|| tokenType == getTokenType(MintLexer.RParen)

	companion object {
		private val KEYWORDS = listOf(
				getTokenType(MintLexer.As),
				getTokenType(MintLexer.Case),
				getTokenType(MintLexer.Catch),
				getTokenType(MintLexer.Component),
				getTokenType(MintLexer.Connect),
				getTokenType(MintLexer.Const),
				getTokenType(MintLexer.Decode),
				getTokenType(MintLexer.Else),
				getTokenType(MintLexer.Encode),
				getTokenType(MintLexer.Enum),
				getTokenType(MintLexer.Exposing),
				getTokenType(MintLexer.For),
				getTokenType(MintLexer.Function),
				getTokenType(MintLexer.Get),
				getTokenType(MintLexer.Global),
				getTokenType(MintLexer.If),
				getTokenType(MintLexer.Module),
				getTokenType(MintLexer.Next),
				getTokenType(MintLexer.Of),
				getTokenType(MintLexer.Parallel),
				getTokenType(MintLexer.Property),
				getTokenType(MintLexer.Provider),
				getTokenType(MintLexer.Record),
				getTokenType(MintLexer.Routes),
				getTokenType(MintLexer.Sequence),
				getTokenType(MintLexer.State),
				getTokenType(MintLexer.Store),
				getTokenType(MintLexer.Style),
				getTokenType(MintLexer.Suite),
				getTokenType(MintLexer.Test),
				getTokenType(MintLexer.Then),
				getTokenType(MintLexer.Try),
				getTokenType(MintLexer.Use),
				getTokenType(MintLexer.Using),
				getTokenType(MintLexer.Void),
				getTokenType(MintLexer.When),
				getTokenType(MintLexer.Where),
				getTokenType(MintLexer.With)
		)

		private val OPERATORS = listOf(
				getTokenType(MintLexer.Compose),
				getTokenType(MintLexer.EqEq),
				getTokenType(MintLexer.LesserEq),
				getTokenType(MintLexer.Lesser),
				getTokenType(MintLexer.GreaterEq),
				getTokenType(MintLexer.Greater),
				getTokenType(MintLexer.Minus),
				getTokenType(MintLexer.Plus),
				getTokenType(MintLexer.Power),
				getTokenType(MintLexer.Times),
				getTokenType(MintLexer.Divided),
				getTokenType(MintLexer.Modulo),
				getTokenType(MintLexer.And),
				getTokenType(MintLexer.Or),
				getTokenType(MintLexer.NotEq),
				getTokenType(MintLexer.DoubleColon),
				getTokenType(MintLexer.Colon),
				getTokenType(MintLexer.Pipe),
				getTokenType(MintLexer.Arrow),
				getTokenType(MintLexer.Equals),
				getTokenType(MintLexer.Hash)
		)

		val KEYWORD = TextAttributesKey.createTextAttributesKey(
				"MintKeyword",
				DefaultLanguageHighlighterColors.KEYWORD
		)

		val COMMENT = TextAttributesKey.createTextAttributesKey(
				"MintComment",
				DefaultLanguageHighlighterColors.DOC_COMMENT
		)

		val TYPE = TextAttributesKey.createTextAttributesKey(
				"MintType",
				DefaultLanguageHighlighterColors.INSTANCE_FIELD
		)

		val STRING = TextAttributesKey.createTextAttributesKey(
				"MintString",
				DefaultLanguageHighlighterColors.STRING
		)

		val OPERATOR = TextAttributesKey.createTextAttributesKey(
				"MintOperator",
				DefaultLanguageHighlighterColors.OPERATION_SIGN
		)

		val BRACE = TextAttributesKey.createTextAttributesKey(
				"MintBrace",
				DefaultLanguageHighlighterColors.BRACES
		)

		val PAREN = TextAttributesKey.createTextAttributesKey(
				"MintParen",
				DefaultLanguageHighlighterColors.PARENTHESES
		)

		val BRACKET = TextAttributesKey.createTextAttributesKey(
				"MintBracket",
				DefaultLanguageHighlighterColors.BRACKETS
		)

		val DOT = TextAttributesKey.createTextAttributesKey(
				"MintDot",
				DefaultLanguageHighlighterColors.DOT
		)

		val SEMICOLON = TextAttributesKey.createTextAttributesKey(
				"MintSemi",
				DefaultLanguageHighlighterColors.SEMICOLON
		)

		val NUMBER = TextAttributesKey.createTextAttributesKey(
				"MintNumber",
				DefaultLanguageHighlighterColors.NUMBER
		)

		val HTML_TAG = TextAttributesKey.createTextAttributesKey(
				"MintHtmlTag",
				XmlHighlighterColors.HTML_TAG_NAME
		)

		val HTML_ATTR = TextAttributesKey.createTextAttributesKey(
				"MintHtmlAttribute",
				XmlHighlighterColors.HTML_ATTRIBUTE_NAME
		)

		val HTML_STYLE = TextAttributesKey.createTextAttributesKey(
				"MintHtmlStyle"
		)

		val STYLE_PROPERTY = TextAttributesKey.createTextAttributesKey(
				"MintStyleProperty"
		)
	}
}
