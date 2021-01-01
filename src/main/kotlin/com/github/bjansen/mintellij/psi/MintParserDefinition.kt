package com.github.bjansen.mintellij.psi

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.lang.MintLanguage
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree
import org.intellij.lang.annotations.MagicConstant

class MintParserDefinition : ParserDefinition {

	override fun createLexer(project: Project): Lexer {
		val delegate = MintLexer(null)

		return ANTLRLexerAdaptor(MintLanguage, delegate)
	}

	override fun createParser(project: Project): PsiParser {
		val delegate = MintParser(null)

		return object : ANTLRParserAdaptor(MintLanguage, delegate) {
			override fun parse(parser: Parser, root: IElementType): ParseTree {
				return (parser as MintParser).topLevel()
			}
		}
	}

	override fun getFileNodeType(): IFileElementType {
		return FILE
	}

	override fun getCommentTokens(): TokenSet {
		return PSIElementTypeFactory.createTokenSet(MintLanguage, MintLexer.Comment)
	}

	override fun getStringLiteralElements(): TokenSet {
		return PSIElementTypeFactory.createTokenSet(MintLanguage, MintLexer.StringLiteral)
	}

	override fun getWhitespaceTokens(): TokenSet {
		return PSIElementTypeFactory.createTokenSet(MintLanguage, MintLexer.WS)
	}

	override fun createElement(node: ASTNode): PsiElement {
		return MintElement(node)
	}

	override fun createFile(viewProvider: FileViewProvider): PsiFile {
		return MintFile(viewProvider)
	}

	companion object {
		private val FILE = IFileElementType(MintLanguage)

		fun getTokenType(@MagicConstant(valuesFromClass = MintLexer::class) rule: Int): IElementType {
			return PSIElementTypeFactory.getTokenIElementTypes(MintLanguage)[rule]
		}

		init {
			PSIElementTypeFactory.defineLanguageIElementTypes(
					MintLanguage,
					MintLexer.tokenNames,
					MintParser.ruleNames
			)
		}
	}
}
