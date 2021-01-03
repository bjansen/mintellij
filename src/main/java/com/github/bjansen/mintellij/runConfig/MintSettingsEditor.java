package com.github.bjansen.mintellij.runConfig;

import com.intellij.openapi.options.SettingsEditor;
import com.intellij.ui.RawCommandLineEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MintSettingsEditor extends SettingsEditor<MintRunConfiguration> {

    private JPanel mainPanel;
    private JComboBox subcommand;
    private RawCommandLineEditor arguments;

    @Override
    protected void resetEditorFrom(@NotNull MintRunConfiguration s) {
        MintRunConfigurationState state = s.getState();

        if (state != null) {
            subcommand.setSelectedItem(state.getSubcommand());
            arguments.setText(state.getArguments());
        }
    }

    @Override
    protected void applyEditorTo(@NotNull MintRunConfiguration s) {
        Object selectedItem = subcommand.getSelectedItem();
        MintRunConfigurationState state = s.getState();

        if (state != null) {
            if (selectedItem != null) {
                state.setSubcommand(selectedItem.toString());
            }
            state.setArguments(arguments.getText());
        }
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return mainPanel;
    }
}
