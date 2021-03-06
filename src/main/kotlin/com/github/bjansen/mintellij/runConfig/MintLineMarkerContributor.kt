package com.github.bjansen.mintellij.runConfig

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.psi.matchesAntlrRule
import com.github.bjansen.mintellij.psi.matchesAntlrToken
import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement

class MintLineMarkerContributor : RunLineMarkerContributor() {

    override fun getInfo(element: PsiElement): Info? {
        if (isMainComponent(element) || isSuite(element)) {

            return Info(
                AllIcons.RunConfigurations.TestState.Run,
                null,
                ExecutorAction.getActions()
            )
        }

        return null
    }

    private fun isSuite(element: PsiElement) =
        element.matchesAntlrToken(MintLexer.StringLiteral) &&
            element.parent?.matchesAntlrRule(MintParser.RULE_suite) == true

    private fun isMainComponent(element: PsiElement) =
        element.matchesAntlrToken(MintLexer.TypeId) &&
            element.text == "Main" &&
            element.parent?.matchesAntlrRule(MintParser.RULE_type_id) == true &&
            element.parent?.parent?.matchesAntlrRule(MintParser.RULE_component) == true
}
