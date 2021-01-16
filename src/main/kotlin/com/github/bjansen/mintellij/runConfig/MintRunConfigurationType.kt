package com.github.bjansen.mintellij.runConfig

import com.github.bjansen.mintellij.icons.MintIcons
import com.intellij.execution.configurations.ConfigurationType

class MintRunConfigurationType : ConfigurationType {

    override fun getDisplayName() = "Mint"

    override fun getConfigurationTypeDescription() = "Mint run configuration type"

    override fun getIcon() = MintIcons.file

    override fun getId() = "MintRunConfiguration"

    override fun getConfigurationFactories() = arrayOf(MintRunConfigurationFactory(this))
}
