
package com.elusivehawk.util.io;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface Serializer<T>
{
	int toBytes(IByteWriter w, T obj);
	
	T fromBytes(IByteReader r);
	
}
