
package com.elusivehawk.util.storage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Array<T> implements IArray<T>
{
	protected final T[] array;
	private boolean immutable = false;
	
	@SuppressWarnings("unqualified-field-access")
	public Array(T[] a)
	{
		array = a;
		
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new ArrayItr<T>(this);
	}
	
	@Override
	public int size()
	{
		return this.array.length;
	}
	
	@Override
	public T get(int i)
	{
		return this.array[i];
	}
	
	@Override
	public IArray<T> set(int i, T obj)
	{
		if (this.isImmutable())
		{
			throw new UnsupportedOperationException();
		}
		
		this.array[i] = obj;
		
		return this;
	}
	
	@Override
	public boolean isImmutable()
	{
		return this.immutable;
	}
	
	@Override
	public Array<T> setImmutable()
	{
		this.immutable = true;
		
		return this;
	}
	
	public List<T> asList()
	{
		return Arrays.asList(this.array);
	}
	
	public static <T> Array<T> create(T[] array)
	{
		return new Array<T>(array);
	}
	
}
