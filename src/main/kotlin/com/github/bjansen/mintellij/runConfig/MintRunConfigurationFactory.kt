package com.github.bjansen.mintellij.runConfig

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project

class MintRunConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {

	override fun createTemplateConfiguration(project: Project): RunConfiguration {
		return MintRunConfiguration(project, this, "Mint")
	}

	override fun getName() = "Mint configuration factory"

}
