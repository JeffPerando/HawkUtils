
package com.elusivehawk.util.json;

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
