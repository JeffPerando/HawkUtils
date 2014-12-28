
package com.elusivehawk.util.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.elusivehawk.util.IPopulator;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class JsonArray extends JsonValue<List<Object>> implements Iterable<Object>
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
	public List<Object> getValue()
	{
		return this.array;
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
	
	public Object getValue(int i)
	{
		return this.array.get(i);
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
	
}
