package com.example.cars4sale.Parser;

import org.w3c.dom.NodeList;

import java.util.Map;

import static com.example.cars4sale.DataStructure.BST.getHigherPrice;
import static com.example.cars4sale.DataStructure.BST.list_to_list;
import static com.example.cars4sale.DataStructure.BST.list_to_map;
import static com.example.cars4sale.DataStructure.SearchUtils.node;

/**
 * ExpHigherPrice: it is extended from the abstract class Exp.
 * This class is used to represent the expression of getting higher price.
 *
 * @author: Yuxuan Lin
 * @UID: u6828533
 */
public class ExpHigherPrice extends Exp {

    private final int price;

    public ExpHigherPrice(int price) {
        this.price = price;
    }

    @Override
    public Map evaluate(Map map, NodeList nodeList) {
        Map result = list_to_map(
                map,
                list_to_list(
                        getHigherPrice(node(nodeList).root, price)
                )
        );
        return result;
    }
}
