package com.github.bjansen.mintellij.lang;

import com.github.bjansen.mintellij.MintLexer;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static com.github.bjansen.mintellij.psi.MintParserDefinition.getTokenType;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class MintSyntaxHighlighter extends SyntaxHighlighterBase {

	private static final List<IElementType> KEYWORDS = Arrays.asList(
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
	);

	private static final List<IElementType> OPERATORS = Arrays.asList(
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
	);

	static final TextAttributesKey KEYWORD =
			createTextAttributesKey("MintKeyword", DefaultLanguageHighlighterColors.KEYWORD);

	static final TextAttributesKey COMMENT =
			createTextAttributesKey("MintComment", DefaultLanguageHighlighterColors.DOC_COMMENT);

	static final TextAttributesKey TYPE =
			createTextAttributesKey("MintType", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

	static final TextAttributesKey STRING =
			createTextAttributesKey("MintString", DefaultLanguageHighlighterColors.STRING);

	static final TextAttributesKey OPERATOR =
			createTextAttributesKey("MintOperator", DefaultLanguageHighlighterColors.OPERATION_SIGN);

	static final TextAttributesKey BRACE =
			createTextAttributesKey("MintBrace", DefaultLanguageHighlighterColors.BRACES);

	static final TextAttributesKey PAREN =
			createTextAttributesKey("MintParen", DefaultLanguageHighlighterColors.PARENTHESES);

	static final TextAttributesKey BRACKET =
			createTextAttributesKey("MintBracket", DefaultLanguageHighlighterColors.BRACKETS);

	static final TextAttributesKey DOT =
			createTextAttributesKey("MintDot", DefaultLanguageHighlighterColors.DOT);

	static final TextAttributesKey SEMICOLON =
			createTextAttributesKey("MintSemi", DefaultLanguageHighlighterColors.SEMICOLON);

	static final TextAttributesKey NUMBER =
			createTextAttributesKey("MintNumber", DefaultLanguageHighlighterColors.NUMBER);

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		return new ANTLRLexerAdaptor(MintLanguage.INSTANCE, new MintLexer(null));
	}

	@NotNull
	@Override
	public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
		if (KEYWORDS.contains(tokenType)
			|| tokenType.equals(getTokenType(MintLexer.BoolLiteral))) {
			return pack(KEYWORD);
		} else if (OPERATORS.contains(tokenType)) {
			return pack(OPERATOR);
		} else if (tokenType.equals(getTokenType(MintLexer.LBrace))
			|| tokenType.equals(getTokenType(MintLexer.RBrace))) {
			return pack(BRACE);
		} else if (tokenType.equals(getTokenType(MintLexer.LParen))
			|| tokenType.equals(getTokenType(MintLexer.SafeCall))
			|| tokenType.equals(getTokenType(MintLexer.RParen))) {
			return pack(PAREN);
		} else if (tokenType.equals(getTokenType(MintLexer.LBracket))
			|| tokenType.equals(getTokenType(MintLexer.RBracket))) {
			return pack(BRACKET);
		} else if (tokenType.equals(getTokenType(MintLexer.SafeAccess))
			|| tokenType.equals(getTokenType(MintLexer.TripleDot))
			|| tokenType.equals(getTokenType(MintLexer.Dot))) {
			return pack(DOT);
		} else if (tokenType.equals(getTokenType(MintLexer.Semi))) {
			return pack(SEMICOLON);
		} else if (tokenType.equals(getTokenType(MintLexer.Comment))) {
			return pack(COMMENT);
		} else if (tokenType.equals(getTokenType(MintLexer.TypeId))) {
			return pack(TYPE);
		} else if (tokenType.equals(getTokenType(MintLexer.StringLiteral))) {
			return pack(STRING);
		} else if (tokenType.equals(getTokenType(MintLexer.NumberLiteral))
			|| tokenType.equals(getTokenType(MintLexer.Digit))) {
			return pack(NUMBER);
		}

		return new TextAttributesKey[0];
	}
}
