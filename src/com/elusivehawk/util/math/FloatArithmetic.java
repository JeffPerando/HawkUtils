
package com.elusivehawk.util.math;

import java.nio.FloatBuffer;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class FloatArithmetic extends Arithmetic
{
	private final float[] data;
	
	public FloatArithmetic(int size)
	{
		this(new float[size]);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public FloatArithmetic(float... fs)
	{
		data = fs;
		
	}
	
	public FloatArithmetic(FloatBuffer buf)
	{
		this(buf.remaining(), buf);
		
	}
	
	public FloatArithmetic(int size, FloatBuffer buf)
	{
		this(size);
		
	}
	
	public FloatArithmetic(FloatArithmetic arith)
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
	
	public float get(int index)
	{
		return this.data[index];
	}
	
	public FloatArithmetic add(int index, float value)
	{
		return this.add(index, value, this);
	}
	
	public FloatArithmetic add(int index, float value, FloatArithmetic dest)
	{
		return dest.set(index, this.get(index) + value);
	}
	
	public FloatArithmetic add(FloatArithmetic arith)
	{
		return this.add(arith, this);
	}
	
	public FloatArithmetic add(FloatArithmetic arith, FloatArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.add(c, arith.get(c), dest);
			
		}
		
		return dest;
	}
	
	public FloatArithmetic addAll(float f)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.add(c, f);
			
		}
		
		return this;
	}
	
	public FloatArithmetic div(int index, float value)
	{
		return this.div(index, value, this);
	}
	
	public FloatArithmetic div(int index, float value, FloatArithmetic dest)
	{
		return dest.set(index, this.get(index) / value);
	}
	
	public FloatArithmetic div(FloatArithmetic arith)
	{
		return this.div(arith, this);
	}
	
	public FloatArithmetic div(FloatArithmetic arith, FloatArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.div(c, arith.get(c), dest);
			
		}
		
		return dest;
	}
	
	public FloatArithmetic divAll(float f)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.div(c, f);
			
		}
		
		return this;
	}
	
	public FloatArithmetic mul(int index, float value)
	{
		return this.mul(index, value, this);
	}
	
	public FloatArithmetic mul(int index, float value, FloatArithmetic dest)
	{
		return dest.set(index, this.get(index) * value);
	}
	
	public FloatArithmetic mul(FloatArithmetic arith)
	{
		return this.mul(arith, this);
	}
	
	public FloatArithmetic mul(FloatArithmetic arith, FloatArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.mul(c, arith.get(c), dest);
			
		}
		
		return dest;
	}
	
	public FloatArithmetic mulAll(float f)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.mul(c, f);
			
		}
		
		return this;
	}
	
	public FloatArithmetic sub(int index, float value)
	{
		return this.sub(index, value, this);
	}
	
	public FloatArithmetic sub(int index, float value, FloatArithmetic dest)
	{
		return dest.set(index, this.get(index) + value);
	}
	
	public FloatArithmetic sub(FloatArithmetic arith)
	{
		return this.sub(arith, this);
	}
	
	public FloatArithmetic sub(FloatArithmetic arith, FloatArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.sub(c, arith.get(c), dest);
			
		}
		
		return dest;
	}
	
	public FloatArithmetic subAll(float f)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.sub(c, f);
			
		}
		
		return this;
	}
	
	public FloatArithmetic set(int index, float value)
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
	
	public FloatArithmetic set(float... fs)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, fs[c]);
			
		}
		
		return this;
	}
	
	public FloatArithmetic set(FloatArithmetic arith)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, arith.get(c));
			
		}
		
		return this;
	}
	
	public FloatArithmetic set(FloatBuffer buf)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, buf.get());
			
		}
		
		return this;
	}
	
	public FloatArithmetic setAll(float value)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, value);
			
		}
		
		return this;
	}
	
	public FloatArithmetic cos()
	{
		return this.cos(this);
	}
	
	public FloatArithmetic cos(FloatArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, (float)Math.cos(this.get(c)));
			
		}
		
		return this;
	}
	
	public FloatArithmetic sin()
	{
		return this.cos(this);
	}
	
	public FloatArithmetic sin(FloatArithmetic dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, (float)Math.sin(this.get(c)));
			
		}
		
		return this;
	}
	
	public FloatBuffer put(FloatBuffer buf)
	{
		buf.put(this.data);
		
		return buf;
	}
	
	public FloatBuffer asBuffer()
	{
		return BufferHelper.makeFloatBuffer(this.data);
	}
	
}
