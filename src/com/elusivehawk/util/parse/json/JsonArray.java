
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
		return this.getBool(i, false);
	}
	
	public boolean getBool(int i, boolean dflt)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Boolean) ? ((Boolean)v).booleanValue() : dflt;
	}
	
	public byte getByte(int i)
	{
		return this.getByte(i, (byte)0);
	}
	
	public byte getByte(int i, byte dflt)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).byteValue() : dflt;
	}
	
	public double getDouble(int i)
	{
		return this.getDouble(i, 0d);
	}
	
	public double getDouble(int i, double dflt)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).doubleValue() : dflt;
	}
	
	public float getFloat(int i)
	{
		return this.getFloat(i, 0f);
	}
	
	public float getFloat(int i, float dflt)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).floatValue() : dflt;
	}
	
	public int getInt(int i)
	{
		return this.getInt(i, 0);
	}
	
	public int getInt(int i, int dflt)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).intValue() : dflt;
	}
	
	public long getLong(int i)
	{
		return this.getLong(i, 0L);
	}
	
	public long getLong(int i, long dflt)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).longValue() : dflt;
	}
	
	public short getShort(int i)
	{
		return this.getShort(i, (short)0);
	}
	
	public short getShort(int i, short dflt)
	{
		Object v = this.getValue(i);
		
		return (v instanceof Number) ? ((Number)v).shortValue() : dflt;
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
