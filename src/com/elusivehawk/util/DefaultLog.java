
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
	@Override
	public void log(LogInfo info, boolean verbose)
	{
		if (!verbose && info.type == EnumLogType.VERBOSE)
		{
			return;
		}
		
		if (!CompInfo.DEBUG && info.type == EnumLogType.DEBUG)
		{
			return;
		}
		
		Thread thr = Thread.currentThread();
		String log = info.log;
		
		if (log != null && !log.equalsIgnoreCase(""))
		{
			String str = String.format(log, info.args);
			
			String[] spl = str.split("\n");
			
			for (String msg : spl)
			{
				printString(msg, thr, info);
				
			}
			
		}
		
		if (info.err != null)
		{
			Throwable e = info.err;
			
			printString(String.format("%s: %s", e.getClass().getName(), e.getLocalizedMessage()), thr, info);
			
			for (StackTraceElement ste : e.getStackTrace())
			{
				printString(String.format("\tat %s", ste.toString()), thr, info);
				
			}
			
		}
		
	}
	
	private static void printString(String str, Thread current, LogInfo info)
	{
		String fin = String.format("[%s] [%s] [%s] %s", info.type, current.getName(), StringHelper.parseDate(Calendar.getInstance(), "-", ":"), (info.type.err && (info.log == null || "".equals(info.log)) ? "Error caught:" : str));
		
		if (info.type.err)
		{
			System.err.println(fin);
			
		}
		else
		{
			System.out.println(fin);
			
		}
		
	}
	
}
