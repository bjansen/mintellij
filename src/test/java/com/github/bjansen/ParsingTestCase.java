package com.github.bjansen;

import com.github.bjansen.mintellij.MintLexer;
import com.github.bjansen.mintellij.MintParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import junit.framework.TestCase;
import org.antlr.intellij.adaptor.parser.SyntaxError;
import org.antlr.intellij.adaptor.parser.SyntaxErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Ignore;

@Ignore
public class ParsingTestCase extends TestCase {

	private final File mintFile;

	public ParsingTestCase(File mintFile, String name) {
		super(name);
		this.mintFile = mintFile;
	}

	@Override
	protected void runTest() throws Throwable {
		final List<SyntaxError> syntaxErrors = parseFile(mintFile);

		String message = "File has syntax errors: \n";

		for (SyntaxError syntaxError : syntaxErrors) {
			message += String.format(
					"* %d:%d %s\n",
					syntaxError.getLine(),
					syntaxError.getCharPositionInLine(),
					syntaxError.getMessage()
			);
		}
		assertEquals(
				message,
				0,
				syntaxErrors.size()
		);
	}

	private List<SyntaxError> parseFile(File mintFile) throws IOException {
		final CharStream charStream = CharStreams.fromFileName(mintFile.getAbsolutePath());
		final MintLexer lexer = new MintLexer(charStream);
		final MintParser parser = new MintParser(new CommonTokenStream(lexer));

		final SyntaxErrorListener errorListener = new SyntaxErrorListener();
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);

		parser.topLevel();

		return errorListener.getSyntaxErrors();
	}
}
