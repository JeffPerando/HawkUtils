
package com.elusivehawk.util.json;

import com.elusivehawk.util.string.ParseException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonParseException extends ParseException
{
	public JsonParseException()
	{
		super();
		
	}
	
	public JsonParseException(String err)
	{
		super(err);
		
	}
	
	public JsonParseException(String err, Object... objs)
	{
		this(String.format(err, objs));
		
	}
	
	public JsonParseException(Throwable e)
	{
		super(e);
		
	}
	
	public JsonParseException(String err, Throwable e)
	{
		super(err, e);
		
	}
	
	public JsonParseException(String err, Throwable e, boolean arg2, boolean arg3)
	{
		super(err, e, arg2, arg3);
		
	}
	
}
