
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IArray<T>
{
	int size();
	
	T get(int i);
	
	IArray<? extends T> set(int i, T obj);
	
	default IArray<? extends T> setImmutable()
	{
		return this;
	};
	
	default boolean isImmutable()
	{
		return false;
	}
	
}
