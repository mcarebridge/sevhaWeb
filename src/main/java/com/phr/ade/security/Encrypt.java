package com.phr.ade.security;

import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Encrypt
{
	private static Logger      log              = Logger.getLogger(Encrypt.class
	                                                    .getName());
	public final static String accountSeperator = ":";
	
	private static String      key              = "1q2w3e4r";
	
	private static String decrypt(String message) throws Exception
	{
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}
	
	private static String encrypt(String message) throws Exception
	{
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return toHexString(cipher.doFinal(message.getBytes("UTF-8")));
	}
	
	private static byte[] convertHexString(String ss)
	{
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++)
		{
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}
	
	private static String toHexString(byte b[])
	{
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++)
		{
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}
	
	public static String[] decodeAccount(String cookieValue) throws Exception
	{
		String origi = Encrypt.decrypt(cookieValue);
		String[] parts = origi.split(Encrypt.accountSeperator);
		if (parts.length == 2 && !parts[0].equals("") && !parts[1].equals(""))
		{
			return parts;
		} else
		{
			return null;
		}
	}
	
	public static String encodeAccount(String username, String password)
	        throws Exception
	{
		String encryptString = null;
		encryptString = Encrypt.encrypt(username + Encrypt.accountSeperator
		        + password);
		return encryptString;
	}
	
	public static String decodeString(String cookieValue) throws Exception
	{
		String origi = Encrypt.decrypt(cookieValue);
		return origi;
		
	}
	
	public static String encodeString(String stringValue) throws Exception
	{
		String encryptString = null;
		encryptString = Encrypt.encrypt(stringValue);
		return encryptString;
	}
	
}