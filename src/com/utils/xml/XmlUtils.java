package com.utils.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtils {
	 //定义解析器和文档对象
     private static SAXReader reader;

	 public static void handleXML(File xmlfile,Map<String,String> map){
		 try {
				if(reader == null) {
					reader = new SAXReader();
				}
		        Document doc = reader.read(xmlfile);
		        Element root = doc.getRootElement();
		        List<Element> rootList = root.elements();
		        for (Element ele : rootList) {
		            String eleName = ele.getName();
		            if (eleName.equals("package")) {
		            	 String namespace = getAttribute(ele, "namespace");
		            	 List<Element> eleList = ele.elements();
		            	 for (Element e : eleList) {
		            		  String name = e.getName();
		      	              if (name.equals("action")) {
		      	            		String actionClass = getAttribute(e, "class");
		      	            		String actionName = getAttribute(e, "name");
		      	            		map.put(actionClass, namespace+"/"+actionName);
		      	              }
						}
		            }
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 public static String getAttribute(Element ele, String attribute) {
	        List attrList = ele.attributes();
	        for (int i = 0; i < attrList.size(); i++) {
	            Attribute item = (Attribute) attrList.get(i);
	            if (item.getName().equals(attribute)) {
	                return item.getValue();
	            }
	        }
	        return "";
	    }
}
