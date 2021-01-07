package com.github.bjansen.mintellij.psi

import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.icons.MintIcons
import com.github.bjansen.mintellij.lang.MintLanguage
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.ItemPresentationProviders
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.StubBasedPsiElement
import com.intellij.psi.stubs.*
import javax.swing.Icon

class MintModule :
    MintPsiElement,
    StubBasedPsiElementBase<MintModuleStub>,
    StubBasedPsiElement<MintModuleStub>,
    PsiNameIdentifierOwner {

    constructor(node: ASTNode) : super(node)
    constructor(stub: MintModuleStub) : super(stub, MintModuleStubElementType)

    override fun getName() : String? {
        return nameIdentifier?.text
    }

    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(MintParserDefinition.getRuleType(MintParser.RULE_type_id))?.psi
    }

    override fun getPresentation() = ItemPresentationProviders.getItemPresentation(this)

    override fun getElementIcon(flags: Int) = MintIcons.module
}

interface MintModuleStub : StubElement<MintModule> {
    fun getName(): String
}

class MintModuleStubImpl(parent: StubElement<*>?, private val name: String) :
    StubBase<MintModule>(parent, MintModuleStubElementType),
    MintModuleStub {

    override fun getName(): String {
        return name
    }
}

object MintModuleStubElementType : IStubElementType<MintModuleStub, MintModule>("MintModuleStub", MintLanguage) {
    override fun getExternalId(): String {
        return "Mint.module"
    }

    override fun serialize(stub: MintModuleStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.getName())
    }

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MintModuleStub {
        return MintModuleStubImpl(parentStub, dataStream.readNameString() ?: "?")
    }

    override fun indexStub(stub: MintModuleStub, sink: IndexSink) {
        sink.occurrence(MintModuleStubIndex.key, stub.getName())
    }

    override fun createPsi(stub: MintModuleStub): MintModule {
        return MintModule(stub)
    }

    override fun createStub(psi: MintModule, parentStub: StubElement<*>?): MintModuleStub {
        return MintModuleStubImpl(parentStub, psi.name ?: "?")
    }
}

class MintModuleStubIndex : StringStubIndexExtension<MintModule>() {
    override fun getKey(): StubIndexKey<String, MintModule> {
        return MintModuleStubIndex.key
    }

    companion object {
        val key: StubIndexKey<String, MintModule> = StubIndexKey.createIndexKey("MintModules")
    }
}
