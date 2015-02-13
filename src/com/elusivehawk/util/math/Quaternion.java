
package com.elusivehawk.util.math;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.util.parse.json.IJsonSerializer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Quaternion extends MathArray<Float> implements IJsonSerializer
{
	protected final float[] data = new float[4];
	
	protected List<Listener> listeners = null;
	protected Matrix matrix = MatrixHelper.identity();
	
	public Quaternion()
	{
		setIdentity();
		
	}
	
	public Quaternion(float[] angles)
	{
		int size = Math.min(angles.length, 3);
		
		for (int c = 0; c < size; c++)
		{
			rotateAxis(MathConst.AXES[c], angles[c]);
			
		}
		
	}
	
	public Quaternion(Quaternion q)
	{
		set(q);
		
	}
	
	@Override
	public int size()
	{
		return 4;
	}
	
	@Override
	public Float get(int pos)
	{
		return this.data[pos];
	}
	
	@Override
	public Quaternion set(int pos, Number num, boolean notify)
	{
		assert !this.isImmutable();
		assert MathHelper.bounds(pos, 0, this.size());
		
		if (this.doSync())
		{
			synchronized (this)
			{
				this.data[pos] = num.floatValue();
				
			}
			
		}
		else
		{
			this.data[pos] = num.floatValue();
			
		}
		
		this.setIsDirty(true);
		
		if (notify)
		{
			this.onChanged();
			
		}
		
		return this;
	}
	
	@Override
	public MathArray<Float> normalize(MathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		float f = MathHelper.length(this);
		
		int length = Math.min(this.size(), dest.size());
		
		for (int c = 0; c < length; c++)
		{
			dest.set(c, this.get(c) / f, false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public MathArray<Float> add(MathArray<Float> obj, MathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) + obj.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public MathArray<Float> div(MathArray<Float> obj, MathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) / obj.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public MathArray<Float> sub(MathArray<Float> obj, MathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) - obj.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public MathArray<Float> mul(MathArray<Float> obj, MathArray<Float> dest)
	{
		assert !dest.isImmutable();
		
		int l = Math.min(this.size(), obj.size());
		
		for (int c = 0; c < l; c++)
		{
			dest.set(c, this.get(c) * obj.get(c), false);
			
		}
		
		dest.onChanged();
		
		return dest;
	}
	
	@Override
	public String toJson(int tabs)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Quaternion clone()
	{
		return new Quaternion(this);
	}
	
	@Override
	public void onChanged()
	{
		if (this.listeners != null)
		{
			this.listeners.forEach(((lis) -> {lis.onQuatChanged(this);}));
			
		}
		
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
	
	public Quaternion setIdentity()
	{
		return (Quaternion)this.setAll(0f).set(3, 1f);
	}
	
	public Quaternion conjugate()
	{
		return this.conjugate(this);
	}
	
	public Quaternion conjugate(boolean local)
	{
		return this.conjugate(local ? this : new Quaternion());
	}
	
	public Quaternion conjugate(Quaternion dest)
	{
		for (int c = 0; c < 3; c++)
		{
			dest.set(c, -this.get(c), false);
			
		}
		
		dest.set(4, this.get(4));
		
		return dest;
	}
	
	public Quaternion rotateAxis(Vector axis, float angle)
	{
		return rotateAxis(this, axis, angle);
	}
	
	public Quaternion rotateAxis(Vector axis, float angle, boolean local)
	{
		return rotateAxis(local ? this : new Quaternion(), axis, angle);
	}
	
	public static Quaternion rotateAxis(Quaternion dest, Vector axis, float angle)
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
				dest.set(c, l * axis.get(c) * sin, false);
				
			}
			
		}
		
		dest.set(3, (float)Math.cos(radian));
		
		return (Quaternion)dest.normalize();
	}
	
	public Vector rotateVec(Vector vec)
	{
		return this.rotateVec(vec, !vec.isImmutable());
	}
	
	public Vector rotateVec(Vector vec, boolean local)
	{
		return this.rotateVec(vec, local ? vec : new Vector());
	}
	
	public Vector rotateVec(Vector vec, Vector dest)
	{
		Quaternion conj = this.conjugate(false);
		Quaternion vQuat = (Quaternion)new Quaternion().set(vec).set(3, 1f);
		
		vQuat.mul(this);
		vQuat.mul(conj, dest);
		
		return dest;
	}
	
	public Matrix asMatrix()
	{
		return this.asMatrix(MatrixHelper.identity());
	}
	
	public Matrix asMatrix(Matrix dest)
	{
		if (this.isDirty())
		{
			this.matrix = MatrixHelper.rotate(this, dest);
			
			this.setIsDirty(false);
			
		}
		
		return this.matrix;
	}
	
	public Quaternion into(FloatBuffer buf, int[] order)
	{
		for (int i : order)
		{
			buf.put(this.get(i));
			
		}
		
		return this;
	}
	
	@FunctionalInterface
	public static interface Listener
	{
		void onQuatChanged(Quaternion q);
		
	}
	
}
