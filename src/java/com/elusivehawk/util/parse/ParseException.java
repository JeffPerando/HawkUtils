
package com.elusivehawk.util.parse;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ParseException extends RuntimeException
{
	public ParseException()
	{
		super();
		
	}
	
	public ParseException(String err)
	{
		super(err);
		
	}
	
	public ParseException(String err, Object... objs)
	{
		this(String.format(err, objs));
		
	}
	
	public ParseException(Throwable e)
	{
		super(e);
		
	}
	
	public ParseException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
	public ParseException(String err, Throwable e, boolean arg2, boolean arg3)
	{
		super(err, e, arg2, arg3);
		
	}
	
}
