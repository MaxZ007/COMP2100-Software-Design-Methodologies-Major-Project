package com.example.cars4sale.Tokenizer;

/**
 * Tokenizer's pre-treatment function demo for fuzzy search queries.
 */
public class TokenizerDemo {

    final static String text1 = "carNme=bmw; cArsLoCcccAtedin = canberra; carspPppIirce < 100000 ; carYaeAar > 2000";
    final static String text2 = "nme = mini; LOC= canberra;pircCE >100; eaRrrrr<2012";

    public static void main(String[] args) {
        MyTokenizer tokenizer1 = new MyTokenizer(text1);
        while (tokenizer1.hasNext()) {
            System.out.println(tokenizer1.current().token());
            tokenizer1.next();
        }
        System.out.println("---------------");
        MyTokenizer tokenizer2 = new MyTokenizer(text2);
        while (tokenizer2.hasNext()) {
            System.out.println(tokenizer2.current().token());
            tokenizer2.next();
        }
    }
}
