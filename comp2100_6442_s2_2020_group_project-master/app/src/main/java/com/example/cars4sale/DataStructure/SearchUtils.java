package com.example.cars4sale.DataStructure;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SearchUtils extends BST {

    private static List nodeList;
    private static NodeList listUltra;
    private static Map mapUltra = new HashMap();


    //transform NodeList to List
    public static List return_list(NodeList list) {
        nodeList = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            NodeList childText = node.getChildNodes();
            for (int j = 0; j < childText.getLength(); j++) {
                if (childText.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    nodeList.add(childText.item(j).getFirstChild().getNodeValue());
                }
            }
        }
        return nodeList;
    }


    //return tree
    public static BST node(NodeList list) {
        nodeList = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            NodeList childText = node.getChildNodes();
            for (int j = 0; j < childText.getLength(); j++) {
                if (childText.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    nodeList.add(childText.item(j).getFirstChild().getNodeValue());
                }
            }
        }
        Map map = groupList(nodeList);

        BST tree = new BST();
        for (int i = 0; i < 1000; i++) {
            //key--price; value--id
            tree.insert(
                    Integer.parseInt(map.get(i).toString().split(",")[3].trim()),
                    map.get(i).toString().substring(1).split(",")[0].trim()
            );
        }
        return tree;
    }

    //generate map
    public static Map groupList(List list) {
        int listSize = list.size();
        int toIndex = 5;
        Map map = new HashMap();
        int keyNum = 0;
        //make sure every group has 4
        for (int i = 0; i < list.size(); i += 5) {
            if (i + 4 > listSize) {
                toIndex = listSize - i;
            }
            List newList = list.subList(i, i + toIndex);
            map.put(keyNum, newList);
            keyNum++;
        }
        return map;
    }

    //give a name and a map, then it can return all same name in map
    public static Map getName(Map map, String s) {
        Map new_map = new HashMap();
        for (int i = 0; i < 1000; i++) {
            if (map.get(i).toString().split(",")[1].trim().toLowerCase().contains(s.toLowerCase())) {
                new_map.put(i, map.get(i));
            }
        }
        return new_map;
    }

    //give a location and a map, then it can return all same location in map
    public static Map getLocation(Map map, String s) {
        Map new_map = new HashMap();
        for (int i = 0; i < 1000; i++) {
            if (map.get(i).toString().split(",")[2].trim().toLowerCase().equals(s.toLowerCase())) {
                new_map.put(i, map.get(i));
            }
        }
        return new_map;
    }

    //give a year and a map, then it can return all same year in map
    public static Map getYear(Map map, Integer year) {
        Map new_map = new HashMap();
        for (int i = 0; i < 1000; i++) {
            if (Integer.parseInt(map.get(i).toString().split(",")[4].trim().substring(0, 4)) == year) {
                new_map.put(i, map.get(i));
            }
        }
        return new_map;
    }

    //give a year and a map, then it can return all before this year in map
    public static Map getYearBefore(Map map, Integer year) {
        Map new_map = new HashMap();
        for (int i = 0; i < 1000; i++) {
            if (Integer.parseInt(map.get(i).toString().split(",")[4].trim().substring(0, 4)) < year) {
                new_map.put(i, map.get(i));
            }
        }
        return new_map;
    }

    //give a year and a map, then it can return all after this year in map
    public static Map getYearAfter(Map map, Integer year) {
        Map new_map = new HashMap();
        for (int i = 0; i < 1000; i++) {
            if (Integer.parseInt(map.get(i).toString().split(",")[4].trim().substring(0, 4)) > year) {
                new_map.put(i, map.get(i));
            }
        }
        return new_map;
    }

    public static Map readData_map() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("app/src/main/assets/carData.xml");
            NodeList sList = d.getElementsByTagName("car");
            node(sList);
            System.out.println(sList.getClass());
            mapUltra = groupList(return_list(sList));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapUltra;
    }

    public static NodeList readData_sList() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("app/src/main/assets/carData.xml");
            listUltra = d.getElementsByTagName("car");
            node(listUltra);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUltra;
    }
}