package com.github.bjansen.mintellij.icons

import com.intellij.openapi.util.IconLoader

object MintIcons {
    val file = IconLoader.getIcon("/mint/icons/logo.svg", MintIcons::class.java)
    val component = IconLoader.getIcon("/mint/icons/nodes/component.svg", MintIcons::class.java)
    val enum = IconLoader.getIcon("/mint/icons/nodes/enum.svg", MintIcons::class.java)
    val function = IconLoader.getIcon("/mint/icons/nodes/function.svg", MintIcons::class.java)
    val module = IconLoader.getIcon("/mint/icons/nodes/module.svg", MintIcons::class.java)
    val property = IconLoader.getIcon("/mint/icons/nodes/property.svg", MintIcons::class.java)
    val provider = IconLoader.getIcon("/mint/icons/nodes/provider.svg", MintIcons::class.java)
    val record = IconLoader.getIcon("/mint/icons/nodes/record.svg", MintIcons::class.java)
    val state = IconLoader.getIcon("/mint/icons/nodes/state.svg", MintIcons::class.java)
    val store = IconLoader.getIcon("/mint/icons/nodes/store.svg", MintIcons::class.java)
}
