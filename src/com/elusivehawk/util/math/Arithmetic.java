
package com.elusivehawk.util.math;

import com.elusivehawk.util.Dirtable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Arithmetic extends Dirtable
{
	private boolean immutable = false, sync = false;
	
	public boolean isImmutable()
	{
		return this.immutable;
	}
	
	public Arithmetic setImmutable()
	{
		this.immutable = true;
		
		return this;
	}
	
	public boolean isSync()
	{
		return this.sync;
	}
	
	public Arithmetic setSync()
	{
		this.sync = true;
		
		return this;
	}
	
	public abstract int size();
	
}
