package org.fugerit.java.tool.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.SafeIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.tool.helper.config.ToolHelpConfig;
import org.fugerit.java.tool.helper.facade.ToolHelpFacade;
import org.fugerit.java.tool.util.ArgHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunTool {
	
	private RunTool() {}

	public static final String ARG_TOOL_HELP_CONFIG_PATH = "tool-help-config-path";
	
	public static final String ARG_OUTPUT_FILE = "output-file";
	
	public static final String ARG_OUTPUT_FORMAT = "output-format";
	
	public static final String ARG_EXCLUDE_INFO = "exclude-info";
	
	public static final String ARG_HELP = "help";
	
	private static void printHelp() {
		log.info( "help : \n\n{}", SafeIO.readStringStream( () -> ClassHelper.loadFromDefaultClassLoader( "tool/help.txt" ) ) );
	}
	
	public static void handle( Properties params ) {
		log.debug( "params : {}", params );
		String help = params.getProperty( ARG_HELP );
		if ( help != null || params.size() == 0 ) {
			printHelp();
		} else {
			SafeFunction.apply( () -> {
				ArgHelper.checkAllRequiredThrowRuntimeEx(params, ARG_TOOL_HELP_CONFIG_PATH, ARG_OUTPUT_FILE, ARG_OUTPUT_FORMAT);
				String toolHelpConfigPath = params.getProperty( ARG_TOOL_HELP_CONFIG_PATH );
				String outputFile = params.getProperty( ARG_OUTPUT_FILE );
				String outputFormat = params.getProperty( ARG_OUTPUT_FORMAT );
				String excludeInfo = params.getProperty( ARG_EXCLUDE_INFO );
				log.debug( "param exclude-info not yet supported : {}", excludeInfo );
				File file = new File( outputFile );
				log.info( "{} : {}", ARG_TOOL_HELP_CONFIG_PATH, toolHelpConfigPath );
				ToolHelpConfig config = ToolHelpConfig.loadSafe( toolHelpConfigPath );
				log.info( "output : {}, format : {}", file.getCanonicalPath(), outputFormat );
				try (FileOutputStream fos = new FileOutputStream( file )) {
					ToolHelpFacade.generate( outputFormat, config, fos );
				}
			}, e -> {
				log.info( "Error : "+e, e );
				printHelp();
			});
		}
	}
	
	public static void main( String[] args ) {
		handle( ArgUtils.getArgs( args ) );
	}
	
}
