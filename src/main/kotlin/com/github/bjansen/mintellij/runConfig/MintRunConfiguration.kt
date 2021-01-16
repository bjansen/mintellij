package com.github.bjansen.mintellij.runConfig

import com.intellij.execution.Executor
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunConfigurationOptions
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.ColoredProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.util.execution.ParametersListUtil
import com.intellij.util.xmlb.annotations.OptionTag
import java.io.File

class MintRunConfiguration(project: Project, factory: ConfigurationFactory, name: String) :
    RunConfigurationBase<MintRunConfigurationState>(project, factory, name) {

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val args = mutableListOf("mint", state?.subcommand)
                args.addAll(ParametersListUtil.parse(state?.arguments ?: ""))
                val commandLine = GeneralCommandLine(args)
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

    @get:OptionTag(tag = "arguments", valueAttribute = "arguments", nameAttribute = "")
    var arguments: String? by string()
}
