package com.github.bjansen.mintellij.psi;

import com.github.bjansen.mintellij.MintLexer;
import com.github.bjansen.mintellij.MintParser;
import com.github.bjansen.mintellij.lang.MintLanguage;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

public class MintParserDefinition implements ParserDefinition {

	private static final IFileElementType FILE = new IFileElementType(MintLanguage.INSTANCE);

	static {
		PSIElementTypeFactory.defineLanguageIElementTypes(MintLanguage.INSTANCE,
				MintLexer.tokenNames, MintParser.ruleNames);
	}

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		final MintLexer delegate = new MintLexer(null);
		return new ANTLRLexerAdaptor(MintLanguage.INSTANCE, delegate);
	}

	@Override
	public PsiParser createParser(Project project) {
		final MintParser delegate = new MintParser(null);
		return new ANTLRParserAdaptor(MintLanguage.INSTANCE, delegate) {
			@Override
			protected ParseTree parse(Parser parser, IElementType root) {
				return ((MintParser) parser).topLevel();
			}
		};
	}

	@Override
	public IFileElementType getFileNodeType() {
		return FILE;
	}

	@NotNull
	@Override
	public TokenSet getCommentTokens() {
		return PSIElementTypeFactory.createTokenSet(MintLanguage.INSTANCE, MintLexer.Comment);
	}

	@NotNull
	@Override
	public TokenSet getStringLiteralElements() {
		return PSIElementTypeFactory.createTokenSet(MintLanguage.INSTANCE, MintLexer.StringLiteral);
	}

	@NotNull
	@Override
	public TokenSet getWhitespaceTokens() {
		return PSIElementTypeFactory.createTokenSet(MintLanguage.INSTANCE, MintLexer.WS);
	}

	@NotNull
	@Override
	public PsiElement createElement(ASTNode node) {
		return new MintElement(node);
	}

	@Override
	public PsiFile createFile(FileViewProvider viewProvider) {
		return new MintFile(viewProvider);
	}

	public static IElementType getTokenType(@MagicConstant(valuesFromClass = MintLexer.class) int rule) {
		return PSIElementTypeFactory.getTokenIElementTypes(MintLanguage.INSTANCE)
				.get(rule);
	}
}
