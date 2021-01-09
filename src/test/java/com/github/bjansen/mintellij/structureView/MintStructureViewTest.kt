package com.github.bjansen.mintellij.structureView

import com.github.bjansen.mintellij.icons.MintIcons
import com.github.bjansen.mintellij.lang.MintFileType
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import com.intellij.ui.IconTestUtil.renderDeferredIcon
import org.junit.Test
import javax.swing.Icon

class MintStructureViewTest : LightPlatformCodeInsightFixture4TestCase() {

    override fun getTestDataPath() = "src/test/resources/structureView"

    @Test
    fun testModule() {
        myFixture.configureByFile("module.mint")
        myFixture.testStructureView {
            val model = it.treeModel

            val expected =
                root(MintFileType.icon, "module.mint") {
                    item(MintIcons.module, "Array") {
                        item(MintIcons.function, "first(Array(a))")
                    }
                }

            assertEquals(expected, model.root)
        }
    }

    @Test
    fun testComponent() {
        myFixture.configureByFile("component.mint")
        myFixture.testStructureView {
            val model = it.treeModel

            val expected =
                root(MintFileType.icon, "component.mint") {
                    item(MintIcons.component, "Main") {
                        item(MintIcons.function, "render()")
                    }
                }

            assertEquals(expected, model.root)
        }
    }

    @Test
    fun testMixedDeclarations() {
        myFixture.configureByFile("mixed.mint")
        myFixture.testStructureView {
            val model = it.treeModel

            val expected =
                root(MintFileType.icon, "mixed.mint") {
                    item(MintIcons.component, "SameName") {
                        item(MintIcons.function, "func(Html)")
                        item(MintIcons.function, "render()")
                    }
                    item(MintIcons.record, "SameName4")
                    item(MintIcons.store, "SameName2") {
                        item(MintIcons.function, "foo()")
                    }
                    item(MintIcons.module, "SameName3") {
                        item(MintIcons.function, "foo()")
                    }
                    item(MintIcons.enum, "SameName")
                }

            assertEquals(expected, model.root)
        }
    }

    private fun root(icon: Icon?, text: String, children: StructureItem.() -> Unit): StructureItem {
        return StructureItem(icon, text, children)
    }

    private fun assertEquals(expected: StructureItem, actual: StructureViewTreeElement) {
        assertEquals(expected.text, actual.presentation.presentableText)
        assertIconEquals(expected.icon, actual.presentation.getIcon(false))
        assertNull(actual.presentation.locationString)

        assertEquals(expected.children.size, actual.children.size)

        if (expected.children.isNotEmpty()) {
            for (i in 0 until expected.children.size) {
                assertEquals(expected.children[i], actual.children[i] as StructureViewTreeElement)
            }
        }
    }

    private fun assertIconEquals(expected: Icon?, actual: Icon?) {
        // TODO this is very slow (several seconds!)
        assertTrue(renderDeferredIcon(actual).contains(expected))
    }
}

class StructureItem(val icon: Icon?, val text: String, childrenBuilder: (StructureItem.() -> Unit) = {}) {

    val children = mutableListOf<StructureItem>()

    init {
        childrenBuilder.invoke(this)
    }

    fun item(icon: Icon?, text: String, childrenBuilder: StructureItem.() -> Unit = {}): StructureItem {
        val item = StructureItem(icon, text, childrenBuilder)

        children.add(item)

        return item
    }
}
