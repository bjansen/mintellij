package com.github.bjansen.mintellij.psi

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.lang.MintLanguage
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.progress.ProgressIndicatorProvider
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.IStubFileElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.util.containers.map2Array
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.parser.ANTLRParseTreeToPSIConverter
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.ParserRuleContext
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

            override fun createListener(parser: Parser?, root: IElementType?, builder: PsiBuilder?): ANTLRParseTreeToPSIConverter {
                return object : ANTLRParseTreeToPSIConverter(language, parser, builder) {
                    override fun exitEveryRule(ctx: ParserRuleContext?) {
                        ProgressIndicatorProvider.checkCanceled()

                        when (ctx?.ruleIndex) {
                            MintParser.RULE_module_definition -> markers.pop().done(MintModuleStubElementType)
                            MintParser.RULE_component -> markers.pop().done(MintComponentStubElementType)
                            MintParser.RULE_record_definition -> markers.pop().done(MintRecordStubElementType)
                            MintParser.RULE_store -> markers.pop().done(MintStoreStubElementType)
                            MintParser.RULE_enum_ -> markers.pop().done(MintEnumStubElementType)
                            else -> super.exitEveryRule(ctx)
                        }
                    }
                }
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
		return when (node.elementType) {
            MintModuleStubElementType -> MintModule(node)
            MintComponentStubElementType -> MintComponent(node)
            MintRecordStubElementType -> MintRecord(node)
            MintStoreStubElementType -> MintStore(node)
            MintEnumStubElementType -> MintEnum(node)
            getRuleType(MintParser.RULE_function) -> MintFunction(node)
            else -> MintElement(node)
        }
	}

	override fun createFile(viewProvider: FileViewProvider): PsiFile {
		return MintFile(viewProvider)
	}

	companion object {
		private val FILE = IStubFileElementType<PsiFileStub<MintFile>>(MintLanguage)

		fun getTokenType(@MagicConstant(valuesFromClass = MintLexer::class) token: Int): IElementType {
			return PSIElementTypeFactory.getTokenIElementTypes(MintLanguage)[token]
		}

		fun getRuleType(@MagicConstant(valuesFromClass = MintParser::class) rule: Int): IElementType {
			return PSIElementTypeFactory.getRuleIElementTypes(MintLanguage)[rule]
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

fun PsiElement.matchesAntlrRule(@MagicConstant(valuesFromClass = MintParser::class) rule: Int): Boolean {
	return node?.elementType == MintParserDefinition.getRuleType(rule)
}

fun PsiElement.firstChildMatchingAntlrRule(@MagicConstant(valuesFromClass = MintParser::class) rule: Int): PsiElement? {
	return node?.findChildByType(MintParserDefinition.getRuleType(rule))?.psi
}

fun PsiElement.findChildrenMatchingAntlrRule(@MagicConstant(valuesFromClass = MintParser::class) rule: Int): Array<PsiElement>? {
    return node
        ?.getChildren(TokenSet.create(MintParserDefinition.getRuleType(rule)))
        ?.map2Array { it.psi }
}

fun PsiElement.matchesAntlrToken(@MagicConstant(valuesFromClass = MintLexer::class) token: Int): Boolean {
	return node?.elementType == MintParserDefinition.getTokenType(token)
}
