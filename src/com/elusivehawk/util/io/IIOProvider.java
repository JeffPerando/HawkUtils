
package com.elusivehawk.util.io;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IIOProvider
{
	IByteReader getIn(String name);
	
	default IByteWriter getOut(String name)
	{
		return null;
	}
	
}
