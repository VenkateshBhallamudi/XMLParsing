package xmlparsing;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {

	public static void main(String[] args) {
		try {				
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		File xmlFile = new File("D:/catalog.xml");
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(xmlFile);
		Element rootElement = document.getDocumentElement();
		System.out.println("Root element is: " + rootElement.getTagName());
		visitNode(null, rootElement);
		} catch (SAXException ex) {
			System.out.println(ex.getMessage());
		} catch (ParserConfigurationException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void visitNode(Element previousNode, Element visitNode) {
		if(previousNode != null) {
			System.out.println("Element " + previousNode.getTagName() + " has element: ");
		}
		System.out.println("Element Name: " + visitNode.getTagName());
		if(visitNode.hasAttributes()) {
			System.out.println("Element " + visitNode.getTagName() + " has attributes:");
			NamedNodeMap attributes = visitNode.getAttributes();
			
			for(int i = 0; i < attributes.getLength(); i++) {
				Attr attribute = (Attr) (attributes.item(i));
				System.out.println("Attribute: " + attribute.getName() + " with value " + attribute.getValue());
			}
		}
		
		NodeList nodeList = visitNode.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				visitNode(visitNode, element);
			} else if(node.getNodeType() == Node.TEXT_NODE) {
				String str = node.getNodeValue().trim();
				if(str.length() > 0) {
					System.out.println("Element text: " + str);
				}
			} else if(node.getNodeType() == Node.ATTRIBUTE_NODE) {
				Attr attribute = (Attr) node;
				System.out.println("Element attribute: Name=" + attribute.getName() + " Value=" + attribute.getValue());
			}
		}
	}
}
