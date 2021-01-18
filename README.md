# Mint plugin for IntelliJ [![Current version](https://img.shields.io/jetbrains/plugin/v/15852?label=Version&style=flat-square)](https://plugins.jetbrains.com/plugin/15852-mint/versions) [![Build](https://img.shields.io/github/workflow/status/bjansen/mintellij/Build/main?label=Build&style=flat-square)](https://github.com/bjansen/mintellij/actions)

<!-- Plugin description -->
This plugin adds support for the [Mint language](https://www.mint-lang.com/) to IntelliJ-based IDEs.
<!-- Plugin description end -->

<img width="1005" alt="image" src="https://user-images.githubusercontent.com/281528/103440330-d92ca180-4c44-11eb-9ae8-f852277db51c.png">

## Status

*This is a toy project, there is no guarantee that it will be actively developed.*

**Working**:
* configurable syntax highlighting
* most of the syntax is accepted by the parser
* Go To declaration (records, stores, components etc)
* run `mint` commands from the IDE
* basic code completion

**Coming next**:
* improve parser to support missing constructs
* detect Mint installation, Mint project type
* navigation, find occurrences
