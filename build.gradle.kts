val ideaVersion: String by project

plugins {
    id("org.jetbrains.intellij") version "0.6.5"
    antlr
}

group = "com.github.bjansen"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

intellij {
    version = ideaVersion
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      Add change notes here.<br>
      <em>most HTML tags may be used</em>""")
    sinceBuild("192")
    untilBuild(null)
}

dependencies {
    antlr("org.antlr:antlr4:4.9")
    implementation("org.antlr:antlr4-runtime:4.9")
    implementation("org.antlr", "antlr4-intellij-adaptor", "0.1")
    testImplementation("junit:junit:4.+")
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-package", "com.github.bjansen.mintellij")
}
