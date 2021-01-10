package com.github.bjansen.mintellij.completion

import com.github.bjansen.mintellij.icons.MintIcons
import com.github.bjansen.mintellij.psi.*
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.Project
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import com.intellij.util.ProcessingContext
import com.intellij.util.Processor
import javax.swing.Icon

object MintTypeCompletionProvider : CompletionProvider<CompletionParameters>() {

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val project = parameters.originalFile.project

        processStubIndex(MintComponentStubIndex.key, MintIcons.component, project, result)
        processStubIndex(MintEnumStubIndex.key, MintIcons.enum, project, result)
        processStubIndex(MintModuleStubIndex.key, MintIcons.module, project, result)
        processStubIndex(MintRecordStubIndex.key, MintIcons.record, project, result)
    }

    private fun processStubIndex(index: StubIndexKey<String, *>, icon: Icon?, project: Project, result: CompletionResultSet) {
        val processor = Processor<String> {
            result.addElement(
                LookupElementBuilder.create(it ?: "??")
                    .withIcon(icon)
            )

            return@Processor true
        }

        StubIndex.getInstance().processAllKeys(index, project, processor)
    }
}
