package com.github.bjansen.mintellij.navigation

import com.github.bjansen.mintellij.icons.MintIcons
import com.github.bjansen.mintellij.psi.*
import com.intellij.icons.AllIcons
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.ItemPresentationProvider
import javax.swing.Icon

class MintItemPresentationProvider : ItemPresentationProvider<MintPsiElement> {

    override fun getPresentation(item: MintPsiElement): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText(): String? {
                return item.name
            }

            override fun getLocationString(): String {
                return item.containingFile.name
            }

            override fun getIcon(unused: Boolean): Icon? {
                return when (item) {
                    is MintComponent -> MintIcons.component
                    is MintEnum -> MintIcons.enum
                    is MintModule -> MintIcons.module
                    is MintRecord -> MintIcons.record
                    is MintStore -> MintIcons.store
                    else -> null
                }
            }

        }
    }
}
