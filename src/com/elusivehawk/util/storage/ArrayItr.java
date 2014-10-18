
package com.elusivehawk.util.storage;

import java.util.Iterator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ArrayItr<T> implements Iterator<T>
{
	private final IArray<T> array;
	private int pos = 0;
	
	public ArrayItr(T[] a)
	{
		this(new Array<T>(a));
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ArrayItr(IArray<T> a)
	{
		assert a != null;
		
		array = a;
		
	}
	
	@Override
	public boolean hasNext()
	{
		return this.pos < this.array.size();
	}
	
	@Override
	public T next()
	{
		return this.array.get(this.pos++);
	}
	
}