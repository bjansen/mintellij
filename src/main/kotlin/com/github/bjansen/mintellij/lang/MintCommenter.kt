package com.github.bjansen.mintellij.lang

import com.intellij.lang.Commenter

class MintCommenter : Commenter {
	override fun getLineCommentPrefix(): String? = null

	override fun getBlockCommentPrefix() = "/*"

	override fun getBlockCommentSuffix() = "*/"

	override fun getCommentedBlockCommentPrefix(): String? = null

	override fun getCommentedBlockCommentSuffix(): String? = null
}
