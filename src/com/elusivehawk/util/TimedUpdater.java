
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TimedUpdater implements IUpdatable
{
	private final double delay;
	private final IUpdatable upd;
	
	private double time = 0d;
	
	@SuppressWarnings("unqualified-field-access")
	public TimedUpdater(double delta, IUpdatable updater)
	{
		assert delta >= 0.0;
		assert updater != null;
		
		delay = delta;
		upd = updater;
		
	}
	
	@Override
	public void update(double delta) throws Throwable
	{
		this.time += delta;
		
		if (this.time >= this.delay)
		{
			this.upd.update(this.time);
			
			this.time = 0d;
			
		}
		
	}
	
}
