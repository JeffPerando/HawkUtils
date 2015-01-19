
package com.elusivehawk.util;

/**
 * 
 * Helper class (Yes, another one) for timing.
 * 
 * @author Elusivehawk
 */
public class Timer
{
	public static final double NANO_SEC = 1000000000D;
	
	protected long start = 0;
	protected double time = 0D;
	protected boolean started = false;
	
	public void start()
	{
		if (!this.started)
		{
			this.started = true;
			
			this.start = System.nanoTime();
			
		}
		
	}
	
	public void stop()
	{
		if (this.started)
		{
			this.started = false;
			
			this.time = (System.nanoTime() - this.start) / NANO_SEC;
			
		}
		
	}
	
	public double time()
	{
		return this.started ? 0 : this.time;
	}
	
	public double timeCall(Runnable r) throws Throwable
	{
		this.start();
		
		try
		{
			r.run();
			
		}
		catch (Throwable e)
		{
			throw e;
		}
		finally
		{
			this.stop();
			
		}
		
		return this.time;
	}
	
}
