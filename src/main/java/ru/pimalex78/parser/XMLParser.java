package ru.pimalex78.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.pimalex78.entities.Box;
import ru.pimalex78.entities.Item;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XMLParser {

    private final List<Box> boxes = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();


    public List<Box> getBoxes() {
        return boxes;
    }

    public List<Item> getItems() {
        return items;
    }

    public void parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File(fileName);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        LinkedList<Node> els = new LinkedList<>(children(doc.getDocumentElement()));
        while (!els.isEmpty()) {
            Node node = els.poll();
            Element e = (Element) node;
            //int parentId = -1; //при таком значении в таблицу Box попадает -1, а из таблицы Item
            //на него есть ссылка, но он не может быть равен -1, поэтому поставил null.
            Integer parentId = null;
            if (node.getParentNode() != null) {
                Element parent = (Element) node.getParentNode();
                if (parent.hasAttribute("id")) {
                    parentId = Integer.parseInt(parent.getAttribute("id"));
                }
            }
            if ("Box".equals(node.getNodeName())) {
                boxes.add(new Box(Integer.parseInt(e.getAttribute("id")), parentId));
            } else if ("Item".equals(node.getNodeName())) {
                items.add(new Item(Integer.parseInt(e.getAttribute("id")), parentId, e.getAttribute("color") ));
            }
            els.addAll(children(node));
        }
    }

    private static List<Node> children(Node nodes) {
        LinkedList<Node> els = new LinkedList<>();
        NodeList list = nodes.getChildNodes();
        for (int index = 0; index < list.getLength(); index++) {
            Node node = list.item(index);
            if ("Box".equals(node.getNodeName()) || "Item".equals(node.getNodeName())) {
                els.add(node);
            }
        }
        return els;
    }
}