
package com.elusivehawk.util.task;

import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class Task
{
	private final ITaskListener listener;
	private int triesLeft = 0;
	private boolean complete = false, tryAgain = true;
	
	public Task()
	{
		this(null);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Task(ITaskListener tlis)
	{
		listener = tlis;
		
	}
	
	public final boolean completeTask()
	{
		if (this.complete)
		{
			return true;
		}
		
		if (this.triesLeft == 0)
		{
			return true;
		}
		
		boolean finish = false;
		
		try
		{
			finish = this.finishTask();
			
		}
		catch (Throwable e)
		{
			Logger.err(e);
			
		}
		
		if (finish)
		{
			synchronized (this)
			{
				this.complete = true;
				
			}
			
			if (this.listener != null)
			{
				this.listener.onTaskComplete(this);
				
			}
			
		}
		else
		{
			if (this.doTryAgain())
			{
				this.triesLeft--;
				
			}
			else
			{
				this.triesLeft = 0;
				
			}
			
		}
		
		return this.complete;
	}
	
	public boolean doTryAgain()
	{
		return this.tryAgain;
	}
	
	public boolean isFinished()
	{
		return this.complete;
	}
	
	protected abstract boolean finishTask() throws Throwable;
	
}
