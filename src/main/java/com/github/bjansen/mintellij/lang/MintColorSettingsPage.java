package com.github.bjansen.mintellij.lang;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.util.IconLoader;
import java.util.Map;
import javax.swing.Icon;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MintColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
        new AttributesDescriptor("Keyword", MintSyntaxHighlighter.KEYWORD),
        new AttributesDescriptor("Comment", MintSyntaxHighlighter.COMMENT),
        new AttributesDescriptor("Type", MintSyntaxHighlighter.TYPE),
        new AttributesDescriptor("String", MintSyntaxHighlighter.STRING),
        new AttributesDescriptor("Number", MintSyntaxHighlighter.NUMBER),
        new AttributesDescriptor("Braces and Operators//Operator", MintSyntaxHighlighter.OPERATOR),
        new AttributesDescriptor("Braces and Operators//Brace", MintSyntaxHighlighter.BRACE),
        new AttributesDescriptor("Braces and Operators//Paren", MintSyntaxHighlighter.PAREN),
        new AttributesDescriptor("Braces and Operators//Bracket", MintSyntaxHighlighter.BRACKET),
        new AttributesDescriptor("Braces and Operators//Dot", MintSyntaxHighlighter.DOT),
        new AttributesDescriptor("Braces and Operators//Semicolon", MintSyntaxHighlighter.SEMICOLON),
    };

    @Override
    public @Nullable Icon getIcon() {
        return IconLoader.getIcon("/mint/icons/logo.svg");
    }

    @Override
    public @NotNull SyntaxHighlighter getHighlighter() {
        return new MintSyntaxHighlighter();
    }

    @Override
    public @NonNls
    @NotNull String getDemoText() {
        return "/*\n"
            + " * Mint demo\n"
            + " */\n"
            + "component Counter {\n"
            + "  state counter = 0\n"
            + "\n"
            + "  fun increment {\n"
            + "    next { counter = counter + 1 }\n"
            + "  }\n"
            + "\n"
            + "  fun decrement {\n"
            + "    next { counter = counter - 1 }\n"
            + "  }\n"
            + "\n"
            + "  fun render {\n"
            + "    <div>\n"
            + "      <button onClick={decrement}>\n"
            + "        \"Decrement\"\n"
            + "      </button>\n"
            + "\n"
            + "      <span>\n"
            + "        <{ Number.toString(counter) }>\n"
            + "      </span>\n"
            + "\n"
            + "      <button onClick={increment}>\n"
            + "        \"Increment\"\n"
            + "      </button>\n"
            + "    </div>\n"
            + "  }\n"
            + "}";
    }

    @Override
    public @Nullable Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @Override
    public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @Override
    public ColorDescriptor @NotNull [] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @Override
    public @NotNull String getDisplayName() {
        return "Mint";
    }
}
