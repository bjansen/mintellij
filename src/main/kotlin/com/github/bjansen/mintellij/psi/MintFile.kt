package com.github.bjansen.mintellij.psi

import com.github.bjansen.mintellij.lang.MintFileType
import com.github.bjansen.mintellij.lang.MintLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class MintFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, MintLanguage), MintPsiElement {

    override fun getFileType() = MintFileType
}
