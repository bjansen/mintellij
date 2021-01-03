package com.github.bjansen.mintellij.runConfig

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class MintRunConfigurationProducer : LazyRunConfigurationProducer<MintRunConfiguration>() {

	override fun getConfigurationFactory(): ConfigurationFactory {
		val configurationType = ConfigurationType.CONFIGURATION_TYPE_EP.findExtension(MintRunConfigurationType::class.java)
		return configurationType!!.configurationFactories[0]
	}

	override fun isConfigurationFromContext(configuration: MintRunConfiguration, context: ConfigurationContext): Boolean {
		return configuration.state?.subcommand?.equals("start") ?: false
	}

	override fun setupConfigurationFromContext(configuration: MintRunConfiguration, context: ConfigurationContext, sourceElement: Ref<PsiElement>): Boolean {
		configuration.state?.subcommand = "start"
		configuration.name = "mint start"

		return true
	}
}
