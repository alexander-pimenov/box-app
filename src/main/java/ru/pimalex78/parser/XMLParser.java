package ru.pimalex78.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.pimalex78.entities.Box;
import ru.pimalex78.entities.Item;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    private final List<Box> boxes = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    //final String fileName = "C:/projects/box-app/src/main/resources/Storage.xml";


    public List<Box> getBoxes() {
        return boxes;
    }

    public List<Item> getItems() {
        return items;
    }

    public void parseXML(String fileName) {


        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean check = false;
                private String thisElement;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    thisElement = qName;
                    check = true;
                    System.out.println(thisElement);
                    if (qName.equalsIgnoreCase("box")) {
                        Integer idBox = Integer.parseInt(attributes.getValue("id"));

                        boxes.add(new Box(idBox));
                        System.out.println("\tidBox: " + idBox);

                    }
                    if (qName.equalsIgnoreCase("item")) {
                        Integer idItem = Integer.parseInt(attributes.getValue("id"));
                        String colorItem = attributes.getValue("color");

                        items.add(new Item(idItem, colorItem));
                        System.out.println("\tidItem: " + idItem);
                        System.out.println("\tcolorItem: " + colorItem);
                    }
                }

                /*Можем использовать этот метод, если между тегами будет текст.*/
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (check) {
                        //System.out.println("Text: " + new String(ch, start, length));
                        //boxes.add(new String(ch, start, length));
                        //items.add(new String(ch, start, length));
                        check = false;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    System.out.println("/" + thisElement);
                    //очистим элемент
                    thisElement = "";
                }
            };

            //Парсим наш XML-файл
            saxParser.parse(fileName, handler);

            //Для просмотра списков полученных boxes и items
            // boxes.forEach(System.out::println);
            //items.forEach(System.out::println);

        } catch (
                Exception ex) {
            ex.printStackTrace();
        }

    }
}