
package com.elusivehawk.util.concurrent;

import com.elusivehawk.util.IUpdatable;
import com.elusivehawk.util.Timer;

/**
 * 
 * Abstract class for timed threading.
 * 
 * @author Elusivehawk
 */
public class ThreadTimed extends ThreadStoppable implements IUpdatable
{
	public static final int MILI_SEC = 1000;
	
	private double time = 0, lastTime, timeUsed = 0, delta, deltaTime = 0, timeSpent = 0;
	
	private IUpdatable updater = this;
	
	private final int updates;
	private Timer timer = new Timer();
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadTimed()
	{
		updates = 30;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadTimed(String name)
	{
		super(name);
		
		updates = 30;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadTimed(IUpdatable upd, int updCount)
	{
		assert upd != null;
		assert updCount > 0;
		
		updater = upd;
		updates = updCount;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ThreadTimed(String name, IUpdatable upd, int updCount)
	{
		super(name);
		
		assert upd != null;
		assert updCount > 0;
		
		updater = upd;
		updates = updCount;
		
	}
	
	@Override
	public final void rawUpdate() throws Throwable
	{
		if (this.timeUsed + this.delta > 1.0)//Have we run out of time?
		{
			long sleep = (long)(MILI_SEC * (1.0 - this.timeUsed));
			
			if (sleep > 0)
			{
				Thread.sleep(sleep);//Sleep for the remainder of the second
				
			}
			
			//Zero out the timed used for the new second.
			
			this.timeSpent = 0;
			this.timeUsed = 0;
			return;
		}
		
		this.lastTime = this.time;
		this.time = System.nanoTime() / Timer.NANO_SEC;
		this.deltaTime = this.time - this.lastTime;
		
		this.timer.start();
		
		this.updater.update(this.deltaTime);
		
		this.timer.stop();
		
		this.timeSpent = this.timer.time();
		
		if (this.timeSpent < this.delta)//What if we actually have MORE time than we know what to do with?
		{
			if (this.doPostUpdate())
			{
				this.timer.start();
				
				this.postUpdate(this.deltaTime);
				
				this.timer.stop();
				
				this.timeSpent += this.timer.time();
				
				if (this.timeSpent >= this.delta)//Do we STILL have more time?!
				{
					return;//No? Good.
				}
				
			}
			
			Thread.sleep((long)((this.delta - this.timeSpent) * MILI_SEC));//Sleep for the remaining time.
			
		}
		
	}
	
	@Override
	public void update(double delta) throws Throwable{}
	
	@Override
	public boolean initiate()
	{
		this.delta = (Timer.NANO_SEC / this.getTargetUpdateCount()) / Timer.NANO_SEC;
		
		return true;
	}
	
	@Override
	public final void firstUpdate()
	{
		this.timer.start();
		this.timer.stop();
		
		this.time = System.nanoTime() / Timer.NANO_SEC;
		
	}
	
	@Override
	protected void onPostUpdate()
	{
		this.timeUsed += this.timeSpent;
		
	}
	
	public int getTargetUpdateCount()
	{
		return this.updates;
	}
	
	public boolean doPostUpdate()
	{
		return false;
	}
	
	public void postUpdate(double delta) throws Throwable{}
	
}
