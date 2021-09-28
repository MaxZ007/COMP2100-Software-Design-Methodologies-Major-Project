package com.example.cars4sale.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Build a Token class with various types of tokens.
 * The class also realizes the pretreatment of converting partially-correct words to the keywords.
 *
 * @author: Yuxuan Lin
 * @UID: u6828533
 */
public class Token {

    static final String[] keyword = {"name", "location", "price", "year"};
    static final String nameRegex = "name*";
    static final String locationRegex = "location*";
    static final String priceRegex = "price*";
    static final String yearRegex = "year*";
    private final String _token;
    private final Type _type;

    public Token(String token, Type type) {
        _token = token;
        _type = type;
    }

    public static boolean regexMatching(String regex, String input) {
        return Pattern.matches(regex, input);
    }

    public static String sortString(String s) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
        }

        ArrayList<Character> arrayList = new ArrayList<>(set);

        char[] charArray = new char[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            charArray[i] = arrayList.get(i);
        }
        Arrays.sort(charArray);
        return new String(charArray);
    }

    public static boolean commonCharacter(String s1, String s2) {
        if (s1.length() < 2 || s2.length() < 2) {
            return false;
        } else if (s1.length() < s2.length()) {
            for (int i = 0; i < s1.length(); i++) {
                if (s2.indexOf(s1.charAt(i)) < 0) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < s2.length(); i++) {
                if (s1.indexOf(s2.charAt(i)) < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean nameContaining(String input) {
        String keyword = "name";
        if (keyword.contains(input) || input.contains(keyword)) {
            return true;
        } else {
            keyword = sortString(keyword);
            input = sortString(input);
            return commonCharacter(input, keyword);
        }
    }

    public static boolean locationContaining(String input) {
        String keyword = "location";
        if (keyword.contains(input) || input.contains(keyword)) {
            return true;
        } else {
            keyword = sortString(keyword);
            input = sortString(input);
            return commonCharacter(input, keyword);
        }
    }

    public static boolean priceContaining(String input) {
        String keyword = "price";
        if (keyword.contains(input) || input.contains(keyword)) {
            return true;
        } else {
            keyword = sortString(keyword);
            input = sortString(input);
            return commonCharacter(input, keyword);
        }
    }

    public static boolean yearContaining(String input) {
        String keyword = "year";
        if (keyword.contains(input) || input.contains(keyword)) {
            return true;
        } else {
            keyword = sortString(keyword);
            input = sortString(input);
            return commonCharacter(input, keyword);
        }
    }

    public String token() {
        return _token;
    }

    public Type type() {
        return _type;
    }

    public enum Type {INT, KEYWORD, COMPARISON, SEMICOLON, NAME}

    /*
    public static void main(String[] args) {
        String keyword = "year";
        keyword = sortString(keyword);
        String input = "yearrrrr";
        input = sortString(input);
        System.out.println(keyword);
        System.out.println(input);
        System.out.println(yearContaining("yearrrrr"));

        Set<Character> set = new HashSet<>();
        for (int i = 0; i < "yyuearrrrrrrr".length(); i++) {
            set.add("yyuearrrrrrrr".charAt(i));
        }
        System.out.println(set);
        System.out.println(set);

        ArrayList<Character> list = new ArrayList<>(set);
        System.out.println(list);

        char[] myCharArray = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            myCharArray[i] = list.get(i);
        }
        System.out.println(myCharArray);

        System.out.println(sortString("loc"));
        System.out.println(sortString("location"));

        System.out.println(commonCharacter("loc", "location"));

        System.out.println(commonCharacter("hyeaarr", "year"));

        System.out.println(commonCharacter("lo", "location"));

        System.out.println(regexMatching(locationRegex, "locationnnnnn"));

    }*/
}
