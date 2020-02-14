package com.github.bjansen.mintellij.psi;

import com.github.bjansen.mintellij.lang.MintFileType;
import com.github.bjansen.mintellij.lang.MintLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class MintFile extends PsiFileBase {

	protected MintFile(@NotNull FileViewProvider viewProvider) {
		super(viewProvider, MintLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public FileType getFileType() {
		return MintFileType.INSTANCE;
	}
}
