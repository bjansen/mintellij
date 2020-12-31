package com.github.bjansen;

import com.intellij.openapi.util.io.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses({MintParserTestSuite.MintCore.class, MintParserTestSuite.OurMintSamples.class})
public class MintParserTestSuite {

	public static class MintCore {
		public static Test suite() {
			// TODO externalize
			final String mintHome = "/Users/bastien/Dev/mint/core";
			return buildSuiteForAllFilesIn(mintHome);
		}
	}

	public static class OurMintSamples {
		public static Test suite() {
			final String ourSamples = "src/test/resources/ParserTests/source";
			return buildSuiteForAllFilesIn(ourSamples);
		}
	}

	@NotNull
	private static Test buildSuiteForAllFilesIn(String mintHome) {
		final TestSuite suite = new TestSuite();

		final ArrayList<File> mintFiles = new ArrayList<>();

		File mintHomeDir = new File(mintHome);

		FileUtil.collectMatchedFiles(
			mintHomeDir,
				Pattern.compile(".*\\.mint"),
				mintFiles
		);

		for (File mintFile : mintFiles) {
			String name = FileUtil.getRelativePath(mintHomeDir, mintFile);
			suite.addTest(new ParsingTestCase(mintFile, name));
		}

		return suite;
	}
}
