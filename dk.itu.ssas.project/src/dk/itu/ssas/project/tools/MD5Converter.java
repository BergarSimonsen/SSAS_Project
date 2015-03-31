package dk.itu.ssas.project.tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Converter {
	public static String toMd5(String s) {
		MessageDigest m;
		String hashtext = s;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			
			for (int i = 0; i < 1000; i++)
			{
				m.update(s.getBytes());
				byte[] digest = m.digest();
				BigInteger bigInt = new BigInteger(1,digest);
				hashtext = bigInt.toString(16);
				// Now we need to zero pad it if you actually want the full 32 chars.
				while(hashtext.length() < 32 ){
				  hashtext = "0"+hashtext;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		return hashtext;
	}
}
