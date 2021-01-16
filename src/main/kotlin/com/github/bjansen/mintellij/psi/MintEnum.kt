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

class MintEnum :
    MintPsiElement,
    StubBasedPsiElementBase<MintEnumStub>,
    StubBasedPsiElement<MintEnumStub>,
    PsiNameIdentifierOwner {

    constructor(node: ASTNode) : super(node)
    constructor(stub: MintEnumStub) : super(stub, MintEnumStubElementType)

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

    override fun getElementIcon(flags: Int) = MintIcons.enum
}

interface MintEnumStub : StubElement<MintEnum> {
    fun getName(): String
}

class MintEnumStubImpl(parent: StubElement<*>?, private val name: String) :
    StubBase<MintEnum>(parent, MintEnumStubElementType),
    MintEnumStub {

    override fun getName(): String {
        return name
    }
}

object MintEnumStubElementType : IStubElementType<MintEnumStub, MintEnum>("MintEnumStub", MintLanguage) {
    override fun getExternalId(): String {
        return "Mint.enum"
    }

    override fun serialize(stub: MintEnumStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.getName())
    }

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MintEnumStub {
        return MintEnumStubImpl(parentStub, dataStream.readNameString() ?: "?")
    }

    override fun indexStub(stub: MintEnumStub, sink: IndexSink) {
        sink.occurrence(MintEnumStubIndex.key, stub.getName())
    }

    override fun createPsi(stub: MintEnumStub): MintEnum {
        return MintEnum(stub)
    }

    override fun createStub(psi: MintEnum, parentStub: StubElement<*>?): MintEnumStub {
        return MintEnumStubImpl(parentStub, psi.name ?: "?")
    }
}

class MintEnumStubIndex : StringStubIndexExtension<MintEnum>() {
    override fun getKey(): StubIndexKey<String, MintEnum> {
        return MintEnumStubIndex.key
    }

    companion object {
        val key: StubIndexKey<String, MintEnum> = StubIndexKey.createIndexKey("MintEnums")
    }
}
