package com.github.bjansen.mintellij.runConfig

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.process.ColoredProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.util.execution.ParametersListUtil
import com.intellij.util.xmlb.annotations.OptionTag
import java.io.File

class MintRunConfiguration(project: Project, factory: ConfigurationFactory, name: String)
	: RunConfigurationBase<MintRunConfigurationState>(project, factory, name) {

	override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
		return object : CommandLineState(environment) {
			override fun startProcess(): ProcessHandler {
				val args = ParametersListUtil.parseToArray(state?.arguments ?: "")
				val commandLine = GeneralCommandLine("mint", state?.subcommand, *args)
				val basePath = environment.project.basePath
				if (basePath != null) {
					commandLine.workDirectory = File(basePath)
				}

				return ColoredProcessHandler(commandLine)
			}
		}
	}

	override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
		return MintSettingsEditor()
	}

	override fun getOptionsClass(): Class<out RunConfigurationOptions> {
		return MintRunConfigurationState::class.java
	}
}

class MintRunConfigurationState : RunConfigurationOptions() {
	@get:OptionTag(tag = "subcommand", valueAttribute = "name", nameAttribute = "")
	var subcommand: String? by string()

	@get:OptionTag(tag = "subcommand", valueAttribute = "arguments", nameAttribute = "")
	var arguments: String? by string()
}
