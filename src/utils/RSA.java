package utils;

import java.math.BigInteger;
import java.util.*;

public class RSA {
    private BigInteger n;
    private BigInteger e;
    String asciiLong = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ÀÁÂÃÄÅÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝàáâãäèéê ";
    String asciiShort = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";
    ArrayList<String> asciiLongArray;
    Map<Character, Integer> asciiLongMap;
    ArrayList<String> asciiShortArray;
    Map<Character, Integer> asciiShortMap;
    private BigInteger d;

    public RSA(){
        asciiLongArray = new ArrayList<>();
        asciiLongMap = new HashMap<>();
        int i = 0;
        for (char c : asciiLong.toCharArray()){
            asciiLongArray.add(String.valueOf(c));
            asciiLongMap.put(c, i++);
        }
        asciiShortArray = new ArrayList<>();
        asciiShortMap = new HashMap<>();
        i = 0;
        for (char c : asciiShort.toCharArray()){
            asciiShortArray.add(String.valueOf(c));
            asciiShortMap.put(c, i++);
        }
    }

    public RSA(String pw, int bit){
        this();
        if (pw.equals("")) {
            return;
        }
        long seed = str2int(pw, false).longValue();

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

    public String code(String str, boolean asciishort){
        if (n == null){
            return "error";
        }
        StringBuilder string = new StringBuilder();
        for (int i = 0; 126*i < str.length(); i++){
            string.append(str2int(str.substring(i * 126, Math.min(str.length(), (i + 1) * 126)), asciishort).modPow(e, n)); //7*18 = 126
            if (126*(i+1) < str.length()){
                string.append(" and ");
            }
        }
        return string.toString();
    }

    public String decode(BigInteger bi){
        return int2str(bi.modPow(d, n));
    }

    public String decode(String bi){
        StringBuilder res = new StringBuilder();
        for (String str : bi.split(" and ")){
            res.append(int2str(new BigInteger(str).modPow(d, n)));
        }
        return res.toString();
    }

    public BigInteger str2int(String text, boolean asciiShort) {
        BigInteger result = BigInteger.ZERO;
        for (char c : text.toCharArray()){
            if (!asciiShort){
                if (asciiLongMap.get(c) == null)
                    continue;
                result = result.shiftLeft(7).add(BigInteger.valueOf(asciiLongMap.get(c)));
            }
            else {
                if (asciiShortMap.get(c) == null)
                    continue;
                result = result.shiftLeft(7).add(BigInteger.valueOf(asciiShortMap.get(c)));

            }
        }
        return result;
    }

    public String int2str(BigInteger a) {
        StringBuilder result = new StringBuilder();
        while (!a.equals(BigInteger.ZERO)) {
            BigInteger chr = a.and(BigInteger.valueOf(127));
            a = a.shiftRight(7);
            result.append(asciiLongArray.get(chr.intValue()));
        }
        result.reverse();
        return result.toString();
    }

    public String int2strlength(BigInteger a, int length, boolean asciishort) {
        StringBuilder result = new StringBuilder();
        while (!a.equals(BigInteger.ZERO) && (result.length() < length || length == 0)) {
            if (!asciishort){
                BigInteger chr = a.and(BigInteger.valueOf(127));
                a = a.shiftRight(7);
                if (chr.mod(BigInteger.TWO).equals(BigInteger.ZERO))
                    result.append((asciiLongArray.get(chr.intValue())).toUpperCase());
                else
                    result.append((asciiLongArray.get(chr.intValue())).toLowerCase());
            }
            else {
                BigInteger chr = a.and(BigInteger.valueOf(63));
                a = a.shiftRight(6);
                if (chr.mod(BigInteger.TWO).equals(BigInteger.ZERO))
                    result.append((asciiShortArray.get(chr.intValue())).toUpperCase());
                else
                    result.append((asciiShortArray.get(chr.intValue())).toLowerCase());
            }

        }
        return result.toString();
    }

    public String codeAndStrCB(String str, int length, boolean ascii) {
        StringBuilder result = new StringBuilder();
        for (String string : code(str, ascii).split(" and ")){
            result.append(int2strlength(new BigInteger(string), length - result.length(), ascii));
            if (result.length() >= length)
                break;
        }
        return result.toString();
    }
}
