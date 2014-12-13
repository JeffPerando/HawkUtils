
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
	
	public static Logger log()
	{
		return INSTANCE;
	}
	
	public static synchronized void setLog(ILog l)
	{
		assert l != null;
		
		INSTANCE.log = l;
		
	}
	
	public boolean enableVerbosity()
	{
		return this.verbose;
	}
	
	public synchronized void setEnableVerbosity(boolean v)
	{
		this.verbose = v;
		
	}
	
	public void debug(String msg, Object... info)
	{
		this.log(EnumLogType.DEBUG, msg, info);
		
	}
	
	public void err(Throwable e)
	{
		this.err(null, e);
		
	}
	
	public void err(String msg, Throwable e, Object... info)
	{
		this.log(EnumLogType.ERROR, msg, e, info);
		
	}
	
	public void info(String msg, Object... info)
	{
		this.log(EnumLogType.INFO, msg, info);
		
	}
	
	public void verbose(String msg, Object... info)
	{
		this.log(EnumLogType.VERBOSE, msg, info);
		
	}
	
	public void warn(String msg, Object... info)
	{
		this.log(EnumLogType.WARN, msg, info);
		
	}
	
	public void wtf(String msg, Object... info)
	{
		this.log(EnumLogType.WTF, msg, info);
		
	}
	
	public void log(EnumLogType type, String msg, Object... info)
	{
		this.log(type, msg, null, info);
		
	}
	
	public void log(EnumLogType type, String msg, Throwable e, Object... info)
	{
		this.log(new LogInfo(type, msg, e, info));
		
	}
	
	public void log(LogInfo info)
	{
		this.log.log(info, this.verbose);
		
	}
	
}
