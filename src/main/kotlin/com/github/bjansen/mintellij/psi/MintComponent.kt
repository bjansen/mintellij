package com.github.bjansen.mintellij.psi

import com.github.bjansen.mintellij.MintParser
import com.github.bjansen.mintellij.icons.MintIcons
import com.github.bjansen.mintellij.lang.MintLanguage
import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentationProviders
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.StubBasedPsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubIndexKey
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream

class MintComponent :
    MintPsiElement,
    StubBasedPsiElementBase<MintComponentStub>,
    StubBasedPsiElement<MintComponentStub>,
    PsiNameIdentifierOwner {

    constructor(node: ASTNode) : super(node)
    constructor(stub: MintComponentStub) : super(stub, MintComponentStubElementType)

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(MintParserDefinition.getRuleType(MintParser.RULE_type_id))?.psi
    }

    override fun getPresentation() = ItemPresentationProviders.getItemPresentation(this)

    override fun getElementIcon(flags: Int) = MintIcons.component
}

interface MintComponentStub : StubElement<MintComponent> {
    fun getName(): String
}

class MintComponentStubImpl(parent: StubElement<*>?, private val name: String) :
    StubBase<MintComponent>(parent, MintComponentStubElementType),
    MintComponentStub {

    override fun getName(): String {
        return name
    }
}

object MintComponentStubElementType : IStubElementType<MintComponentStub, MintComponent>("MintComponentStub", MintLanguage) {
    override fun getExternalId(): String {
        return "Mint.component"
    }

    override fun serialize(stub: MintComponentStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.getName())
    }

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MintComponentStub {
        return MintComponentStubImpl(parentStub, dataStream.readNameString() ?: "?")
    }

    override fun indexStub(stub: MintComponentStub, sink: IndexSink) {
        sink.occurrence(MintComponentStubIndex.key, stub.getName())
    }

    override fun createPsi(stub: MintComponentStub): MintComponent {
        return MintComponent(stub)
    }

    override fun createStub(psi: MintComponent, parentStub: StubElement<*>?): MintComponentStub {
        return MintComponentStubImpl(parentStub, psi.name ?: "?")
    }
}

class MintComponentStubIndex : StringStubIndexExtension<MintComponent>() {
    override fun getKey(): StubIndexKey<String, MintComponent> {
        return MintComponentStubIndex.key
    }

    companion object {
        val key: StubIndexKey<String, MintComponent> = StubIndexKey.createIndexKey("MintComponents")
    }
}
