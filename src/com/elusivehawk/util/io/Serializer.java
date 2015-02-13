
package com.elusivehawk.util.io;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface Serializer<T>
{
	int toBytes(T obj, IByteWriter w);
	
	T fromBytes(IByteReader r);
	
}
