package com.github.bjansen.mintellij.completion

import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.psi.MintFunction
import com.github.bjansen.mintellij.psi.firstChildMatchingAntlrRule
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

object MintFunctionParameterCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        PsiTreeUtil.getParentOfType(parameters.position, MintFunction::class.java)
            ?.getParameters()
            ?.forEach {
                result.addElement(
                    LookupElementBuilder.create(it.firstChildMatchingAntlrRule(MintParser.RULE_variable)?.text ?: "??")
                        .withTypeText(it.firstChildMatchingAntlrRule(MintParser.RULE_type_or_type_variable)?.text)
                )
            }
    }
}
