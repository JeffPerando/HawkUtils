
package com.elusivehawk.util.math;

import com.elusivehawk.util.IDirty;
import com.elusivehawk.util.IPopulator;
import com.elusivehawk.util.storage.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public abstract class MathArray<T extends Number> implements IPopulator<Buffer<T>>, IDirty
{
	private boolean dirty = false, immutable = false, sync = false;
	
	@Override
	public boolean isDirty()
	{
		return this.dirty;
	}
	
	@Override
	public synchronized void setIsDirty(boolean b)
	{
		this.dirty = b;
		
	}
	
	@Override
	public void populate(Buffer<T> buf)
	{
		for (int c = 0; c < size(); c++)
		{
			buf.add(get(c));
			
		}
		
	}
	
	public boolean isImmutable()
	{
		return this.immutable;
	}
	
	public MathArray<T> setImmutable()
	{
		this.immutable = true;
		
		return this;
	}
	
	public boolean doSync()
	{
		return this.sync;
	}
	
	public MathArray<T> setSync()
	{
		this.sync = true;
		
		return this;
	}
	
	public MathArray<T> set(int pos, T num)
	{
		return this.set(pos, num, true);
	}
	
	public MathArray<T> setAll(T num)
	{
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, num, false);
			
		}
		
		this.onChanged();
		
		return this;
	}
	
	public MathArray<T> normalize()
	{
		return this.normalize(this);
	}
	
	public Number[] multiget(int bitmask)
	{
		int count = 0;
		
		for (int bits : MathConst.BITMASKS)
		{
			if ((bitmask & bits) != 0)
			{
				count++;
				
			}
			
		}
		
		if (count == 0)
		{
			return new Float[0];
		}
		
		Number[] ret = new Number[count];
		count = 0;
		
		for (int c = 0; c < MathConst.BITMASKS.length; c++)
		{
			if ((bitmask & MathConst.BITMASKS[c]) != 0)
			{
				ret[count++] = this.get(c);
				
			}
			
		}
		
		return ret;
	}
	
	public MathArray<T> set(MathArray<T> obj)
	{
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			this.set(c, obj.get(c), false);
			
		}
		
		this.onChanged();
		
		return this;
	}
	
	public MathArray<T> add(MathArray<T> obj)
	{
		return this.add(obj, this);
	}
	
	public MathArray<T> div(MathArray<T> obj)
	{
		return this.div(obj, this);
	}
	
	public MathArray<T> mul(MathArray<T> obj)
	{
		return this.mul(obj, this);
	}
	
	public MathArray<T> sub(MathArray<T> obj)
	{
		return this.sub(obj, this);
	}
	
	public MathArray<T> cos()
	{
		return this.cos(this);
	}
	
	public MathArray<T> cos(MathArray<T> dest)
	{
		int i = Math.min(this.size(), dest.size());
	
		for (int c = 0; c < i; c++)
		{
			dest.set(c, Math.cos(this.get(c).doubleValue()), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	public MathArray<T> sin()
	{
		return this.sin(this);
	}
	
	public MathArray<T> sin(MathArray<T> dest)
	{
		int i = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < i; c++)
		{
			dest.set(c, Math.sin(this.get(c).doubleValue()), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	public void onChanged(){}
	
	public abstract T get(int index);
	
	public abstract int size();
	
	public abstract MathArray<T> set(int pos, Number num, boolean notify);
	
	public abstract MathArray<T> add(MathArray<T> obj, MathArray<T> dest);
	
	public abstract MathArray<T> div(MathArray<T> obj, MathArray<T> dest);
	
	public abstract MathArray<T> mul(MathArray<T> obj, MathArray<T> dest);
	
	public abstract MathArray<T> sub(MathArray<T> obj, MathArray<T> dest);
	
	public abstract MathArray<T> normalize(MathArray<T> dest);
	
}
