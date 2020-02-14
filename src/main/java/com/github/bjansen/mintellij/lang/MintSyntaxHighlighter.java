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
			getTokenType(MintLexer.Record),
			getTokenType(MintLexer.Sequence),
			getTokenType(MintLexer.State),
			getTokenType(MintLexer.Then),
			getTokenType(MintLexer.Try),
			getTokenType(MintLexer.Use),
			getTokenType(MintLexer.Void),
			getTokenType(MintLexer.When),
			getTokenType(MintLexer.With)
	);

	private static final TextAttributesKey KEYWORD =
			createTextAttributesKey("MintKeyword", DefaultLanguageHighlighterColors.KEYWORD);

	private static final TextAttributesKey COMMENT =
			createTextAttributesKey("MintComment", DefaultLanguageHighlighterColors.DOC_COMMENT);

	private static final TextAttributesKey TYPE =
			createTextAttributesKey("MintType", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
		return new ANTLRLexerAdaptor(MintLanguage.INSTANCE, new MintLexer(null));
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		if (KEYWORDS.contains(tokenType)) {
			return pack(KEYWORD);
		} else if (tokenType.equals(getTokenType(MintLexer.Comment))) {
			return pack(COMMENT);
		} else if (tokenType.equals(getTokenType(MintLexer.TypeId))) {
			return pack(TYPE);
		}

		return new TextAttributesKey[0];
	}
}
