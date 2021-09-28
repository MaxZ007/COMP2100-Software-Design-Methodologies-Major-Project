package com.example.cars4sale.Tokenizer;

public abstract class Tokenizer {

    /**
     * check whether there is a next token in the remaining text
     *
     * @return boolean
     */
    public abstract boolean hasNext();

    /**
     * return the current token extracted by next() method
     *
     * @return the current token
     */
    public abstract Token current();

    /**
     * Extract next token from the current text and save it.
     */
    public abstract void next();

}
