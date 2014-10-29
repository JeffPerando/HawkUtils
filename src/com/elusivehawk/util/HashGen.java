
package com.elusivehawk.util;

import java.security.MessageDigest;

/**
 * 
 * Enables for easy hashing.
 * 
 * @author Elusivehawk
 */
public final class HashGen
{
	private static final HashGen INSTANCE = new HashGen();
	
	private MessageDigest
				md5 = newDigest("MD5"),
				sha1 = newDigest("SHA-1"),
				sha2 = newDigest("SHA-2"),
				sha3 = newDigest("SHA-3"),
				sha256 = newDigest("SHA-256");
	
	private HashGen(){}
	
	private static MessageDigest newDigest(String name)
	{
		MessageDigest ret = null;
		
		try
		{
			ret = MessageDigest.getInstance(name);
			
		}
		catch (Throwable e)
		{
			Logger.log().err(e);
			
		}
		
		return ret;
	}
	
	public static byte[] md5(byte[]... in)
	{
		return hash(INSTANCE.md5, in);
	}
	
	public static byte[] sha1(byte[]... in)
	{
		return hash(INSTANCE.sha1, in);
	}
	
	public static byte[] sha2(byte[]... in)
	{
		return hash(INSTANCE.sha2, in);
	}
	
	public static byte[] sha3(byte[]... in)
	{
		return hash(INSTANCE.sha3, in);
	}
	
	public static byte[] sha256(byte[]... in)
	{
		return hash(INSTANCE.sha256, in);
	}
	
	public static byte[] hash(MessageDigest md, byte[]... in)
	{
		if (md == null)
		{
			return new byte[0];
		}
		
		for (byte[] bs : in)
		{
			md.update(bs);
			
		}
		
		return md.digest();
	}
	
}
