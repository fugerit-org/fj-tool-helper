package test.org.fugerit.java.tool.helper.config;

import org.fugerit.java.tool.helper.config.ToolHelpConfig;
import org.junit.Assert;
import org.junit.Test;

public class TestToolHelpConfig {

	@Test
	public void testSelf() {
		ToolHelpConfig config = ToolHelpConfig.loadSafe( "cl://tool/tool-help-config.xml" );
		Assert.assertNotNull( config );
	}
	
}
