
package com.elusivehawk.util.math;

import java.nio.DoubleBuffer;
import com.elusivehawk.util.parse.json.IJsonSerializer;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class DoubleArithmetic extends Arithmetic implements IJsonSerializer
{
	private final double[] data;
	
	public DoubleArithmetic(int size)
	{
		this(new double[size]);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public DoubleArithmetic(double... fs)
	{
		data = fs;
		
	}
	
	public DoubleArithmetic(DoubleBuffer buf)
	{
		this(buf.remaining(), buf);
		
	}
	
	public DoubleArithmetic(int size, DoubleBuffer buf)
	{
		this(size);
		
	}
	
	public DoubleArithmetic(DoubleArithmetic arith)
	{
		this(arith.size());
		
		set(arith);
		setIsDirty(false);
		
	}
	
	@Override
	public final int size()
	{
		return this.data.length;
	}
	
	@Override
	public String toJson(int tabs)
	{
		StringBuilder b = new StringBuilder();
		
		b.append("[");
		
		for (int c = 0; c < this.size(); c++)
		{
			if (c > 0)
			{
				b.append(", ");
				
			}
			
			b.append(this.get(c));
			
		}
		
		b.append("]");
		
		return b.toString();
	}
	
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		
		ret.append("[");
		
		for (int c = 0; c < this.size(); c++)
		{
			if (c > 0)
			{
				ret.append(", ");
				
			}
			
			ret.append(this.get(c));
			
		}
		
		ret.append("]");
		
		return ret.toString();
	}
	
	public double get(int index)
	{
		return this.data[index];
	}
	
	public DoubleArithmetic add(int index, double value)
	{
		return this.add(index, value, this);
	}
	
	public DoubleArithmetic add(int index, double value, DoubleArithmetic dest)
	{
		return dest.set(index, this.get(index) + value);
	}
	
	public DoubleArithmetic add(DoubleArithmetic arith)
	{
		return this.add(arith, this);
	}
	
	public DoubleArithmetic add(DoubleArithmetic arith, DoubleArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.add(c, arith.get(c), dest);
			
		}
		
		return dest;
	}
	
	public DoubleArithmetic addAll(double f)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.add(c, f);
			
		}
		
		return this;
	}
	
	public DoubleArithmetic div(int index, double value)
	{
		return this.div(index, value, this);
	}
	
	public DoubleArithmetic div(int index, double value, DoubleArithmetic dest)
	{
		return dest.set(index, this.get(index) / value);
	}
	
	public DoubleArithmetic div(DoubleArithmetic arith)
	{
		return this.div(arith, this);
	}
	
	public DoubleArithmetic div(DoubleArithmetic arith, DoubleArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.div(c, arith.get(c), dest);
			
		}
		
		return dest;
	}
	
	public DoubleArithmetic divAll(double f)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.div(c, f);
			
		}
		
		return this;
	}
	
	public DoubleArithmetic mul(int index, double value)
	{
		return this.mul(index, value, this);
	}
	
	public DoubleArithmetic mul(int index, double value, DoubleArithmetic dest)
	{
		return dest.set(index, this.get(index) * value);
	}
	
	public DoubleArithmetic mul(DoubleArithmetic arith)
	{
		return this.mul(arith, this);
	}
	
	public DoubleArithmetic mul(DoubleArithmetic arith, DoubleArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.mul(c, arith.get(c), dest);
			
		}
		
		return dest;
	}
	
	public DoubleArithmetic mulAll(double f)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.mul(c, f);
			
		}
		
		return this;
	}
	
	public DoubleArithmetic sub(int index, double value)
	{
		return this.sub(index, value, this);
	}
	
	public DoubleArithmetic sub(int index, double value, DoubleArithmetic dest)
	{
		return dest.set(index, this.get(index) + value);
	}
	
	public DoubleArithmetic sub(DoubleArithmetic arith)
	{
		return this.sub(arith, this);
	}
	
	public DoubleArithmetic sub(DoubleArithmetic arith, DoubleArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.sub(c, arith.get(c), dest);
			
		}
		
		return dest;
	}
	
	public DoubleArithmetic subAll(double f)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.sub(c, f);
			
		}
		
		return this;
	}
	
	public DoubleArithmetic set(int index, double value)
	{
		assert !this.isImmutable();
		
		if (this.data[index] != value)
		{
			if (this.isSync())
			{
				synchronized (this)
				{
					this.data[index] = value;
					
				}
				
			}
			else
			{
				this.data[index] = value;
				
			}
			
			this.setIsDirty(true);
			
		}
		
		return this;
	}
	
	public DoubleArithmetic set(double... fs)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, fs[c]);
			
		}
		
		return this;
	}
	
	public DoubleArithmetic set(DoubleArithmetic arith)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, arith.get(c));
			
		}
		
		return this;
	}
	
	public DoubleArithmetic set(DoubleBuffer buf)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, buf.get());
			
		}
		
		return this;
	}
	
	public DoubleArithmetic setAll(double value)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, value);
			
		}
		
		return this;
	}
	
	public DoubleArithmetic cos()
	{
		return this.cos(this);
	}
	
	public DoubleArithmetic cos(DoubleArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, Math.cos(this.get(c)));
			
		}
		
		return this;
	}
	
	public DoubleArithmetic sin()
	{
		return this.cos(this);
	}
	
	public DoubleArithmetic sin(DoubleArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, Math.sin(this.get(c)));
			
		}
		
		return this;
	}
	
	public DoubleBuffer put(DoubleBuffer buf)
	{
		buf.put(this.data);
		
		return buf;
	}
	
	public DoubleBuffer asBuffer()
	{
		return BufferHelper.makeDoubleBuffer(this.data);
	}
	
}
