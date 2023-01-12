/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shubham.automation.utils;


//import javax.xml.parsers.SAXParser;

/**
 *
 * @author Shubham Aggarwal
 */
public class XmlParser {
    
    String xmlDirPath = "./src/test/resources/AA-Artifacts/questions-xmls";
    String xmlPath;
    
    public XmlParser(String xmlName){
        this.xmlPath = this.xmlDirPath + xmlName;
    }
    
    
    
    
}
