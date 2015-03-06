
package com.elusivehawk.util.parse.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonArray implements IJsonSerializer, Iterable<Object>
{
	private final List<Object> array = new ArrayList<Object>();
	
	public JsonArray(){}
	
	public JsonArray(IPopulator<JsonArray> pop)
	{
		pop.populate(this);
		
	}
	
	@Override
	public Iterator<Object> iterator()
	{
		return this.array.iterator();
	}
	
	@Override
	public String toJson(int tabs)
	{
		StringBuilder b = new StringBuilder();
		
		b.append("[");
		
		for (int c = 0; c < this.array.size(); c++)
		{
			if (c > 0)
			{
				b.append(", ");
				
			}
			
			Object obj = this.array.get(c);
			
			if (obj instanceof IJsonSerializer)
			{
				b.append(((IJsonSerializer)obj).toJson(tabs + 1));
				
			}
			else
			{
				b.append(obj);
				
			}
			
		}
		
		b.append("]");
		
		return b.toString();
	}
	
	public int length()
	{
		return this.array.size();
	}
	
	public boolean getBool(int i)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Boolean) ? ((Boolean)v).booleanValue() : false;
	}
	
	public byte getByte(int i)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).byteValue() : 0;
	}
	
	public double getDouble(int i)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).doubleValue() : 0;
	}
	
	public float getFloat(int i)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).floatValue() : 0;
	}
	
	public int getInt(int i)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).intValue() : 0;
	}
	
	public long getLong(int i)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).longValue() : 0;
	}
	
	public short getShort(int i)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).shortValue() : 0;
	}
	
	public Object getValue(int i)
	{
		return this.array.get(i);
	}
	
	public <T> T getValue(int i, Class<T> clazz)
	{
		return this.getValue(i, clazz, null);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(int i, Class<T> clazz, T dflt)
	{
		Object obj = this.getValue(i);
		
		if (obj == null)
		{
			return dflt;
		}
		
		if (clazz.isInstance(obj))
		{
			return (T)obj;
		}
		
		try
		{
			T ret = clazz.getConstructor(obj.getClass()).newInstance(obj);
			
			if (ret == null)
			{
				return dflt;
			}
			
			return ret;
		}
		catch (Throwable e)
		{
			Logger.err(e);
			
		}
		
		throw new ClassCastException(String.format("Cannot convert %s to %s", obj.getClass(), clazz));
	}
	
	public void add(Object obj)
	{
		this.array.add(obj);
		
	}
	
	public void addAll(Object... objs)
	{
		for (Object obj : objs)
		{
			this.array.add(obj);
			
		}
		
	}
	
	public <T> T[] toArray(T[] ret, Class<T> clazz)
	{
		assert ret.length != this.length();
		
		for (int c = 0; c < this.length(); c++)
		{
			ret[c] = this.getValue(c, clazz);
			
		}
		
		return ret;
	}
	
}
