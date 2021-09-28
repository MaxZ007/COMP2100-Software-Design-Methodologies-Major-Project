package com.example.cars4sale.Tokenizer;

import java.util.Arrays;

/**
 * Implement a unique Tokenizer to tokenize the search queries, according to the designated vocabulary.
 * MyTokenizer's pre-treatment functionality can handle most of the partially valid search queries.
 *
 * @author: Yuxuan Lin
 * @UID: u6828533
 */
public class MyTokenizer extends Tokenizer {

    private String _buffer;        //save text
    private Token currentToken;    //save token extracted from next()

    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and save it to currentToken
     */
    public MyTokenizer(String text) {
        _buffer = text;        // save input text (string)
        next();        // extracts the first token.
    }

    /**
     * returned the current token extracted by {@code next()}
     *
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * check whether there still exists another tokens in the buffer or not
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }

    /**
     * This function will find and extract a next token from {@code _buffer} and
     * save the token to {@code currentToken}.
     */
    public void next() {

        _buffer = _buffer.trim(); // remove whitespace

        if (_buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        char firstChar = _buffer.charAt(0);

        if (firstChar == ';') {
            currentToken = new Token(";", Token.Type.SEMICOLON);
        }

        if (firstChar == '=' || firstChar == '<' || firstChar == '>') {
            currentToken = new Token(Character.toString(firstChar), Token.Type.COMPARISON);
        }

        if (Character.isDigit(firstChar)) {
            StringBuilder stringBuilder = new StringBuilder(Character.toString(firstChar));
            for (int i = 1; i < _buffer.length() && Character.isDigit(_buffer.charAt(i)); i++) {
                stringBuilder.append(_buffer.charAt(i));
            }
            currentToken = new Token(stringBuilder.toString(), Token.Type.INT);
        }

        int j = 0;
        int k = 0;
        if (Character.isLetter(firstChar)) {
            int i = 0;
            while (i < _buffer.length() && Character.isLetter(_buffer.charAt(i))) {
                i += 1;
            }
            j = i;
            String findWord = _buffer.substring(0, i).toLowerCase();
            if (Token.regexMatching(findWord, Token.nameRegex) || Token.nameContaining(findWord)) {
                findWord = "name";
            } else if (Token.regexMatching(findWord, Token.locationRegex) || Token.locationContaining(findWord)) {
                findWord = "location";
            } else if (Token.regexMatching(findWord, Token.priceRegex) || Token.priceContaining(findWord)) {
                findWord = "price";
            } else if (Token.regexMatching(findWord, Token.yearRegex) || Token.yearContaining(findWord)) {
                findWord = "year";
            }
            k = findWord.length();
            if (Arrays.asList(Token.keyword).contains(findWord.toLowerCase())) {
                currentToken = new Token(findWord, Token.Type.KEYWORD);
            } else {
                currentToken = new Token(findWord, Token.Type.NAME);
            }
        }

        // Remove the extracted token from buffer
        int tokenLen = currentToken.token().length();
        if (j > tokenLen) {
            tokenLen = j;
        } else if (j < k) {
            tokenLen = j;
        }
        _buffer = _buffer.substring(tokenLen);
    }
}
