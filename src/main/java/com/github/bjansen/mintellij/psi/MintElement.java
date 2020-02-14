package com.github.bjansen.mintellij.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class MintElement extends ASTWrapperPsiElement {

	public MintElement(@NotNull ASTNode node) {
		super(node);
	}
}
