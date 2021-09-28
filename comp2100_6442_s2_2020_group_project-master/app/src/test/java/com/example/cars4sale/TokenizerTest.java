package com.example.cars4sale;

import com.example.cars4sale.Tokenizer.MyTokenizer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TokenizerTest {
    final static String text1 = "carNme=bmw; cArsLoCcccAtedin = canberra; carspPppIirce < 100000 ; carYaeAar > 2000";
    final static String text2 = "name = bmw; location = canberra; price < 100000; year > 2000";
    final static String text3 = "nme  =mini; LOC= canberra;pircCE >100; eaRrrrr<2012";
    final static String text4 = "name=mini; location=canberra; price>100; eaRrrrr<2012";
    final static String text5 = "ha=mini";
    final static String text6 = "nbm=mini";
    final static String text7 = "name=mini";


    @Test(timeout = 1000)
    public void testTokenizerEquity1() {

        MyTokenizer tokenizer1 = new MyTokenizer(text1);
        MyTokenizer tokenizer2 = new MyTokenizer(text2);

        while (tokenizer1.hasNext() && tokenizer2.hasNext()) {
            assertEquals(tokenizer1.current().token(), tokenizer2.current().token());
            tokenizer1.next();
            tokenizer2.next();
        }
    }

    @Test(timeout = 1000)
    public void testTokenizerEquity2() {

        MyTokenizer tokenizer1 = new MyTokenizer(text3);
        MyTokenizer tokenizer2 = new MyTokenizer(text4);

        while (tokenizer1.hasNext() && tokenizer2.hasNext()) {
            assertEquals(tokenizer1.current().token(), tokenizer2.current().token());
            tokenizer1.next();
            tokenizer2.next();
        }
    }

    @Test(timeout = 1000)
    public void testTokenizerInEquity1() {
        MyTokenizer tokenizer1 = new MyTokenizer(text5);
        MyTokenizer tokenizer2 = new MyTokenizer(text7);

        assertNotEquals(tokenizer1.current().token(), tokenizer2.current().token());
    }

    @Test(timeout = 1000)
    public void testTokenizerInEquity2() {

        MyTokenizer tokenizer1 = new MyTokenizer(text6);
        MyTokenizer tokenizer2 = new MyTokenizer(text7);

        assertNotEquals(tokenizer1.current().token(), tokenizer2.current().token());
    }
}
