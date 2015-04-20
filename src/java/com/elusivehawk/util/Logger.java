
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class Logger
{
	private static final Logger INSTANCE = new Logger();
	
	private ILog log = new DefaultLog();
	private boolean verbose = true;
	
	private Logger(){}
	
	@Deprecated
	public static Logger log()
	{
		return INSTANCE;
	}
	
	public static synchronized void setLog(ILog l)
	{
		assert l != null;
		
		INSTANCE.log = l;
		
	}
	
	public static boolean enableVerbosity()
	{
		return INSTANCE.verbose;
	}
	
	public static synchronized void setEnableVerbosity(boolean v)
	{
		INSTANCE.verbose = v;
		
	}
	
	public static void debug(String msg, Object... info)
	{
		log(EnumLogType.DEBUG, msg, info);
		
	}
	
	public static void err(Throwable e)
	{
		err("Error caught:", e);
		
	}
	
	public static void err(String msg, Object... info)
	{
		log(EnumLogType.ERROR, msg, info);
		
	}
	
	public static void err(String msg, Throwable e, Object... info)
	{
		log(EnumLogType.ERROR, msg, e, info);
		
	}
	
	public static void info(String msg, Object... info)
	{
		log(EnumLogType.INFO, msg, info);
		
	}
	
	public static void verbose(String msg, Object... info)
	{
		log(EnumLogType.VERBOSE, msg, info);
		
	}
	
	public static void warn(String msg, Object... info)
	{
		log(EnumLogType.WARN, msg, info);
		
	}
	
	public static void wtf(String msg, Object... info)
	{
		log(EnumLogType.WTF, msg, info);
		
	}
	
	public static void log(EnumLogType type, String msg, Object... info)
	{
		log(type, msg, null, info);
		
	}
	
	public static void log(EnumLogType type, String msg, Throwable e, Object... info)
	{
		log(new LogInfo(type, msg, e, info));
		
	}
	
	public static void log(LogInfo info)
	{
		INSTANCE.log.log(info, enableVerbosity());
		
	}
	
}
