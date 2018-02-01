package com.ramyarecipes.xml;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ramyarecipes.entity.Attachment;

public class XmlFileReader {

	public static void fileToString(String filename) throws IOException {
		try {

			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("item");

			System.out.println("----------------------------"+nList.getLength());
			//nList.getLength()
			for (int temp = 0; temp < 100; temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;


					String type = eElement.getElementsByTagName("wppost_type").item(0).getTextContent();
					if("post".equals(type)){

						System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
						System.out.println("Link : " + eElement.getElementsByTagName("link").item(0).getTextContent());
						System.out.println("Date : " + eElement.getElementsByTagName("pubDate").item(0).getTextContent());
						System.out.println("Content : " + eElement.getElementsByTagName("contentencoded").item(0).getTextContent());
						System.out.println("Id : " + eElement.getElementsByTagName("wppost_id").item(0).getTextContent());

						System.out.println("Type : " + eElement.getElementsByTagName("wppost_type").item(0).getTextContent());
						System.out.println("Id : " + eElement.getElementsByTagName("wppost_id").item(0).getTextContent());	
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<String> extractTags(Node node) {
		return null;
	}
	
	private List<String> extractComments(Node node) {
		return null;
	}
	
	private List<Attachment> extractAttachments(Node node,String pid) {
		return null;
	}
 


}
