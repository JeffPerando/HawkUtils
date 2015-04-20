
package com.elusivehawk.util.storage;

import java.util.Iterator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ArrayIterator<T> implements Iterator<T>
{
	private final T[] array;
	private final int size;
	
	private int pos = 0;
	
	public ArrayIterator(T[] objs)
	{
		this(objs, objs.length);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ArrayIterator(T[] objs, int length)
	{
		assert length <= objs.length;
		
		array = objs;
		size = length;
		
	}
	
	@Override
	public boolean hasNext()
	{
		return this.pos < this.size;
	}
	
	@Override
	public T next()
	{
		return this.array[this.pos++];
	}
	
}
