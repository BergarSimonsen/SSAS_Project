package dk.itu.ssas.project;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Converter {
	public static String toMd5(String s) {
		String plaintext = "your text here";
		MessageDigest m;
		String hashtext = "";
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(s.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hashtext;
	}
}
