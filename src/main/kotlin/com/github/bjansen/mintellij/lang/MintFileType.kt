package com.github.bjansen.mintellij.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MintFileType extends LanguageFileType {

	public static final MintFileType INSTANCE = new MintFileType();

	private MintFileType() {
		super(MintLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return "Mint File";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "Mint";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return "mint";
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return IconLoader
				.getIcon("/mint/icons/logo.svg");
	}
}
