package com.github.bjansen.mintellij.navigation

import com.github.bjansen.mintellij.psi.MintModule
import com.github.bjansen.mintellij.psi.MintModuleStubIndex
import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.util.Processor
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter

class GotoModuleContributor : ChooseByNameContributorEx {

    override fun processNames(processor: Processor<in String>, scope: GlobalSearchScope, filter: IdFilter?) {
        StubIndex.getInstance().processAllKeys(
            MintModuleStubIndex.key,
            processor,
            scope,
            filter
        )
    }

    override fun processElementsWithName(name: String, processor: Processor<in NavigationItem>, parameters: FindSymbolParameters) {
        StubIndex.getInstance().processElements(
            MintModuleStubIndex.key,
            name,
            parameters.project,
            parameters.searchScope,
            parameters.idFilter,
            MintModule::class.java,
            processor
        )
    }
}
