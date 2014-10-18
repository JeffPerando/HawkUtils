
package com.elusivehawk.util.storage;

import java.util.Iterator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IArray<T> extends Iterable<T>
{
	@Override
	default Iterator<T> iterator()
	{
		return new ArrayItr<T>(this);
	}
	
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
