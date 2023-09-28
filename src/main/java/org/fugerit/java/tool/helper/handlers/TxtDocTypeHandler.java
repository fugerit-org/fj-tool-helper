package org.fugerit.java.tool.helper.handlers;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.doc.base.config.DocException;
import org.fugerit.java.doc.base.config.DocInput;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandlerDefault;
import org.fugerit.java.doc.base.helper.DocTypeFacadeDefault;
import org.fugerit.java.doc.base.helper.DocTypeFacadeHelper;
import org.fugerit.java.doc.base.model.DocBase;
import org.fugerit.java.doc.base.model.DocCell;
import org.fugerit.java.doc.base.model.DocContainer;
import org.fugerit.java.doc.base.model.DocElement;
import org.fugerit.java.doc.base.model.DocLi;
import org.fugerit.java.doc.base.model.DocList;
import org.fugerit.java.doc.base.model.DocPara;
import org.fugerit.java.doc.base.model.DocPhrase;
import org.fugerit.java.doc.base.model.DocRow;
import org.fugerit.java.doc.base.model.DocTable;
import org.fugerit.java.doc.base.model.util.DocTableUtil;

import lombok.extern.slf4j.Slf4j;


public class TxtDocTypeHandler extends DocTypeHandlerDefault {

	public static final String TYPE = "txt";
	
	public static final String MODULE = "tool";
	
	public static final String MIME = "text/plain";
	
	public TxtDocTypeHandler() {
		super(TYPE, MODULE, MIME, StandardCharsets.UTF_8);
	}

	private static final long serialVersionUID = -7025738320588016219L;

	@Override
	public void handle(DocInput docInput, DocOutput docOutput) throws Exception {
		PrintWriter writer = new PrintWriter( new OutputStreamWriter( docOutput.getOs(), this.getCharset() ) );
		DocBase docBase = docInput.getDoc();
		TxtFacade facade = new TxtFacade( writer );
		facade.handleDoc( docBase );
	}
	
}

@Slf4j
class TxtFacade extends DocTypeFacadeDefault {

	private static final long serialVersionUID = 5027188017450419796L;
	
	private transient PrintWriter writer;

	protected PrintWriter getWriter() {
		return writer;
	}
	
	public TxtFacade(PrintWriter writer) {
		super();
		this.writer = writer;
	}

	@Override
	public void handleDoc(DocBase docBase) throws DocException {
		// actual document handling :we will treat only the body
		DocTypeFacadeHelper helper = new DocTypeFacadeHelper( docBase );
		this.handleElements( docBase.getDocBody(), helper );
		// flush content
		this.getWriter().flush();	
	}

	private void handleText( String text, int textStyle) throws DocException {
		DocException.apply( () -> {
			log.trace( "style ignored : {}", textStyle );
			this.writer.print( text );
		} );
	}
	
	@Override
	public void handlePara(DocPara docPara, DocContainer parent, DocTypeFacadeHelper helper) throws DocException {
		boolean body = ( helper.getDepth() == DocTypeFacadeHelper.ROOT_DEPTH );
		int headLevel = docPara.getHeadLevel();
		if ( headLevel > 0 ) {
			this.getWriter().println();
		}
		while ( headLevel>0 ) {
			this.writer.print( "#" );
			headLevel--;
		}
		if ( docPara.getHeadLevel() > 0 ) {
			this.writer.print( " " );
		}
		// test
		this.handleText(docPara.getText(), docPara.getStyle() );
		if ( body ) {
			this.writer.println( "  " );	// endline with two white spaces	
		} else {
			this.writer.print( " " );
		}
	}

	@Override
	public void handlePhrase(DocPhrase docPhrase, DocContainer parent, DocTypeFacadeHelper helper) throws DocException {
		this.handleText(docPhrase.getText(), docPhrase.getStyle() );
		this.writer.print( " " );
	}

	@Override
	public void handleList(DocList docList, DocContainer parent, DocTypeFacadeHelper helper) throws DocException {
		this.getWriter().println();
		for ( DocElement liEl : docList.getElementList() ) {
			if ( liEl instanceof DocLi ) {
				DocLi li = (DocLi) liEl;
				this.getWriter().print( "* " );
				this.handleElements( li, helper );
				this.getWriter().println();
			}
		}
	}

	@Override
	public void handleTable(DocTable docTable, DocContainer parent, DocTypeFacadeHelper helper) throws DocException {
		this.getWriter().println();
		this.handleDocUtilTable( docTable, parent, helper );
	}

	protected void handleDocUtilTable( DocTable table, DocContainer parent, DocTypeFacadeHelper helper ) throws DocException {
		DocTableUtil tableUtil = new DocTableUtil( table );
		handleRowList( table, tableUtil, tableUtil.getDataRows() );
		log.trace( "parent : {}, helper : {}", parent, helper );
	}

	private String getText( DocElement docCell ) {
		return((DocPara) ((DocCell)docCell).getElementList().get( 0 )).getText();
	}
	
	protected void handleRowList( DocTable table, DocTableUtil tableUtil, List<DocElement> rowList  ) throws DocException {
		for ( DocElement element: rowList ) {
			List<DocElement> row = ((DocRow) element).getElementList();
			String name = getText( row.get( 0 ) ).replace("`", "");
			String required = getText( row.get( 1 ) );
			String description = getText( row.get( 3 ) );
			String defaultValue = getText( row.get( 2 ) );
			String line = "\t"+ArgUtils.ARG_PREFIX+name+" <arg> "+description;
			if ( BooleanUtils.isTrue( required ) ) {
				line+= " [required]";
			}
			if ( !"none".equalsIgnoreCase( defaultValue ) && StringUtils.isNotEmpty( defaultValue ) ) {
				line+= " [default:"+defaultValue+"]";
			}
			this.getWriter().println(line);
			this.getWriter().println();
		}
		log.trace( "table : {} , tableUtil : {}", table, tableUtil );
	}
 	
	
}
