<?xml version="1.0" encoding="utf-8"?>
<doc
	xmlns="http://javacoredoc.fugerit.org"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://javacoredoc.fugerit.org https://www.fugerit.org/data/java/doc/xsd/doc-2-1.xsd" > 

  <!--
  	This is a Venus Fugerit Doc (https://github.com/fugerit-org/fj-doc) XML Source Document.
  	For documentation on how to write a valid Venus Doc XML Meta Model refer to : 
  	https://venusguides.fugerit.org/src/docs/common/doc_format_summary.html
  -->

  <metadata>
	<!-- Margin for document : left;right;top;bottom -->
	<info name="margins">10;10;10;30</info>  
	<!-- documenta meta information -->
	<info name="doc-title">Tool help</info>
	<info name="doc-author">fugerit79</info>
	<info name="doc-language">en</info>
  </metadata>
  
  <body>
	
	<h size="16" head-level="1" space-after="10" style="bold">${config.toolName}</h>
	
	<para space-after="10">${config.toolDescription}</para>
	
	<para space-after="10">Example : ${config.commandExample}</para>
	
	<h size="14" head-level="2" space-after="10" style="bold">parameter help</h>
	
	<table columns="6" colwidths="15;10;15;25;10;25" width="100">
		<row header="true">
			<cell align="center"><para style="bold">name</para></cell>
			<cell align="center"><para style="bold">required</para></cell>
			<cell align="center"><para style="bold">default</para></cell>
			<cell align="center"><para style="bold">description</para></cell>
			<cell align="center"><para style="bold">since</para></cell>
			<cell align="center"><para style="bold">info</para></cell>
		</row>
		<#list config.params as current>
		<row>
			<cell><para>${current.id}</para></cell>
			<cell><para>${current.required}</para></cell>
			<cell><para>${current.defaultValue}</para></cell>
			<cell><para>${current.description}</para></cell>
			<cell><para>${current.since}</para></cell>
			<cell><para>${current.info}</para></cell>
		</row>
		</#list>
	</table>

  </body>

</doc>
