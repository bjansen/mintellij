package com.github.bjansen.mintellij.lang

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader

object MintFileType : LanguageFileType(MintLanguage) {
	override fun getName() = "Mint File"

	override fun getDescription() = "Mint"

	override fun getDefaultExtension() = "mint"

	override fun getIcon() = IconLoader.getIcon("/mint/icons/logo.svg")
}
