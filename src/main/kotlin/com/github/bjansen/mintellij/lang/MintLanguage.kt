package com.github.bjansen.mintellij.lang;

import com.intellij.lang.Language;

public class MintLanguage extends Language {

	public static final MintLanguage INSTANCE = new MintLanguage();

	private MintLanguage() {
		super("Mint");
	}
}
