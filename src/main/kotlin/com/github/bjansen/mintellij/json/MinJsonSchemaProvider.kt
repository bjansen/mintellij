package com.github.bjansen.mintellij.json

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory
import com.jetbrains.jsonSchema.extension.SchemaType

class MinJsonSchemaProviderFactory : JsonSchemaProviderFactory {
    override fun getProviders(project: Project): MutableList<JsonSchemaFileProvider> {
        return mutableListOf(MinJsonSchemaProvider)
    }
}

object MinJsonSchemaProvider : JsonSchemaFileProvider {
    override fun isAvailable(file: VirtualFile) = file.name == "mint.json"

    override fun getName() = "Mint"

    override fun getSchemaFile() =
        JsonSchemaProviderFactory.getResourceFile(
            MinJsonSchemaProviderFactory::class.java,
            "/schemas/mint.schema.json"
        )

    override fun getSchemaType() = SchemaType.embeddedSchema
}
