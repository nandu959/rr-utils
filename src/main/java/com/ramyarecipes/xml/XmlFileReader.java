package com.ramyarecipes.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ramyarecipes.entity.Attachment;
import com.ramyarecipes.entity.Post;

public class XmlFileReader {

	static Map<String, List<Attachment>> attachments = new HashMap<String, List<Attachment>>();

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

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String type = eElement.getElementsByTagName("wppost_type").item(0).getTextContent();
					if("attachment".equals(type)) {

						Attachment attachment = new Attachment();

						attachment.setId(eElement.getElementsByTagName("wppost_id").item(0).getTextContent());
						attachment.setLink(eElement.getElementsByTagName("wpattachment_url").item(0).getTextContent());
						attachment.setPid(eElement.getElementsByTagName("wppost_parent").item(0).getTextContent());

						List<Attachment> ats = attachments.get(attachment.getPid());
						if(ats==null) {
							ats = new ArrayList<Attachment>();
							ats.add(attachment);
							attachments.put(attachment.getPid(), ats);
						}
						else {
							ats.add(attachment);
							attachments.put(attachment.getPid(), ats);
						}
					}
				}
			}

			//System.out.println(attachments);
			//			//nList.getLength()

			List<Post> posts = new ArrayList<Post>();
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String type = eElement.getElementsByTagName("wppost_type").item(0).getTextContent();
					if("post".equals(type)){

						Post post = new Post();
						post.setComments(extractComments(eElement).size()+"");
						post.setContent(eElement.getElementsByTagName("contentencoded").item(0).getTextContent());
						post.setId(eElement.getElementsByTagName("wppost_id").item(0).getTextContent());
						post.setAttachments(attachments.get(post.getId()));
						post.setLink( eElement.getElementsByTagName("link").item(0).getTextContent());						
						post.setThumbnail(getThumbailNailById(post.getId(),eElement));						
						post.setTimestamp(eElement.getElementsByTagName("pubDate").item(0).getTextContent());						
						post.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
						posts.add(post);

						processContents(post.getContent());

						//						System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
						//						System.out.println("Link : " + eElement.getElementsByTagName("link").item(0).getTextContent());
						//						System.out.println("Date : " + eElement.getElementsByTagName("pubDate").item(0).getTextContent());
						//System.out.println("Content : " + eElement.getElementsByTagName("contentencoded").item(0).getTextContent());
						//						System.out.println("Id : " + eElement.getElementsByTagName("wppost_id").item(0).getTextContent());
						//
						//						System.out.println("Type : " + eElement.getElementsByTagName("wppost_type").item(0).getTextContent());
						//						System.out.println("Id : " + eElement.getElementsByTagName("wppost_id").item(0).getTextContent());	
						//
						//						System.out.println("Thumbnail Id : "+ getThumbailNailById(eElement));
						//						
						//						System.out.println("Comments : "+ extractComments(eElement));
					}
				}
			}
			//System.out.println(posts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getThumbailNailById(String pid,Element element) {
		NodeList nList = element.getElementsByTagName("wppostmeta");
		String tn="";
		for(int i=0;i < nList.getLength();i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;				
				if("_thumbnail_id".equals(eElement.getElementsByTagName("wpmeta_key").item(0).getTextContent())) {
					String tid = eElement.getElementsByTagName("wpmeta_value").item(0).getTextContent();
					List<Attachment> atcmtsList =  attachments.get(pid)!=null?attachments.get(pid):new ArrayList<Attachment>();

					for (Attachment atchmt :atcmtsList) {
						if(tid.equals(atchmt.getId())) {
							tn = atchmt.getLink();
						}
					}

				}
			}
		}
		return tn;
	}

	private List<String> extractTags(Node node) {
		return null;
	}
	String pattern = "<(img)\b[^>]*>";

	private static String processContents(String contents) {
		StringBuilder processedContents=new StringBuilder();
		String[] lines = contents.split("\n");
		
		for (String line : lines) {
			
			if(line.contains("<img")) {
				Pattern p = Pattern.compile("src=\"(.*?)\"");
				Matcher m = p.matcher(line);
				String src = "";
				if (m.find()) {
					src = m.group(1);
					//System.out.println(src);
				}
				String replacement="<img src=\""+src +"\"/>";
				
				StringBuilder sb = new StringBuilder();
				sb.append(line.replaceAll(line.substring(line.indexOf("<img"), line.indexOf("/>")+2), replacement));

				//System.out.println(sb);
			}
			
			
			else if(line.contains("[gallery")) {
				String x = line.replace("[gallery ids=\"", "");
				String y = x.substring(0, x.indexOf("\" t"));
				System.out.println(y);
			}
			
			processedContents.append(line);
		}

		return processedContents.toString();
	}

	public static List<String> extractComments(Element element) {
		NodeList nList = element.getElementsByTagName("wpcomment");
		List<String> comments = new ArrayList<String>();
		for(int i=0;i < nList.getLength();i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;				
				if(!"pingback".equals(eElement.getElementsByTagName("wpcomment_type").item(0).getTextContent())) {
					String comment = eElement.getElementsByTagName("wpcomment_content").item(0).getTextContent();
					comments.add(comment);
				}
			}
		}
		return comments;
	}

	private List<Attachment> extractAttachments(Node node,String pid) {
		return null;
	}



}
