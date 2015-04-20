
package com.elusivehawk.util.math;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.util.parse.json.JsonArray;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class VectorD extends DoubleArithmetic
{
	private List<Listener> listeners = null;
	
	public VectorD()
	{
		this(3);
		
	}
	
	public VectorD(int length)
	{
		super(length);
		
	}
	
	public VectorD(double... info)
	{
		super(info);
		
	}
	
	public VectorD(VectorD vec)
	{
		super(vec);
		
	}
	
	@SuppressWarnings("boxing")
	public VectorD(JsonArray json)
	{
		this(json.length());
		
		for (int c = 0; c < size(); c++)
		{
			Object obj = json.getValue(c);
			
			if (!(obj instanceof Number))
			{
				throw new RuntimeException(String.format("Incompatible argument in array object %s: %s", c, obj));
			}
			
			set(c, ((Number)obj).floatValue());
			
		}
		
	}
	
	@Override
	public VectorD clone()
	{
		return new VectorD(this);
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder(1 + (this.size() * 2));
		
		b.append("vector:[");
		
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
	public boolean equals(Object obj)
	{
		if (!(obj instanceof VectorD))
		{
			return false;
		}
		
		VectorD vec = (VectorD)obj;
		
		if (vec.size() != this.size())
		{
			return false;
		}
		
		for (int c = 0; c < this.size(); c++)
		{
			if (vec.get(c) != this.get(c))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public void addListener(Listener lis)
	{
		assert lis != null;
		
		if (this.listeners == null)
		{
			this.listeners = new ArrayList<Listener>();
			
		}
		
		this.listeners.add(lis);
		
	}
	
	public void removeListener(Listener lis)
	{
		if (this.listeners != null)
		{
			this.listeners.remove(lis);
			
		}
		
	}
	
	public void onChanged()
	{
		if (this.listeners != null)
		{
			this.listeners.forEach(((lis) -> {lis.onVecChanged(this);}));
			
		}
		
	}
	
	public VectorD cross(VectorD other)
	{
		this.cross(other, this);
		
		return this;
	}
	
	public void cross(VectorD other, VectorD dest)
	{
		dest.set(MathHelper.cross(this, other));
		
	}
	
	public double dot(VectorD other)
	{
		return MathHelper.dot(this, other);
	}
	
	public double length()
	{
		return MathHelper.length(this);
	}
	
	public VectorD scale(double f, VectorD dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, this.get(c) * f);
			
		}
		
		return dest;
	}
	
	public VectorD absolute()
	{
		return this.absolute(this);
	}
	
	public VectorD absolute(VectorD dest)
	{
		int i = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < i; c++)
		{
			dest.set(c, Math.abs(this.get(c)));
			
		}
		
		return dest;
	}
	
	public VectorD negate()
	{
		return this.negate(this);
	}
	
	public VectorD negate(boolean local)
	{
		return this.negate(local ? this : new VectorD(this.size()));
	}
	
	public VectorD negate(VectorD dest)
	{
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, -this.get(c));
			
		}
		
		return dest;
	}
	
	public VectorD normalize()
	{
		return this.normalize(this);
	}
	
	public VectorD normalize(VectorD dest)
	{
		assert !dest.isImmutable();
		
		double f = MathHelper.length(this);
		
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, dest.get(c) / f);
			
		}
		
		return dest;
	}
	
	public VectorD scaleAdd(double f, VectorD vec, VectorD dest)//FIXME
	{
		return dest;
	}
	
	public VectorD add(DoubleArithmetic obj, boolean local)
	{
		return (VectorD)this.add(obj, local ? this : new VectorD(this.size()));
	}
	
	public VectorD div(DoubleArithmetic obj, boolean local)
	{
		return (VectorD)this.div(obj, local ? this : new VectorD(this.size()));
	}
	
	public VectorD mul(DoubleArithmetic obj, boolean local)
	{
		return (VectorD)this.mul(obj, local ? this : new VectorD(this.size()));
	}
	
	public VectorD sub(DoubleArithmetic obj, boolean local)
	{
		return (VectorD)this.sub(obj, local ? this : new VectorD(this.size()));
	}
	
	public VectorF toVecF()
	{
		VectorF ret = new VectorF(this.size());
		
		for (int c = 0; c < this.size(); c++)
		{
			ret.set(c, (float)this.get(c));
			
		}
		
		ret.setIsDirty(false);
		
		return ret;
	}
	
	@FunctionalInterface
	public static interface Listener
	{
		void onVecChanged(VectorD vec);
		
	}
	
}
