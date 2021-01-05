package com.github.bjansen.mintellij.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

open class MintElement(node: ASTNode) : ASTWrapperPsiElement(node)
