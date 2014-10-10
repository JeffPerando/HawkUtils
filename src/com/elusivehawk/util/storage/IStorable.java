
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IStorable<T>
{
	void store(Buffer<T> buf);
	
}
