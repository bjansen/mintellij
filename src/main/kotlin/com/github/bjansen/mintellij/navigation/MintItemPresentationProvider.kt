package com.github.bjansen.mintellij.navigation

import com.github.bjansen.mintellij.psi.MintFunction
import com.github.bjansen.mintellij.psi.MintPsiElement
import com.intellij.ide.projectView.PresentationData
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.ItemPresentationProvider

class MintItemPresentationProvider : ItemPresentationProvider<MintPsiElement> {

    override fun getPresentation(item: MintPsiElement): ItemPresentation {
        val name = if (item is MintFunction) item.getSignature() else item.name

        return PresentationData(
            name,
            item.containingFile.name,
            item.getIcon(0),
            null
        )
    }
}
