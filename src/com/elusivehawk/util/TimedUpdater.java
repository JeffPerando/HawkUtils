
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TimedUpdater implements IUpdatable
{
	private final double timeBetweenCalls;
	private final IUpdatable upd;
	private double timeSpent = 0d;
	
	@SuppressWarnings("unqualified-field-access")
	public TimedUpdater(double t, IUpdatable u)
	{
		assert u != null;
		
		timeBetweenCalls = t;
		upd = u;
		
	}
	
	@Override
	public void update(double delta, Object... extra) throws Throwable
	{
		this.timeSpent += delta;
		
		if (this.timeSpent >= this.timeBetweenCalls)
		{
			this.upd.update(this.timeSpent, extra);
			
			this.timeSpent = 0d;
			
		}
		
	}
	
}
