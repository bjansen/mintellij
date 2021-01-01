package com.github.bjansen.mintellij.lang;

import static com.github.bjansen.mintellij.psi.MintParserDefinition.getTokenType;

import com.github.bjansen.mintellij.MintLexer;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MintBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[] {
        newPair(MintLexer.LBrace, MintLexer.RBrace),
        newPair(MintLexer.LBracket, MintLexer.RBracket),
        newPair(MintLexer.LParen, MintLexer.RParen),
    };

    private static BracePair newPair(int left, int right) {
        return new BracePair(getTokenType(left), getTokenType(right), false);
    }

    @Override
    public BracePair @NotNull [] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType,
        @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
