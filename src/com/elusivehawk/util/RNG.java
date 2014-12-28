
package com.elusivehawk.util;

import java.util.Random;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class RNG
{
	private static final Random RNG = new Random();
	
	private RNG(){}
	
	public static Random rng()
	{
		return RNG;
	}
	
	public static String randomString()
	{
		return randomString(1024);
	}
	
	public static String randomString(int limit)
	{
		char[] ch = new char[rng().nextInt(limit)];
		
		for (int c = 0; c < ch.length; c++)
		{
			ch[c] = (char)(rng().nextInt() & 0xFFFF);
			
		}
		
		return new String(ch);
	}
	
}
