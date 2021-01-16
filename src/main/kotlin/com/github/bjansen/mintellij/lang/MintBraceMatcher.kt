package com.github.bjansen.mintellij.lang

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.psi.MintParserDefinition
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class MintBraceMatcher : PairedBraceMatcher {

    override fun getPairs() = PAIRS

    override fun isPairedBracesAllowedBeforeType(
        lbraceType: IElementType,
        contextType: IElementType?
    ) = true

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int) =
        openingBraceOffset

    companion object {
        private val PAIRS = arrayOf(
            newPair(MintLexer.LBrace, MintLexer.RBrace),
            newPair(MintLexer.LBracket, MintLexer.RBracket),
            newPair(MintLexer.LParen, MintLexer.RParen)
        )

        private fun newPair(left: Int, right: Int): BracePair {
            return BracePair(
                MintParserDefinition.getTokenType(left),
                MintParserDefinition.getTokenType(right),
                false
            )
        }
    }
}
