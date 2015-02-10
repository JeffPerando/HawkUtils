
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Dirtable implements IDirty
{
	private boolean dirty = false;
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public synchronized void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
}
