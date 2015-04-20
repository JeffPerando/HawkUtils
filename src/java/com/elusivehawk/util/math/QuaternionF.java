
package com.elusivehawk.util.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class QuaternionF extends FloatArithmetic
{
	private List<Listener> listeners = null;
	private MatrixF matrix = new MatrixF();
	
	public QuaternionF()
	{
		super(4);
		
		setIdentity();
		
	}
	
	public QuaternionF(float... angles)
	{
		this();
		
		for (int c = 0; c < size(); c++)
		{
			rotateAxis(MathConst.AXES_F[c], angles[c]);
			
		}
		
	}
	
	public QuaternionF(QuaternionF q)
	{
		this();
		
		set(q);
		
	}
	
	@Override
	public QuaternionF clone()
	{
		return new QuaternionF(this);
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
			this.listeners.forEach(((lis) -> {lis.onQuatChanged(this);}));
			
		}
		
	}
	
	public QuaternionF setIdentity()
	{
		return (QuaternionF)this.setAll(0f).set(3, 1f);
	}
	
	public QuaternionF conjugate()
	{
		return this.conjugate(this);
	}
	
	public QuaternionF conjugate(boolean local)
	{
		return this.conjugate(local ? this : new QuaternionF());
	}
	
	public QuaternionF conjugate(QuaternionF dest)
	{
		for (int c = 0; c < 3; c++)
		{
			dest.set(c, -this.get(c));
			
		}
		
		dest.set(3, this.get(3));
		
		return dest;
	}
	
	public QuaternionF normalize()
	{
		return this.normalize(this);
	}
	
	public QuaternionF normalize(QuaternionF dest)
	{
		assert !dest.isImmutable();
		
		float f = MathHelper.length(this);
		
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, this.get(c) / f);
			
		}
		
		return dest;
	}
	
	public QuaternionF rotateAxis(VectorF axis, float angle)
	{
		return rotateAxis(axis, angle, this);
	}
	
	public QuaternionF rotateAxis(VectorF axis, float angle, boolean local)
	{
		return rotateAxis(axis, angle, local ? this : new QuaternionF());
	}
	
	public static QuaternionF rotateAxis(VectorF axis, float angle, QuaternionF dest)
	{
		float l = axis.length();
		
		if (l == 0)
		{
			return dest.setIdentity();
		}
		
		l = 1 / l;
		
		float radian = MathHelper.toRadians(angle / 2);
		float sin = (float)Math.sin(radian);
		
		for (int c = 0; c < 3; c++)
		{
			if (axis.get(c) > 0)
			{
				dest.set(c, l * axis.get(c) * sin);
				
			}
			
		}
		
		dest.set(3, (float)Math.cos(radian));
		
		return dest.normalize();
	}
	
	public VectorF rotateVec(VectorF vec)
	{
		return this.rotateVec(vec, !vec.isImmutable());
	}
	
	public VectorF rotateVec(VectorF vec, boolean local)
	{
		return this.rotateVec(vec, local ? vec : new VectorF());
	}
	
	public VectorF rotateVec(VectorF vec, VectorF dest)
	{
		QuaternionF conj = this.conjugate(false);
		QuaternionF vQuat = (QuaternionF)new QuaternionF().set(vec).set(3, 1f);
		
		vQuat.mul(this);
		vQuat.mul(conj, dest);
		
		return dest;
	}
	
	public MatrixF asMatrix()
	{
		if (this.isDirty())
		{
			this.matrix = new MatrixF().rotate(this);
			
			this.setIsDirty(false);
			
		}
		
		return this.matrix;
	}
	
	public QuaternionD toQuatF()
	{
		QuaternionD ret = new QuaternionD(this.size());
		
		for (int c = 0; c < this.size(); c++)
		{
			ret.set(c, this.get(c));
			
		}
		
		return ret;
	}
	
	@FunctionalInterface
	public static interface Listener
	{
		void onQuatChanged(QuaternionF q);
		
	}
	
}
