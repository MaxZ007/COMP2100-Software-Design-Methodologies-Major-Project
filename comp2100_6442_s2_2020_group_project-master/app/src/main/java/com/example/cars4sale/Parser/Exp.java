package com.example.cars4sale.Parser;

import org.w3c.dom.NodeList;

import java.util.Map;

/**
 * Abstract class Exp to represent expressions
 *
 * @author: Yuxuan Lin
 * @UID: u6828533
 */
public abstract class Exp {

    /**
     * Takes a Map and a NodeList and returns the evaluated map of search results.
     *
     * @param map      The Map of all the car details.
     * @param nodeList The NodeList of the data source document getting elements by tag name "car".
     * @return map The evaluated map of search results.
     */
    public abstract Map evaluate(Map map, NodeList nodeList);

}
