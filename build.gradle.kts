plugins {
    id("org.jetbrains.intellij") version "0.4.16"
    antlr
}

group = "com.github.bjansen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

intellij {
    version = "IC-201-EAP-SNAPSHOT"
}

tasks.getByName<JavaExec>("runIde") {
    systemProperty("idea.auto.reload.plugins", "")
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      Add change notes here.<br>
      <em>most HTML tags may be used</em>""")
}

dependencies {
    antlr("org.antlr:antlr4:4.8-1")
    implementation("org.antlr:antlr4-runtime:4.8-1")
    implementation("org.antlr", "antlr4-intellij-adaptor", "0.1")
    testImplementation("junit:junit:4.+")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-package", "com.github.bjansen.mintellij")
}
