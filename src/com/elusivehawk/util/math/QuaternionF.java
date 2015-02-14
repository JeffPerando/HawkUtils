
package com.elusivehawk.util.math;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.util.parse.json.IJsonSerializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class QuaternionF extends FloatArithmetic implements IJsonSerializer
{
	protected List<Listener> listeners = null;
	protected MatrixF matrix = MatrixHelper.identity();
	
	public QuaternionF()
	{
		super(4);
		
		setIdentity();
		
	}
	
	public QuaternionF(float[] angles)
	{
		this();
		
		int size = Math.min(angles.length, 3);
		
		for (int c = 0; c < size; c++)
		{
			rotateAxis(MathConst.AXES[c], angles[c]);
			
		}
		
	}
	
	public QuaternionF(QuaternionF q)
	{
		this();
		
		set(q);
		
	}
	
	public FloatArithmetic normalize(FloatArithmetic dest)
	{
		assert !dest.isImmutable();
		
		float f = MathHelper.length(this);
		
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, this.get(c) / f);
			
		}
		
		return dest;
	}
	
	@Override
	public String toJson(int tabs)
	{
		// TODO Auto-generated method stub
		return null;
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
		
		dest.set(4, this.get(4));
		
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
		
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, dest.get(c) / f);
			
		}
		
		return dest;
	}
	
	public QuaternionF rotateAxis(VectorF axis, float angle)
	{
		return rotateAxis(this, axis, angle);
	}
	
	public QuaternionF rotateAxis(VectorF axis, float angle, boolean local)
	{
		return rotateAxis(local ? this : new QuaternionF(), axis, angle);
	}
	
	public static QuaternionF rotateAxis(QuaternionF dest, VectorF axis, float angle)
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
		return this.asMatrix(MatrixHelper.identity());
	}
	
	public MatrixF asMatrix(MatrixF dest)
	{
		if (this.isDirty())
		{
			this.matrix = MatrixHelper.rotate(this, dest);
			
			this.setIsDirty(false);
			
		}
		
		return this.matrix;
	}
	
	@FunctionalInterface
	public static interface Listener
	{
		void onQuatChanged(QuaternionF q);
		
	}
	
}
