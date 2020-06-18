package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RSA {
    private BigInteger n;
    private BigInteger e;
    String ascii = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ÀÁÂÃÄÅÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝàáâãäèéê ";;
    ArrayList<String> asciiTable;
    Map<Character, Integer> asciiMap;
    private BigInteger d;

    public RSA(){
        asciiTable = new ArrayList<>();
        asciiMap = new HashMap<>();
        int i = 0;
        for (char c : ascii.toCharArray()){
            asciiTable.add(String.valueOf(c));
            asciiMap.put(c, i++);
        }
    }

    public RSA(String pw, int bit){
        this();
        if (pw.equals("")) {
            return;
        }
        long seed = str2int(pw).longValue();

        Random rand = new Random(seed);
        e = BigInteger.valueOf(65535);

        BigInteger p = BigInteger.probablePrime(bit, rand);
        BigInteger q = BigInteger.probablePrime(bit, rand);
        n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        do {
            p = p.nextProbablePrime();
            q = q.nextProbablePrime();
            n = p.multiply(q);
            phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        } while (!e.gcd(phi).equals(BigInteger.ONE));
        d = e.modInverse(phi);
    }

    public String code(String str){
        if (n == null){
            return "error";
        }
        StringBuilder string = new StringBuilder();
        for (int i = 0; 126*i < str.length(); i++){
            string.append(str2int(str.substring(i * 126, Math.min(str.length(), (i + 1) * 126))).modPow(e, n)); //7*18 = 126
            if (126*(i+1) < str.length()){
                string.append(" and ");
            }
        }
        return string.toString();
    }

    public String decode(BigInteger bi){
        String str = int2str(bi.modPow(d, n));
        return str;
    }

    public String decode(String bi){
        StringBuilder res = new StringBuilder();
        for (String str : bi.split(" and ")){
            res.append(int2str(new BigInteger(str).modPow(d, n)));
        }
        return res.toString();
    }

    public String codeAndStr(String str, int length){
        StringBuilder result = new StringBuilder();
        for (String string : code(str).split(" and ")){
            result.append(int2strlength(new BigInteger(string), length - result.length()));
            if (result.length() >= length)
                break;
        }
        return result.toString();
    }

    public BigInteger str2int(String text) {
        BigInteger result = BigInteger.ZERO;
        for (char c : text.toCharArray()){
            if (asciiMap.get(c) == null)
                continue;
            result = result.shiftLeft(7).add(BigInteger.valueOf(asciiMap.get(c)));
        }
        return result;
    }

    public String int2str(BigInteger a) {
        StringBuilder result = new StringBuilder();
        while (!a.equals(BigInteger.ZERO)) {
            BigInteger chr = a.and(BigInteger.valueOf(127));
            a = a.shiftRight(7);
            result.append(asciiTable.get(chr.intValue()));
        }
        result.reverse();
        return result.toString();
    }

    public String int2strlength(BigInteger a, int length) {
        StringBuilder result = new StringBuilder();
        while (!a.equals(BigInteger.ZERO) && (result.length() < length || length == 0)) {
            BigInteger chr = a.and(BigInteger.valueOf(127));
            a = a.shiftRight(7);
            if (chr.mod(BigInteger.TWO).equals(BigInteger.ZERO))
                result.append((asciiTable.get(chr.intValue())).toUpperCase());
            else
                result.append((asciiTable.get(chr.intValue())).toLowerCase());
        }
        return result.toString();
    }
}
