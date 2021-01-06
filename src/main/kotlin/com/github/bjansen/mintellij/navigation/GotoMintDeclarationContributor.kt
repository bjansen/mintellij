package com.github.bjansen.mintellij.navigation

import com.github.bjansen.mintellij.psi.*
import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
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
        StubIndex.getInstance().processElements(
            MintModuleStubIndex.key, name, parameters.project, parameters.searchScope, parameters.idFilter, MintModule::class.java, processor
        )
        StubIndex.getInstance().processElements(
            MintComponentStubIndex.key, name, parameters.project, parameters.searchScope, parameters.idFilter, MintComponent::class.java, processor
        )
        StubIndex.getInstance().processElements(
            MintRecordStubIndex.key, name, parameters.project, parameters.searchScope, parameters.idFilter, MintRecord::class.java, processor
        )
        StubIndex.getInstance().processElements(
            MintStoreStubIndex.key, name, parameters.project, parameters.searchScope, parameters.idFilter, MintStore::class.java, processor
        )
        StubIndex.getInstance().processElements(
            MintEnumStubIndex.key, name, parameters.project, parameters.searchScope, parameters.idFilter, MintEnum::class.java, processor
        )
    }
}
