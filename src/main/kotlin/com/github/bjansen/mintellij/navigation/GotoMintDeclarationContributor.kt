package com.github.bjansen.mintellij.navigation

import com.github.bjansen.mintellij.psi.MintComponent
import com.github.bjansen.mintellij.psi.MintComponentStubIndex
import com.github.bjansen.mintellij.psi.MintEnum
import com.github.bjansen.mintellij.psi.MintEnumStubIndex
import com.github.bjansen.mintellij.psi.MintModule
import com.github.bjansen.mintellij.psi.MintModuleStubIndex
import com.github.bjansen.mintellij.psi.MintRecord
import com.github.bjansen.mintellij.psi.MintRecordStubIndex
import com.github.bjansen.mintellij.psi.MintStore
import com.github.bjansen.mintellij.psi.MintStoreStubIndex
import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import com.intellij.util.Processor
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter

class GotoMintDeclarationContributor : ChooseByNameContributorEx {

    override fun processNames(processor: Processor<in String>, scope: GlobalSearchScope, filter: IdFilter?) {
        StubIndex.getInstance().processAllKeys(MintModuleStubIndex.key, processor, scope, filter)
        StubIndex.getInstance().processAllKeys(MintComponentStubIndex.key, processor, scope, filter)
        StubIndex.getInstance().processAllKeys(MintRecordStubIndex.key, processor, scope, filter)
        StubIndex.getInstance().processAllKeys(MintStoreStubIndex.key, processor, scope, filter)
        StubIndex.getInstance().processAllKeys(MintEnumStubIndex.key, processor, scope, filter)
    }

    override fun processElementsWithName(name: String, processor: Processor<in NavigationItem>, parameters: FindSymbolParameters) {
        val project = parameters.project
        val scope = parameters.searchScope
        val idFilter = parameters.idFilter

        fun <Psi : PsiElement> processStub(key: StubIndexKey<String, Psi>, clazz: Class<Psi>, processor: Processor<in Psi>) {
            StubIndex.getInstance().processElements(key, name, project, scope, idFilter, clazz, processor)
        }

        processStub(MintModuleStubIndex.key, MintModule::class.java, processor)
        processStub(MintComponentStubIndex.key, MintComponent::class.java, processor)
        processStub(MintRecordStubIndex.key, MintRecord::class.java, processor)
        processStub(MintStoreStubIndex.key, MintStore::class.java, processor)
        processStub(MintEnumStubIndex.key, MintEnum::class.java, processor)
    }
}
