package com.github.bjansen.mintellij.runConfig

import com.github.bjansen.mintellij.MintLexer
import com.github.bjansen.mintellij.psi.matchesAntlrToken
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
        return if (context.psiLocation?.matchesAntlrToken(MintLexer.TypeId) == true) {
            // Main component
            configuration.state?.subcommand?.equals("start") ?: false
        } else {
            // A test suite
            configuration.state?.subcommand?.equals("test") ?: false
        }
    }

    override fun setupConfigurationFromContext(
        configuration: MintRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {
        if (context.psiLocation?.matchesAntlrToken(MintLexer.TypeId) == true) {
            // Main component
            configuration.state?.subcommand = "start"
            configuration.name = "mint start"
        } else {
            // A test suite
            configuration.state?.subcommand = "test"
            configuration.state?.arguments = "--reporter documentation"
            configuration.name = "mint test"
        }

        return true
    }
}
