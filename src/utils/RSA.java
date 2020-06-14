package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class RSA {
    private BigInteger n;
    private BigInteger e;
    String ascii = "azertyuiopqsdfghjklmwxcvbn1234567890°²&é\"'(-è_çà)=+-~#{[|`\\^@]}*";
    ArrayList<String> asciiTable;

    public RSA(){
        asciiTable = new ArrayList<>();
        for (char c : ascii.toCharArray()){
            asciiTable.add(String.valueOf(c));
        }
    }

    public RSA(String pw){
        this();
        if (pw.equals("")) {
            return;
        }
        long seed = str2int(pw).longValue();

        Random rand = new Random(seed);
        e = BigInteger.valueOf(65535);

        BigInteger p = BigInteger.probablePrime(512, rand);
        BigInteger q = BigInteger.probablePrime(512, rand);
        n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        do {
            p = p.nextProbablePrime();
            q = q.nextProbablePrime();
            n = p.multiply(q);
            phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        } while (!e.gcd(phi).equals(BigInteger.ONE));
        BigInteger d = e.modInverse(phi);
    }

    public BigInteger code(String str){
        if (n == null){
            return BigInteger.ONE;
        }
        BigInteger name = str2int(str);
        return name.modPow(e, n);
    }

    public String codeAndStr(String str, int length){
        BigInteger res = code(str);
        return int2strlength(res, length);
    }

    public BigInteger str2int(String text) {
        BigInteger result = BigInteger.ZERO;
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            BigInteger chr = new BigInteger(Integer.toBinaryString(b), 2);
            result = result.shiftLeft(8).add(chr);
        }
        return result;
    }

    public String int2str(BigInteger a) {
        StringBuilder result = new StringBuilder();
        while (!a.equals(BigInteger.ZERO)) {
            BigInteger chr = a.and(BigInteger.valueOf(63));
            a = a.shiftRight(6);
            result.append(asciiTable.get(chr.intValue()));
        }
        return result.toString();
    }

    public String int2strlength(BigInteger a, int length) {
        StringBuilder result = new StringBuilder();
        while (!a.equals(BigInteger.ZERO) && (result.length() < length || length == 0)) {
            BigInteger chr = a.and(BigInteger.valueOf(63));
            a = a.shiftRight(6);
            result.append(asciiTable.get(chr.intValue()));
        }
        return result.toString();
    }
}
