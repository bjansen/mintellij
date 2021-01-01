import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ideaVersion: String by project

plugins {
    id("org.jetbrains.intellij") version "0.6.5"
    antlr
    java
    kotlin("jvm") version "1.3.72"
}

group = "com.github.bjansen"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

intellij {
    version = ideaVersion
}

tasks {
    generateGrammarSource {
        arguments = arguments + listOf("-visitor", "-package", "com.github.bjansen.mintellij")

        doLast {
            val parserPackagePath = "${outputDirectory.canonicalPath}/com/github/bjansen/mintellij"

            file(parserPackagePath).mkdirs()

            copy {
                from(outputDirectory.canonicalPath)
                into(parserPackagePath)
                include("Mint*")
            }
            delete(fileTree(outputDirectory.canonicalPath) {
                include("Mint*")
            })
        }
    }

    withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    withType<KotlinCompile> {
        mustRunAfter(generateGrammarSource)

        kotlinOptions.jvmTarget = "1.8"
    }

    patchPluginXml {
        changeNotes(
            """
              Add change notes here.<br>
              <em>most HTML tags may be used</em>"""
        )
        sinceBuild("192")
        untilBuild(null)
    }
}

dependencies {
    antlr("org.antlr:antlr4:4.9")
    implementation("org.antlr:antlr4-runtime:4.9")
    implementation("org.antlr", "antlr4-intellij-adaptor", "0.1")
    testImplementation("junit:junit:4.+")
}
