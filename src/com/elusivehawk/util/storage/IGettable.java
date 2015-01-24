
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IGettable<T>
{
	T get();
	
	default boolean isNull()
	{
		return this.get() == null;
	}
	
}
