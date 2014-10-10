
package com.elusivehawk.util;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IObjFilter<T>
{
	T filter(int index, T obj);
	
}
