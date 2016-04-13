package net.sokontokoro_factory.tweetly_oauth.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Calculation {

	public static byte[] calcHmacSHA1(String data, String key)
			throws NoSuchAlgorithmException, InvalidKeyException {

		byte[] rawHmac = null;
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(secretKey);
		rawHmac = mac.doFinal(data.getBytes());
		return rawHmac;
	}
	
	public static String getTimestamp(){
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	public static String getNonce(){
		return UUID.randomUUID().toString();
	}
}