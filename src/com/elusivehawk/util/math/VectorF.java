
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
public class VectorF extends FloatArithmetic
{
	private List<Listener> listeners = null;
	
	public VectorF()
	{
		this(3);
		
	}
	
	public VectorF(int length)
	{
		super(length);
		
	}
	
	public VectorF(float... info)
	{
		super(info);
		
	}
	
	public VectorF(VectorF vec)
	{
		super(vec);
		
	}
	
	@SuppressWarnings("boxing")
	public VectorF(JsonArray json)
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
	public VectorF clone()
	{
		return new VectorF(this);
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
		if (!(obj instanceof VectorF))
		{
			return false;
		}
		
		VectorF vec = (VectorF)obj;
		
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
	
	public VectorF cross(VectorF other)
	{
		this.cross(other, this);
		
		return this;
	}
	
	public void cross(VectorF other, VectorF dest)
	{
		dest.set(MathHelper.cross(this, other));
		
	}
	
	public float dot(VectorF other)
	{
		return MathHelper.dot(this, other);
	}
	
	public float length()
	{
		return MathHelper.length(this);
	}
	
	public VectorF scale(float f, VectorF dest)
	{
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, this.get(c) * f);
			
		}
		
		return dest;
	}
	
	public VectorF absolute()
	{
		return this.absolute(this);
	}
	
	public VectorF absolute(VectorF dest)
	{
		int i = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < i; c++)
		{
			dest.set(c, Math.abs(this.get(c)));
			
		}
		
		return dest;
	}
	
	public VectorF negate()
	{
		return this.negate(this);
	}
	
	public VectorF negate(boolean local)
	{
		return this.negate(local ? this : new VectorF(this.size()));
	}
	
	public VectorF negate(VectorF dest)
	{
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, -this.get(c));
			
		}
		
		return dest;
	}
	
	public VectorF normalize()
	{
		return this.normalize(this);
	}
	
	public VectorF normalize(VectorF dest)
	{
		assert !dest.isImmutable();
		
		float f = MathHelper.length(this);
		
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, dest.get(c) / f);
			
		}
		
		return dest;
	}
	
	public VectorF scaleAdd(float f, VectorF vec, VectorF dest)//FIXME
	{
		return dest;
	}
	
	public VectorF add(FloatArithmetic obj, boolean local)
	{
		return (VectorF)this.add(obj, local ? this : new VectorF(this.size()));
	}
	
	public VectorF div(FloatArithmetic obj, boolean local)
	{
		return (VectorF)this.div(obj, local ? this : new VectorF(this.size()));
	}
	
	public VectorF mul(FloatArithmetic obj, boolean local)
	{
		return (VectorF)this.mul(obj, local ? this : new VectorF(this.size()));
	}
	
	public VectorF sub(FloatArithmetic obj, boolean local)
	{
		return (VectorF)this.sub(obj, local ? this : new VectorF(this.size()));
	}
	
	public VectorD toVecD()
	{
		VectorD ret = new VectorD(this.size());
		
		for (int c = 0; c < this.size(); c++)
		{
			ret.set(c, this.get(c));
			
		}
		
		ret.setIsDirty(false);
		
		return ret;
	}
	
	@FunctionalInterface
	public static interface Listener
	{
		void onVecChanged(VectorF vec);
		
	}
	
}
