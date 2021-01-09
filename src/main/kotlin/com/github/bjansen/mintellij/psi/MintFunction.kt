package com.github.bjansen.mintellij.psi

import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.icons.MintIcons
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentationProviders
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

class MintFunction(node: ASTNode) : MintElement(node), MintPsiElement, PsiNameIdentifierOwner {

    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier() = firstChildMatchingAntlrRule(MintParser.RULE_variable)

    override fun getName() = nameIdentifier?.text

    override fun getPresentation() = ItemPresentationProviders.getItemPresentation(this)

    override fun getElementIcon(flags: Int) = MintIcons.function

    fun getSignature(): String {
        val parameterTypes = firstChildMatchingAntlrRule(MintParser.RULE_parameter_list)
            ?.findChildrenMatchingAntlrRule(MintParser.RULE_parameter)
            ?.mapNotNull { it.firstChildMatchingAntlrRule(MintParser.RULE_type_or_type_variable)?.text }
            ?.joinToString(", ")
            ?: ""

        return "$name($parameterTypes)"
    }
}
