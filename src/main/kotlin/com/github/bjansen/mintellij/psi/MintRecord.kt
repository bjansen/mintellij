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

class MintRecord :
    MintPsiElement,
    StubBasedPsiElementBase<MintRecordStub>,
    StubBasedPsiElement<MintRecordStub>,
    PsiNameIdentifierOwner {

    constructor(node: ASTNode) : super(node)
    constructor(stub: MintRecordStub) : super(stub, MintRecordStubElementType)

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

    override fun getElementIcon(flags: Int) = MintIcons.record
}

interface MintRecordStub : StubElement<MintRecord> {
    fun getName(): String
}

class MintRecordStubImpl(parent: StubElement<*>?, private val name: String) :
    StubBase<MintRecord>(parent, MintRecordStubElementType),
    MintRecordStub {

    override fun getName(): String {
        return name
    }
}

object MintRecordStubElementType : IStubElementType<MintRecordStub, MintRecord>("MintRecordStub", MintLanguage) {
    override fun getExternalId(): String {
        return "Mint.record"
    }

    override fun serialize(stub: MintRecordStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.getName())
    }

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MintRecordStub {
        return MintRecordStubImpl(parentStub, dataStream.readNameString() ?: "?")
    }

    override fun indexStub(stub: MintRecordStub, sink: IndexSink) {
        sink.occurrence(MintRecordStubIndex.key, stub.getName())
    }

    override fun createPsi(stub: MintRecordStub): MintRecord {
        return MintRecord(stub)
    }

    override fun createStub(psi: MintRecord, parentStub: StubElement<*>?): MintRecordStub {
        return MintRecordStubImpl(parentStub, psi.name ?: "?")
    }
}

class MintRecordStubIndex : StringStubIndexExtension<MintRecord>() {
    override fun getKey(): StubIndexKey<String, MintRecord> {
        return MintRecordStubIndex.key
    }

    companion object {
        val key: StubIndexKey<String, MintRecord> = StubIndexKey.createIndexKey("MintRecords")
    }
}
