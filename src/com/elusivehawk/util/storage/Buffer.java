
package com.elusivehawk.util.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.util.Dirtable;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.math.MathHelper;

/**
 * 
 * Buffers are basically {@link List}s with a position and marker.
 * <p>
 * Note that they're designed to emulate {@linkplain java.nio.Buffer NIO buffers}, and that they can be iterated through.
 * 
 * @author Elusivehawk
 */
public class Buffer<T> extends Dirtable implements Collection<T>, Iterator<T>, IGettable<T>
{
	protected final List<T> l;
	protected int pos = 0, mark = 0;
	
	public Buffer()
	{
		this(16);
		
	}
	
	public Buffer(IPopulator<Buffer<T>> pop)
	{
		this();
		
		pop.populate(this);
		
		rewind();
		
	}
	
	public Buffer(int limit)
	{
		this(new ArrayList<T>(limit));
		
	}
	
	public Buffer(int limit, IPopulator<Buffer<T>> pop)
	{
		this(limit);
		
		pop.populate(this);
		
		rewind();
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Buffer(List<T> list)
	{
		l = list;
		
	}
	
	public Buffer(List<T> list, IPopulator<Buffer<T>> pop)
	{
		this(list);
		
		pop.populate(this);
		
		rewind();
		
	}
	
	@Override
	public boolean hasNext()
	{
		return this.pos < this.l.size();
	}
	
	@Override
	public T next()
	{
		return this.next(true);
	}
	
	@Override
	public T get()
	{
		return this.get(this.pos);
	}
	
	public T get(int pos)
	{
		return MathHelper.bounds(pos, 0, this.l.size()) ? this.l.get(pos) : null;
	}
	
	@Override
	public void remove()
	{
		this.l.remove(this.pos);
		
	}
	
	@Override
	public boolean add(T obj)
	{
		if (!this.l.add(obj))
		{
			return false;
		}
		
		this.pos++;
		
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> col)
	{
		for (T obj : col)
		{
			this.add(obj);
			
		}
		
		return true;
	}
	
	@Override
	public int size()
	{
		return this.l.size();
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return this;
	}
	
	@Override
	public void clear()
	{
		while (this.hasNext())
		{
			this.remove();
			
		}
		
	}
	
	@Override
	public boolean contains(Object arg0)
	{
		return this.l.contains(arg0);
	}
	
	@Override
	public boolean containsAll(Collection<?> arg0)
	{
		return this.l.containsAll(arg0);
	}
	
	@Override
	public boolean isEmpty()
	{
		return !this.hasNext();
	}
	
	@Override
	public boolean remove(Object arg0)
	{
		return this.l.remove(arg0);
	}
	
	@Override
	public boolean removeAll(Collection<?> arg0)
	{
		return this.l.removeAll(arg0);
	}
	
	@Override
	public boolean retainAll(Collection<?> arg0)
	{
		return this.l.retainAll(arg0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray()
	{
		return (T[])this.l.toArray();
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return this.l.toArray(arg0);
	}
	
	public T next(boolean next)
	{
		if (!this.hasNext())
		{
			return null;
		}
		
		T ret = this.get(this.pos);
		
		if (next)
		{
			this.pos++;
			
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public Buffer<T> add(T... objs)
	{
		for (T obj : objs)
		{
			this.add(obj);
			
		}
		
		return this;
	}
	
	public int position()
	{
		return this.pos;
	}
	
	public void position(int position)
	{
		this.pos = position;
	}
	
	public void mark()
	{
		this.mark = this.pos;
		
	}
	
	public void norm()
	{
		this.mark = 0;
		
	}
	
	public int remaining()
	{
		return this.l.size() - this.pos;
	}
	
	public void rewind()
	{
		this.pos = this.mark;
		this.mark = 0;
		
	}
	
	public void skip()
	{
		this.skip(1);
		
	}
	
	public void skip(int count)
	{
		this.pos += count;
		
	}
	
	public void rewind(int count)
	{
		this.pos -= count;
		
	}
	
}
