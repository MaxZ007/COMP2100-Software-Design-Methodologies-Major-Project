package com.example.cars4sale.Parser;

import org.w3c.dom.NodeList;

import java.util.Map;

import static com.example.cars4sale.DataStructure.SearchUtils.getName;

/**
 * ExpName: it is extended from the abstract class Exp.
 * This class is used to represent the expression of string name (car name / location name).
 *
 * @author: Yuxuan Lin
 * @UID: u6828533
 */
public class ExpName extends Exp {

    private final String name;

    public ExpName(String name) {
        this.name = name;
    }

    @Override
    public Map evaluate(Map map, NodeList nodeList) {
        return getName(map, name);
    }
}
