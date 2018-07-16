package br.com.globo.support;

import java.nio.charset.Charset;

public class Encodes {

    private Encodes() {

    }

    public static String encodeFromHeaderParameter(String input) {

        var i88591 = Charset.forName("ISO-8859-1");
        var utf8 = Charset.forName("UTF-8");

        return new String(input.getBytes(i88591), utf8);
    }
}