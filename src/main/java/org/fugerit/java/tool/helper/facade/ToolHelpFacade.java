package org.fugerit.java.tool.helper.facade;

import java.io.OutputStream;

import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.process.DocProcessContext;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;
import org.fugerit.java.tool.helper.config.ToolHelpConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToolHelpFacade {

	private ToolHelpFacade() {}
	
	private static final String CHAIN_ID_TOOL_HELP = "tool_help";
	
	private static final String ATT_CONFIG = "config";
	
	
	// load the configuration or throw a ConfigRuntimeException
	private static final FreemarkerDocProcessConfig FACADE = 
			FreemarkerDocProcessConfigFacade.loadConfigSafe( "cl://fj-doc-tool-helper/freemarker-doc-process.xml" );
	
	public static void generate( String type, ToolHelpConfig config, OutputStream baos ) throws DocException {
		DocTypeHandler handler = FACADE.getFacade().findHandler(type);
		log.info( "type : {}, config : {}", type, config );
		FACADE.getFacade().handlers().forEach( h -> log.info( "handler : {} -> {}", h.getKey(), h ) );
		if ( handler == null ) {
			throw new DocException( "No handler found : "+type );
		}
		// run code or raise DocException
		DocException.apply( () -> 
			FACADE.fullProcess( CHAIN_ID_TOOL_HELP, DocProcessContext.newContext( ATT_CONFIG, config ), handler, DocOutput.newOutput(baos) )	
		);
	}
	
}
