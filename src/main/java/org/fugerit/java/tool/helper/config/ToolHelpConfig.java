package org.fugerit.java.tool.helper.config;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.xml.XmlBeanHelper;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToolHelpConfig {

	@Getter private String toolName;
	
	@Getter private String toolDescription;
	
	@Getter private String commandExample;
	
	@Getter private List<ToolHelpParam> params;
	
	private ToolHelpConfig() {
		this.params = new ArrayList<>();
	}
	
	public static ToolHelpConfig loadSafe( String path ) {
		ToolHelpConfig config = new ToolHelpConfig();
		SafeFunction.apply( () -> {
			try ( InputStreamReader reader = new InputStreamReader( StreamHelper.resolveStream( path ) ) ) {
				Document doc = DOMIO.loadDOMDoc( reader );
				Element root = doc.getDocumentElement();
				config.toolName = getOnlyRequired( root , "toolName" ).getTextContent();
				config.toolDescription = getOnlyRequired( root , "toolDescription" ).getTextContent();
				config.commandExample = getOnlyRequired( root , "commandExample" ).getTextContent();
				NodeList paramTags = root.getElementsByTagName( "param" );
				for ( int k=0; k<paramTags.getLength(); k++ ) {
					Element currentTag = (Element)paramTags.item(k);
					ToolHelpParam param = new ToolHelpParam();
					boolean allSet = XmlBeanHelper.setFromElementSafe( param , currentTag, XmlBeanHelper.MODE_XML_ELEMENTS );
					if ( !allSet ) {
						throw new ConfigRuntimeException( "Issue configuring param : "+param );
					}
					log.info( "current param {}", param );
					config.params.add(param);
				}
			}
		} );
		
		return config;
	}
	
	private static Element getOnlyRequired( Element tag, String tagName ) {
		NodeList list = tag.getElementsByTagName( tagName );
		int len = list.getLength();
		log.info( "tagName : {}, found : {}", tagName, len );
		if ( len == 0 || len > 1 ) {
			throw new ConfigRuntimeException( tagName+" is a required parameter and must be unique (size:"+len );
		}
		return (Element)list.item( 0 );
	}
	
}
