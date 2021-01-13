package com.github.bjansen.mintellij.completion

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import org.junit.Test

class MintCompletionContributorTest : LightPlatformCodeInsightFixture4TestCase() {

    override fun getTestDataPath() = "src/test/resources/completion"

    @Test
    fun testCompletionOfReturnTypes() {
        myFixture.configureByFiles("return-types.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertContainsElements(
            lookups.map { it.lookupString },
            "String", "Maybe", "Math", "Array"
        )
    }

    @Test
    fun testCompletionOfFunctionParameterTypes() {
        myFixture.configureByFiles("function-parameter-type.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertContainsElements(
            lookups.map { it.lookupString },
            "String", "Maybe", "Math", "Array"
        )
    }

    @Test
    fun testCompletionOfDeclarationNames() {
        myFixture.configureByFiles("declaration-name.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertEmpty(lookups)
    }

    @Test
    fun testCompletionOfModuleInExpression() {
        myFixture.configureByFiles("module-expression.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertContainsElements(
            lookups.map { it.lookupString },
            "String", "Maybe", "Math", "Array"
        )
    }

    @Test
    fun testCompletionOfModuleInExpressionAfterPartialName() {
        myFixture.configureByFiles("module-expression-2.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertContainsElements(
            lookups.map { it.lookupString },
            "Maybe", "Math"
        )
    }

    @Test
    fun testCompletionOfModuleInParens() {
        myFixture.configureByFiles("module-expression-3.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertContainsElements(
            lookups.map { it.lookupString },
            "String", "Maybe", "Math", "Array"
        )
    }

    @Test
    fun testCompletionOfModuleInBody() {
        myFixture.configureByFiles("module-expression-4.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertContainsElements(
            lookups.map { it.lookupString },
            "String", "Maybe", "Math", "Array"
        )
    }

    @Test
    fun testCompletionOfModuleAccess() {
        myFixture.configureByFiles("module-access.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertContainsElements(
            lookups.map { it.lookupString },
            "Maybe", "Math"
        )
    }

    @Test
    fun testCompletionOfFunctionParameterInBodyNoPrefix() {
        myFixture.configureByFiles("function-parameter.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)
        assertContainsElements(
            lookups.map { it.lookupString },
            "foo"
        )
    }

    @Test
    fun testCompletionOfFunctionParameterInBodyWithPrefix() {
        myFixture.configureByFiles("function-parameter-2.mint", "mint-core.mint")

        val lookups = myFixture.complete(CompletionType.BASIC)

        assertContainsElements(
            lookups.map { it.lookupString },
            "myParam", "myOtherParam"
        )
    }
}
