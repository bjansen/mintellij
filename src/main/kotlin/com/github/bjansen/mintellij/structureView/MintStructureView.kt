package com.github.bjansen.mintellij.structureView

import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.psi.*
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.*
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.ItemPresentationProviders
import com.intellij.openapi.editor.Editor
import com.intellij.psi.*
import com.intellij.util.containers.toArray

class MintStructureViewFactory : PsiStructureViewFactory {
    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder {
        return object : TreeBasedStructureViewBuilder() {
            override fun createStructureViewModel(editor: Editor?) = MintStructureViewModel(psiFile)
        }
    }
}

class MintStructureViewModel(psiFile: PsiFile) :
    StructureViewModelBase(psiFile, MintStructureViewElement(psiFile as MintFile)),
    StructureViewModel.ElementInfoProvider {

    override fun getSorters(): Array<Sorter> {
        return arrayOf(Sorter.ALPHA_SORTER)
    }

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement?): Boolean {
        return false
    }
    override fun isAlwaysLeaf(element: StructureViewTreeElement?): Boolean {
        return false
    }

}

class MintStructureViewElement(val psiElement: MintPsiElement) : StructureViewTreeElement, SortableTreeElement {

    override fun getPresentation(): ItemPresentation {
        val presentation = ItemPresentationProviders.getItemPresentation(psiElement)

        if (presentation != null) {
            return PresentationData(presentation.presentableText, null, presentation.getIcon(false), null)
        }
        return object : ItemPresentation {
            override fun getPresentableText() = "unknown ${psiElement::class.java}"
            override fun getLocationString() = null
            override fun getIcon(unused: Boolean) = null
        }
    }

    override fun getChildren(): Array<TreeElement> {
        val visitor = MintStructureViewVisitor()
        psiElement.accept(visitor)

        return visitor.declarations
    }

    override fun navigate(requestFocus: Boolean) = psiElement.navigate(requestFocus)

    override fun canNavigate() = psiElement.canNavigate()

    override fun canNavigateToSource() = psiElement.canNavigateToSource()

    override fun getValue() = psiElement

    override fun getAlphaSortKey() = psiElement.name ?: ""
}

class MintStructureViewVisitor : PsiElementVisitor() {

    var declarations = emptyArray<TreeElement>()

    private fun findDeclarationsIn(elements: Array<PsiElement>) {
        declarations = elements
            .filterIsInstance<PsiNameIdentifierOwner>()
            .filterIsInstance<MintPsiElement>()
            .map { MintStructureViewElement(it) }
            .toArray(emptyArray())
    }

    override fun visitFile(file: PsiFile) {
        findDeclarationsIn(file.firstChildMatchingAntlrRule(MintParser.RULE_topLevel)?.children ?: emptyArray())
    }

    override fun visitElement(element: PsiElement) {
        when (element) {
            is MintComponent -> visitComponent(element)
            is MintEnum -> visitEnum(element)
            is MintModule -> visitModule(element)
            is MintRecord -> visitRecord(element)
            is MintStore -> visitStore(element)
        }
    }

    fun visitComponent(element: MintComponent) {
        val blockChildren = element.firstChildMatchingAntlrRule(MintParser.RULE_component_block)?.children

        if (blockChildren != null) {
            findDeclarationsIn(blockChildren)
        }
    }

    fun visitEnum(element: MintEnum) {
        val blockChildren = element.firstChildMatchingAntlrRule(MintParser.RULE_enum_block)?.children

        if (blockChildren != null) {
            findDeclarationsIn(blockChildren)
        }
    }

    fun visitModule(element: MintModule) {
        val blockChildren = element.firstChildMatchingAntlrRule(MintParser.RULE_block)?.children

        if (blockChildren != null) {
            findDeclarationsIn(blockChildren)
        }
    }

    fun visitRecord(element: MintRecord) {
        val blockChildren = element.firstChildMatchingAntlrRule(MintParser.RULE_record_definitions_block)?.children

        if (blockChildren != null) {
            findDeclarationsIn(blockChildren)
        }
    }

    fun visitStore(element: MintStore) {
        val blockChildren = element.firstChildMatchingAntlrRule(MintParser.RULE_store_block)?.children

        if (blockChildren != null) {
            findDeclarationsIn(blockChildren)
        }
    }
}
