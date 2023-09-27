package org.fugerit.java.tool.helper.handlers;

import java.nio.charset.StandardCharsets;

import org.fugerit.java.doc.base.typehandler.markdown.SimpleMarkdownExtTypeHandler;

public class MarkdownDocTypeHandler extends SimpleMarkdownExtTypeHandler {

	public MarkdownDocTypeHandler() {
		super(StandardCharsets.UTF_8, false);
	}

	private static final long serialVersionUID = 2926754297274075646L;

}
