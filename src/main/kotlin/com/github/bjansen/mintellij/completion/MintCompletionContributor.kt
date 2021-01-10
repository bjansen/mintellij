package com.github.bjansen.mintellij.completion

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.psi.MintParserDefinition.Companion.getTokenType
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns.psiElement

class MintCompletionContributor : CompletionContributor() {

    init {
        extend(
            CompletionType.BASIC,
            psiElement(getTokenType(MintLexer.TypeId)),
            MintTypeCompletionProvider
        )
    }
}
