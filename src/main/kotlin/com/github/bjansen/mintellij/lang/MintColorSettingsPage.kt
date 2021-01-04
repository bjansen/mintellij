package com.github.bjansen.mintellij.lang

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.IconLoader
import org.jetbrains.annotations.NonNls

class MintColorSettingsPage : ColorSettingsPage {

	override fun getIcon() = IconLoader.getIcon("/mint/icons/logo.svg")

	override fun getHighlighter() = MintSyntaxHighlighter()

	@NonNls
	override fun getDemoText(): String {
		return """
            /*
             * Mint demo
             */
            component Counter {
              state counter = 0
            
              fun increment {
                next { counter = counter + 1 }
              }
            
              fun decrement {
                next { counter = counter - 1 }
              }
            
              fun render {
                <div>
                  <button onClick={decrement}>
                    "Decrement"
                  </button>
            
                  <span>
                    <{ Number.toString(counter) }>
                  </span>
            
                  <button onClick={increment}>
                    "Increment"
                  </button>
                </div>
              }
            }"""
	}

	override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

	override fun getAttributeDescriptors() = DESCRIPTORS

	override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

	override fun getDisplayName() = "Mint"

	companion object {
		private val DESCRIPTORS = arrayOf(
				AttributesDescriptor("Keyword", MintSyntaxHighlighter.KEYWORD),
				AttributesDescriptor("Comment", MintSyntaxHighlighter.COMMENT),
				AttributesDescriptor("Type", MintSyntaxHighlighter.TYPE),
				AttributesDescriptor("String", MintSyntaxHighlighter.STRING),
				AttributesDescriptor("Number", MintSyntaxHighlighter.NUMBER),
				AttributesDescriptor("Braces and Operators//Operator", MintSyntaxHighlighter.OPERATOR),
				AttributesDescriptor("Braces and Operators//Brace", MintSyntaxHighlighter.BRACE),
				AttributesDescriptor("Braces and Operators//Paren", MintSyntaxHighlighter.PAREN),
				AttributesDescriptor("Braces and Operators//Bracket", MintSyntaxHighlighter.BRACKET),
				AttributesDescriptor("Braces and Operators//Dot", MintSyntaxHighlighter.DOT),
				AttributesDescriptor("Braces and Operators//Semicolon", MintSyntaxHighlighter.SEMICOLON),
				AttributesDescriptor("HTML & Style//HTML tag name", MintSyntaxHighlighter.HTML_TAG),
				AttributesDescriptor("HTML & Style//HTML attribute name", MintSyntaxHighlighter.HTML_ATTR),
				AttributesDescriptor("HTML & Style//Style name", MintSyntaxHighlighter.HTML_STYLE),
				AttributesDescriptor("HTML & Style//Style property name", MintSyntaxHighlighter.STYLE_PROPERTY)
		)
	}
}
