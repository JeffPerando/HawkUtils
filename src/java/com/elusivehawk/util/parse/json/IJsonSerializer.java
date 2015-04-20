
package com.elusivehawk.util.parse.json;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IJsonSerializer
{
	String toJson(int tabs);
	
	default String toJson()
	{
		return this.toJson(0);
	}
	
}
