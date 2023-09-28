# fj-tool-helper

Simple project with utilities for stand alone tool.

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](CHANGELOG.md) 
[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/fj-tool-helper.svg)](https://mvnrepository.com/artifact/org.fugerit.java/fj-tool-helper) 
[![license](https://img.shields.io/badge/License-Apache%20License%202.0-teal.svg)](https://opensource.org/licenses/Apache-2.0) 
[![code of conduct](https://img.shields.io/badge/conduct-Contributor%20Covenant-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_fj-tool-helper&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit-org_fj-tool-helper)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_fj-tool-helper&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit-org_fj-tool-helper)

## Quickstart

build : 

`mvn clean install -P singlepackage`

run : 

``` 
Example : java -jar target/dist-fj-tool-helper-*.jar \
 --tool-help-config-path src/main/resources/tool/tool-help-config.xml \
 --output-file target/help.md \
 --output-format md  
```
 
## **parameter help**  

| **name** | **required** | **default** | **description** | **since** | **info**  |
|---------------|---------------|---------------|---------------|---------------|---------------|
| `tool-help-config-path` | `true` | none | Path to tool-help-config xml file. | 0.1.0 | By default the path will be looked on file system, to search in classpath prefix with cl://  |
| `output-file` | `true` | none | Path to the the file to create. | 0.1.0 | Must be a physical file  |
| `output-format` | `true` | none | Output format, currently accepted formats are md (markdown), html, pdf, txt. | 0.1.0 | The output will be generated via Venus Fugerit document generation framework (https://github.com/fugerit-org/fj-doc).  |
| `exclude-info` | `false` | `false` | If set to `true`, the 'info' columns of tool parameters will be ignored. | 0.1.0 | The output will be generated via Venus Fugerit document generation framework (https://github.com/fugerit-org/fj-doc).  |
| `help` | `false` | none | Print help about the tool. | 0.1.0 | In case of errors the help will be printed too.  |
