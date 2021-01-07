package com.github.bjansen.mintellij.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.NavigatablePsiElement

open class MintElement(node: ASTNode) : ASTWrapperPsiElement(node), MintPsiElement

interface MintPsiElement : NavigatablePsiElement
