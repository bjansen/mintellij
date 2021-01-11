package com.github.bjansen.mintellij.completion

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.psi.MintParserDefinition.Companion.getRuleType
import com.github.bjansen.mintellij.psi.MintParserDefinition.Companion.getTokenType
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns.psiElement

class MintCompletionContributor : CompletionContributor() {

    init {
        // Return types, parameter types
        extend(
            CompletionType.BASIC,
            psiElement(getTokenType(MintLexer.TypeId)).withSuperParent(2, psiElement(getRuleType(MintParser.RULE_type))),
            MintTypeCompletionProvider
        )

        // Type access (no dot, parent is a PsiErrorElement)
        extend(
            CompletionType.BASIC,
            psiElement(getTokenType(MintLexer.TypeId)).withSuperParent(2, psiElement(getRuleType(MintParser.RULE_basic_expression))),
            MintTypeCompletionProvider
        )

        // Type access (with a dot)
        extend(
            CompletionType.BASIC,
            psiElement(getTokenType(MintLexer.TypeId)).withSuperParent(2, psiElement(getRuleType(MintParser.RULE_module_access))),
            MintTypeCompletionProvider
        )
    }
}
