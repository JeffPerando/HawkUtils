
package com.elusivehawk.util.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class QuaternionD extends DoubleArithmetic
{
	private List<Listener> listeners = null;
	private MatrixD matrix = new MatrixD();
	
	public QuaternionD()
	{
		super(4);
		
		setIdentity();
		
	}
	
	public QuaternionD(double... angles)
	{
		this();
		
		for (int c = 0; c < size(); c++)
		{
			rotateAxis(MathConst.AXES_D[c], angles[c]);
			
		}
		
	}
	
	public QuaternionD(QuaternionD q)
	{
		this();
		
		set(q);
		
	}
	
	@Override
	public QuaternionD clone()
	{
		return new QuaternionD(this);
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
	
	public QuaternionD setIdentity()
	{
		return (QuaternionD)this.setAll(0f).set(3, 1f);
	}
	
	public QuaternionD conjugate()
	{
		return this.conjugate(this);
	}
	
	public QuaternionD conjugate(boolean local)
	{
		return this.conjugate(local ? this : new QuaternionD());
	}
	
	public QuaternionD conjugate(QuaternionD dest)
	{
		for (int c = 0; c < 3; c++)
		{
			dest.set(c, -this.get(c));
			
		}
		
		dest.set(3, this.get(3));
		
		return dest;
	}
	
	public QuaternionD normalize()
	{
		return this.normalize(this);
	}
	
	public QuaternionD normalize(QuaternionD dest)
	{
		assert !dest.isImmutable();
		
		double f = MathHelper.length(this);
		
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, this.get(c) / f);
			
		}
		
		return dest;
	}
	
	public QuaternionD rotateAxis(VectorD axis, double angle)
	{
		return rotateAxis(axis, angle, this);
	}
	
	public QuaternionD rotateAxis(VectorD axis, double angle, boolean local)
	{
		return rotateAxis(axis, angle, local ? this : new QuaternionD());
	}
	
	public static QuaternionD rotateAxis(VectorD axis, double angle, QuaternionD dest)
	{
		double l = axis.length();
		
		if (l == 0)
		{
			return dest.setIdentity();
		}
		
		l = 1 / l;
		
		double radian = MathHelper.toRadians(angle / 2);
		double sin = Math.sin(radian);
		
		for (int c = 0; c < 3; c++)
		{
			if (axis.get(c) > 0)
			{
				dest.set(c, l * axis.get(c) * sin);
				
			}
			
		}
		
		dest.set(3, Math.cos(radian));
		
		return dest.normalize();
	}
	
	public VectorD rotateVec(VectorD vec)
	{
		return this.rotateVec(vec, !vec.isImmutable());
	}
	
	public VectorD rotateVec(VectorD vec, boolean local)
	{
		return this.rotateVec(vec, local ? vec : new VectorD());
	}
	
	public VectorD rotateVec(VectorD vec, VectorD dest)
	{
		QuaternionD conj = this.conjugate(false);
		QuaternionD vQuat = (QuaternionD)new QuaternionD().set(vec).set(3, 1f);
		
		vQuat.mul(this);
		vQuat.mul(conj, dest);
		
		return dest;
	}
	
	public MatrixD asMatrix()
	{
		if (this.isDirty())
		{
			this.matrix = new MatrixD().rotate(this);
			
			this.setIsDirty(false);
			
		}
		
		return this.matrix;
	}
	
	public QuaternionF toQuatF()
	{
		QuaternionF ret = new QuaternionF(this.size());
		
		for (int c = 0; c < this.size(); c++)
		{
			ret.set(c, (float)this.get(c));
			
		}
		
		return ret;
	}
	
	@FunctionalInterface
	public static interface Listener
	{
		void onQuatChanged(QuaternionD q);
		
	}
	
}
