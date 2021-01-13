package com.github.bjansen.mintellij.lang

import com.github.bjansen.mintellij.icons.MintIcons
import com.intellij.openapi.fileTypes.LanguageFileType

object MintFileType : LanguageFileType(MintLanguage) {
	override fun getName() = "Mint File"

	override fun getDescription() = "Mint"

	override fun getDefaultExtension() = "mint"

	override fun getIcon() = MintIcons.file
}
