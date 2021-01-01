package com.github.bjansen.mintellij.lang;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

public class MintCommenter implements Commenter {
	@Nullable
	@Override
	public String getLineCommentPrefix() {
		return null;
	}

	@Nullable
	@Override
	public String getBlockCommentPrefix() {
		return "/*";
	}

	@Nullable
	@Override
	public String getBlockCommentSuffix() {
		return "*/";
	}

	@Nullable
	@Override
	public String getCommentedBlockCommentPrefix() {
		return null;
	}

	@Nullable
	@Override
	public String getCommentedBlockCommentSuffix() {
		return null;
	}
}
