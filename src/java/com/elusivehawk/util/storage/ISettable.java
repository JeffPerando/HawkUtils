
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface ISettable<T>
{
	boolean set(T obj);
	
}
