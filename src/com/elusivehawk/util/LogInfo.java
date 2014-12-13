
package com.elusivehawk.util;


/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class LogInfo
{
	public final EnumLogType type;
	public final String log;
	public final Throwable err;
	public final Object[] args;
	
	public LogInfo(EnumLogType logType, String info)
	{
		this(logType, info, new Object[0]);
		
	}
	
	public LogInfo(EnumLogType logType, String info, Throwable e)
	{
		this(logType, info, e, new Object[0]);
		
	}
	
	public LogInfo(EnumLogType logType, String info, Object... objs)
	{
		this(logType, info, null, objs);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public LogInfo(EnumLogType logType, String info, Throwable e, Object... objs)
	{
		type = logType;
		log = info;
		err = e;
		args = objs;
		
	}
	
}
