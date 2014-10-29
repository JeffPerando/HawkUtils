
package com.elusivehawk.util;

import java.util.Calendar;
import com.elusivehawk.util.string.StringHelper;

/**
 * 
 * Default logging implementation.
 * 
 * @author Elusivehawk
 */
public class DefaultLog implements ILog
{
	private boolean enableVerbosity = true;
	
	@Override
	public void log(EnumLogType type, String msg)
	{
		if (!this.enableVerbosity && type == EnumLogType.VERBOSE)
		{
			return;
		}
		
		if (!CompInfo.DEBUG && type == EnumLogType.DEBUG)
		{
			return;
		}
		
		Thread thr = Thread.currentThread();
		
		String fin = String.format("[%s] [%s] [%s]: %s", type, thr.getName(), StringHelper.parseDate(Calendar.getInstance(), "-", ":"), (type.err && (msg == null || "".equals(msg)) ? "Error caught:" : msg));
		
		if (type.err)
		{
			System.err.println(fin);
			
		}
		else
		{
			System.out.println(fin);
			
		}
		
	}
	
	@Override
	public boolean enableVerbosity()
	{
		return this.enableVerbosity;
	}
	
	@Override
	public void setEnableVerbosity(boolean v)
	{
		this.enableVerbosity = v;
		
	}
	
}
