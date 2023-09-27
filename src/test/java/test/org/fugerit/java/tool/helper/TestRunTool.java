package test.org.fugerit.java.tool.helper;

import java.io.File;
import java.io.IOException;

import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.tool.helper.RunTool;
import org.fugerit.java.tool.helper.handlers.TxtDocTypeHandler;
import org.junit.Assert;
import org.junit.Test;

public class TestRunTool {

	@Test
	public void testSelf() throws IOException {
		File outputFile = new File("target/help.txt");
		String[] args = {
				ArgUtils.getArgString( RunTool.ARG_TOOL_HELP_CONFIG_PATH ), "cl://tool/tool-help-config.xml",
				ArgUtils.getArgString( RunTool.ARG_OUTPUT_FILE ), outputFile.getCanonicalPath(),
				ArgUtils.getArgString( RunTool.ARG_OUTPUT_FORMAT ), TxtDocTypeHandler.TYPE,
		};
		RunTool.main(args);
		Assert.assertTrue( outputFile.exists() );
	}
	
	@Test
	public void testKo1() throws IOException {
		File outputFile = new File("target/none1.txt");
		String[] args = {
				ArgUtils.getArgString( RunTool.ARG_TOOL_HELP_CONFIG_PATH ), "not-exists.xml"
		};
		RunTool.main(args);
		Assert.assertFalse( outputFile.exists() );
	}
	
	@Test
	public void testHelp() throws IOException {
		File outputFile = new File("target/none2.txt");
		String[] args = {
				ArgUtils.getArgString( RunTool.ARG_HELP )
		};
		RunTool.main(args);
		RunTool.main(new String[0]);
		Assert.assertFalse( outputFile.exists() );
	}
	
}
