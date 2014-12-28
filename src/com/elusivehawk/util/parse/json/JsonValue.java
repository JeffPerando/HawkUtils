
package com.elusivehawk.util.parse.json;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class JsonValue<T> implements IJsonSerializer
{
	@Override
	public String toString()
	{
		return this.toJson(0);
	}
	
	public abstract T getValue();
	
}
