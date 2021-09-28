package com.example.cars4sale.Parser;

import com.example.cars4sale.Tokenizer.MyTokenizer;
import com.example.cars4sale.Tokenizer.Token;

/**
 * The main objective of this class is to implement a parser for the search query.
 * It should be able to parse the following context-free grammar rule:
 * <exp>        ::=   <term> | <term> ; <exp>
 * <term>       ::=   <keyword> <comparison> <value>
 * <value>      ::=   <unsigned string> | <unsigned integer>
 * <keyword>    ::=   "name" | "location" | "price" | "year"
 * <comparison> ::=   "=" | "<" | ">"
 *
 * @author: Yuxuan Lin
 * @UID: u6828533
 */
public class Parser {

    MyTokenizer _tokenizer;

    public Parser(MyTokenizer _tokenizer) {
        this._tokenizer = _tokenizer;
    }

    /**
     * Parse the queries with the form of
     * <exp> ::= <term> | <term> ; <exp>
     */
    public Exp parseExp() {
        if (_tokenizer.current().type().equals(Token.Type.KEYWORD)) {
            Exp term = parseKeyword();
            if (_tokenizer.hasNext()) {
                _tokenizer.next();
                if (_tokenizer.hasNext() && _tokenizer.current().type().equals(Token.Type.SEMICOLON)) {
                    _tokenizer.next();
                    Exp exp = parseExp();
                    return new ExpAnd(term, exp);
                }
            }
            return term;
        } else throw new IllegalArgumentException();
    }

    /**
     * Parse the queries with the form of
     * <keyword> ::= "name" | "location" | "price" | "year"
     */
    public Exp parseKeyword() {
        if (_tokenizer.hasNext()) {
            Exp term = null;
            if (_tokenizer.current().token().equalsIgnoreCase("name")) {
                term = parseName();
            } else if (_tokenizer.current().token().equalsIgnoreCase("location")) {
                term = parseLocation();
            } else if (_tokenizer.current().token().equalsIgnoreCase("price")) {
                term = parsePrice();
            } else if (_tokenizer.current().token().equalsIgnoreCase("year")) {
                term = parseYear();
            }
            return term;
        } else throw new IllegalArgumentException();
    }

    /**
     * Parse the queries with the form of
     * <value> ::= <unsigned string>
     */
    public Exp parseName() {
        if (_tokenizer.hasNext()) {
            _tokenizer.next();
            if (_tokenizer.current().type().equals(Token.Type.COMPARISON)) {
                if (_tokenizer.current().token().equals("=")) {
                    _tokenizer.next();
                    if (!_tokenizer.current().type().equals(Token.Type.NAME)) {
                        throw new IllegalArgumentException();
                    }
                    String name = _tokenizer.current().token();
                    return new ExpName(name);
                }
            }
        } else throw new IllegalArgumentException();
        return null;
    }

    /**
     * Implement getLocation.
     */
    public Exp parseLocation() {
        if (_tokenizer.hasNext()) {
            _tokenizer.next();
            if (_tokenizer.current().type().equals(Token.Type.COMPARISON)) {
                if (_tokenizer.current().token().equals("=")) {
                    _tokenizer.next();
                    if (!_tokenizer.current().type().equals(Token.Type.NAME)) {
                        throw new IllegalArgumentException();
                    }
                    String location = _tokenizer.current().token();
                    return new ExpLocation(location);
                }
            }
        } else throw new IllegalArgumentException();
        return null;
    }

    /**
     * Implement getHigherPrice and getLowerPrice.
     */
    public Exp parsePrice() {
        if (_tokenizer.hasNext()) {
            _tokenizer.next();
            if (_tokenizer.current().type().equals(Token.Type.COMPARISON)) {
                switch (_tokenizer.current().token()) {
                    case "=":
                        throw new IllegalArgumentException();
                    case "<": {
                        _tokenizer.next();
                        if (!_tokenizer.current().type().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int price = Integer.parseInt(_tokenizer.current().token());
                        return new ExpLowerPrice(price);
                    }
                    case ">": {
                        _tokenizer.next();
                        if (!_tokenizer.current().type().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int price = Integer.parseInt(_tokenizer.current().token());
                        return new ExpHigherPrice(price);
                    }
                }
            }
        } else throw new IllegalArgumentException();
        return null;
    }

    /**
     * Implement getYearBefore, getYearAfter and getYear.
     */
    public Exp parseYear() {
        if (_tokenizer.hasNext()) {
            _tokenizer.next();
            if (_tokenizer.current().type().equals(Token.Type.COMPARISON)) {
                switch (_tokenizer.current().token()) {
                    case "=": {
                        _tokenizer.next();
                        if (!_tokenizer.current().type().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int year = Integer.parseInt(_tokenizer.current().token());
                        return new ExpYear(year);
                    }
                    case "<": {
                        _tokenizer.next();
                        if (!_tokenizer.current().type().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int year = Integer.parseInt(_tokenizer.current().token());
                        return new ExpYearBefore(year);
                    }
                    case ">": {
                        _tokenizer.next();
                        if (!_tokenizer.current().type().equals(Token.Type.INT)) {
                            throw new IllegalArgumentException();
                        }
                        int year = Integer.parseInt(_tokenizer.current().token());
                        return new ExpYearAfter(year);
                    }
                }
            }
        } else throw new IllegalArgumentException();
        return null;
    }

}
