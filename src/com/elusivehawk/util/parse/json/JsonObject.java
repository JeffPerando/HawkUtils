
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
		Object v = this.getValue(name);
		
		return (v instanceof Boolean) ? ((Boolean)v).booleanValue() : false;
	}
	
	public byte getByte(String name)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).byteValue() : 0;
	}
	
	public double getDouble(String name)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).doubleValue() : 0;
	}
	
	public float getFloat(String name)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).floatValue() : 0;
	}
	
	public int getInt(String name)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).intValue() : 0;
	}
	
	public long getLong(String name)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).longValue() : 0;
	}
	
	public short getShort(String name)
	{
		Object v = this.getValue(name);
		
		return (v instanceof Number) ? ((Number)v).shortValue() : 0;
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
