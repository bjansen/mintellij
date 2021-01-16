package com.github.bjansen.mintellij.psi

import com.github.bjansen.mintellij.MintLexer
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

class MintStore :
    MintPsiElement,
    StubBasedPsiElementBase<MintStoreStub>,
    StubBasedPsiElement<MintStoreStub>,
    PsiNameIdentifierOwner {

    constructor(node: ASTNode) : super(node)
    constructor(stub: MintStoreStub) : super(stub, MintStoreStubElementType)

    override fun getName(): String? {
        return nameIdentifier?.text
    }

    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(MintParserDefinition.getTokenType(MintLexer.TypeId))?.psi
    }

    override fun getPresentation() = ItemPresentationProviders.getItemPresentation(this)

    override fun getElementIcon(flags: Int) = MintIcons.store
}

interface MintStoreStub : StubElement<MintStore> {
    fun getName(): String
}

class MintStoreStubImpl(parent: StubElement<*>?, private val name: String) :
    StubBase<MintStore>(parent, MintStoreStubElementType),
    MintStoreStub {

    override fun getName(): String {
        return name
    }
}

object MintStoreStubElementType : IStubElementType<MintStoreStub, MintStore>("MintStoreStub", MintLanguage) {
    override fun getExternalId(): String {
        return "Mint.store"
    }

    override fun serialize(stub: MintStoreStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.getName())
    }

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MintStoreStub {
        return MintStoreStubImpl(parentStub, dataStream.readNameString() ?: "?")
    }

    override fun indexStub(stub: MintStoreStub, sink: IndexSink) {
        sink.occurrence(MintStoreStubIndex.key, stub.getName())
    }

    override fun createPsi(stub: MintStoreStub): MintStore {
        return MintStore(stub)
    }

    override fun createStub(psi: MintStore, parentStub: StubElement<*>?): MintStoreStub {
        return MintStoreStubImpl(parentStub, psi.name ?: "?")
    }
}

class MintStoreStubIndex : StringStubIndexExtension<MintStore>() {
    override fun getKey(): StubIndexKey<String, MintStore> {
        return MintStoreStubIndex.key
    }

    companion object {
        val key: StubIndexKey<String, MintStore> = StubIndexKey.createIndexKey("MintStores")
    }
}
