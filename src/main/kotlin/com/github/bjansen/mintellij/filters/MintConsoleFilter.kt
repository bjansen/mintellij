package com.github.bjansen.mintellij.filters

import com.intellij.execution.filters.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import java.util.regex.Pattern

/**
 * Adds hyperlinks to the console output to navigate from error locations to the actual source code.
 *
 * For example:
 *
 * <code>┌──┬ source/Routes.mint:15:1 ────┐</code>
 */
class MintConsoleFilterProvider : ConsoleFilterProvider {

	override fun getDefaultFilters(project: Project) = arrayOf(MintConsoleFilter(project))

}

class MintConsoleFilter(private val project: Project) : Filter {

    private val filePattern: Pattern = Pattern.compile("(.*):(\\d+):(\\d+)")

	override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
		if (line.length > 2 && line[0] == '┌') {
            val start = line.indexOf('┬')

            if (start > 0) {
                val end = line.indexOf('─', start)

                if (start + 2 < end - 1) {
                    val trimmed = line.substring(start + 2, end - 1)

                    val startInConsole = entireLength - line.length + start + 2
                    val endInConsole = entireLength - line.length + end - 1
                    return createHyperlink(trimmed, startInConsole, endInConsole)
                }
            }
		}

        return null
	}

    private fun createHyperlink(rawPath: CharSequence, start: Int, end: Int): Filter.Result? {
        val matcher = filePattern.matcher(rawPath)


        if (matcher.matches()) {
            val file = project.guessProjectDir()?.findFileByRelativePath(matcher.group(1))

            if (file != null){
                val link = OpenFileHyperlinkInfo(
                    project,
                    file,
                    matcher.group(2).toInt() - 1,
                    matcher.group(3).toInt() - 1
                )

                return Filter.Result(start, end, link)
            }
        }

        return null
    }
}
