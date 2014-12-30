
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Bitmask
{
	private final long mask;
	private final int offset;
	
	@SuppressWarnings("unqualified-field-access")
	public Bitmask(long bitmask, int off)
	{
		assert off >= 0;
		
		mask = bitmask << off;
		offset = off;
		
	}
	
	public long getValue(long n)
	{
		return (n & this.mask) >> this.offset;
	}
	
	public long setValue(long n, long value)
	{
		return (n & ~this.mask) | ((value << this.offset) & this.mask);
	}
	
}
