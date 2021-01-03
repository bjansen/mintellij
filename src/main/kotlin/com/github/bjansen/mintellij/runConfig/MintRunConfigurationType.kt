package com.github.bjansen.mintellij.runConfig

import com.intellij.execution.configurations.ConfigurationType
import com.intellij.openapi.util.IconLoader

class MintRunConfigurationType : ConfigurationType {

	override fun getDisplayName() = "Mint"

	override fun getConfigurationTypeDescription() = "Mint run configuration type"

	override fun getIcon() = IconLoader.getIcon("/mint/icons/logo.svg")

	override fun getId() = "MintRunConfiguration"

	override fun getConfigurationFactories() = arrayOf(MintRunConfigurationFactory(this))
}
