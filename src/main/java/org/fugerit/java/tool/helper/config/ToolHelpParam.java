package org.fugerit.java.tool.helper.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ToolHelpParam {

	@Getter @Setter private String id;
	
	@Getter @Setter private String required;
	
	@Getter @Setter private String defaultValue;
	
	@Getter @Setter private String description;
	
	@Getter @Setter private String info;
	
	@Getter @Setter private String since;
	
}
