
package com.elusivehawk.util.parse.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonObject implements IJsonSerializer
{
	private final Map<String, Object> objs = new HashMap<String, Object>();
	
	public JsonObject(){}
	
	public JsonObject(IPopulator<JsonObject> pop)
	{
		pop.populate(this);
		
	}
	
	@Override
	public String toJson(int tabs)
	{
		StringBuilder b = new StringBuilder();
		
		for (int c = 0; c < tabs; c++)
		{
			b.append("\t");
			
		}
		
		tabs++;
		
		b.append("{");
		
		boolean prev = false;
		
		for (Entry<String, Object> entry : this.objs.entrySet())
		{
			if (prev)
			{
				b.append(", \n");
				
			}
			else prev = true;
			
			for (int c = 0; c < tabs; c++)
			{
				b.append("\t");
				
			}
			
			Object obj = entry.getValue();
			
			b.append(String.format("\"%s\": %s", entry.getKey(), obj instanceof IJsonSerializer ? ((IJsonSerializer)obj).toJson(tabs) : obj instanceof String ? String.format("\"%s\"", obj) : obj));
			
		}
		
		b.append("\n}");
		
		return b.toString();
	}
	
	public boolean getBool(String name)
	{
		return this.getBool(name, false);
	}
	
	public boolean getBool(String name, boolean dflt)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Boolean) ? ((Boolean)v).booleanValue() : dflt;
	}
	
	public byte getByte(String name)
	{
		return this.getByte(name, (byte)0);
	}
	
	public byte getByte(String name, byte dflt)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).byteValue() : dflt;
	}
	
	public double getDouble(String name)
	{
		return this.getDouble(name, 0d);
	}
	
	public double getDouble(String name, double dflt)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).doubleValue() : dflt;
	}
	
	public float getFloat(String name)
	{
		return this.getFloat(name, 0f);
	}
	
	public float getFloat(String name, float dflt)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).floatValue() : dflt;
	}
	
	public int getInt(String name)
	{
		return this.getInt(name, 0);
	}
	
	public int getInt(String name, int dflt)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).intValue() : dflt;
	}
	
	public long getLong(String name)
	{
		return this.getLong(name, 0L);
	}
	
	public long getLong(String name, long dflt)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).longValue() : dflt;
	}
	
	public short getShort(String name)
	{
		return this.getShort(name, (short)0);
	}
	
	public short getShort(String name, short dflt)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).shortValue() : dflt;
	}
	
	public Object getValue(String name)
	{
		return this.objs.get(name);
	}
	
	public <T> T getValue(String name, Class<T> clazz)
	{
		return this.getValue(name, clazz, null);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(String name, Class<T> clazz, T dflt)
	{
		Object obj = this.getValue(name);
		
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
	
	public boolean add(String name, Object data)
	{
		if (this.objs.containsKey(name))
		{
			return false;
		}
		
		this.objs.put(name, data);
		
		return true;
	}
	
	public boolean hasKey(String name)
	{
		return this.objs.containsKey(name);
	}
	
}
