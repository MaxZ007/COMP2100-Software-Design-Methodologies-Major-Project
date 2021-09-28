package com.example.cars4sale.Parser;

import org.w3c.dom.NodeList;

import java.util.Map;

import static com.example.cars4sale.DataStructure.SearchUtils.getYear;

/**
 * ExpYear: it is extended from the abstract class Exp.
 * This class is used to represent the expression of integer year.
 *
 * @author: Yuxuan Lin
 * @UID: u6828533
 */
public class ExpYear extends Exp {

    private final int year;

    public ExpYear(int year) {
        this.year = year;
    }

    @Override
    public Map evaluate(Map map, NodeList nodeList) {
        return getYear(map, year);
    }
}
