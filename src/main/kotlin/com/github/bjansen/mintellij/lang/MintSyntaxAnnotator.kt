package com.github.bjansen.mintellij.lang

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.psi.matchesAntlrRule
import com.github.bjansen.mintellij.psi.matchesAntlrToken
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

/**
 * Adds syntax highlighting to HTML, CSS etc.
 */
class MintSyntaxAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element.matchesAntlrToken(MintLexer.Variable)) {
            if (element.parent?.matchesAntlrRule(MintParser.RULE_tag_name) == true) {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element)
                    .textAttributes(MintSyntaxHighlighter.HTML_TAG)
                    .create()
            } else if (element.parent?.matchesAntlrRule(MintParser.RULE_html_style) == true
                || element.parent?.matchesAntlrRule(MintParser.RULE_style) == true
            ) {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element)
                    .textAttributes(MintSyntaxHighlighter.HTML_STYLE)
                    .create()
            }
        }

        if (element.matchesAntlrRule(MintParser.RULE_variable) && element.parent?.matchesAntlrRule(MintParser.RULE_html_attribute) == true) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(element)
                .textAttributes(MintSyntaxHighlighter.HTML_ATTR)
                .create()
        }

        if (element.matchesAntlrToken(MintLexer.VariableWithDashes)
            || element.matchesAntlrToken(MintLexer.Variable)
        ) {
            if (element.parent?.matchesAntlrRule(MintParser.RULE_css_definition) == true) {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element)
                    .textAttributes(MintSyntaxHighlighter.STYLE_PROPERTY)
                    .create()
            }
        }
    }
}
