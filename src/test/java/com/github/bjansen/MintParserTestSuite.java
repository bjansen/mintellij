package com.github.bjansen;

import com.intellij.openapi.util.io.FileUtil;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

		FileUtil.collectMatchedFiles(
				new File(mintHome),
				Pattern.compile(".*\\.mint"),
				mintFiles
		);

		for (File mintFile : mintFiles) {
			suite.addTest(new ParsingTestCase(mintFile));
		}

		return suite;
	}
}
