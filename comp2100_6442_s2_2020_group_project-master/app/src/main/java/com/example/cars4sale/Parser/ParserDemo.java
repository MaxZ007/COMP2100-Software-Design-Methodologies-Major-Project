package com.example.cars4sale.Parser;

import com.example.cars4sale.DataStructure.SearchUtils;
import com.example.cars4sale.Tokenizer.MyTokenizer;

import java.util.Map;

/**
 * Parser demo for grammar:
 *
 * <exp>        ::=   <term> | <term> ; <exp>
 * <term>       ::=   <keyword> <comparison> <value>
 * <value>      ::=   <unsigned string> | <unsigned integer>
 * <keyword>    ::=   "name" | "location" | "price" | "year"
 * <comparison> ::=   "=" | "<" | ">"
 */
public class ParserDemo {

    final static String text = "NaemE=tesla; lLoc = perth; peicRE<200000; aYaer>2008";

    public static void main(String[] args) {
        MyTokenizer _tokenizer = new MyTokenizer(text);
        Exp _exp = new Parser(_tokenizer).parseExp();
        Map searchResult = _exp.evaluate(SearchUtils.readData_map(), SearchUtils.readData_sList());
        System.out.println(searchResult);
    }
}
