
package com.elusivehawk.util.math;

import static com.elusivehawk.util.math.MathConst.W;
import static com.elusivehawk.util.math.MathConst.X;
import static com.elusivehawk.util.math.MathConst.Y;
import static com.elusivehawk.util.math.MathConst.Z;
import static com.elusivehawk.util.math.MathHelper.square;
import static com.elusivehawk.util.math.MathHelper.toRadians;
import java.nio.FloatBuffer;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MatrixF extends FloatArithmetic
{
	private final int sizeSqrt;
	
	public MatrixF()
	{
		this(4);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MatrixF(int s)
	{
		super(s * s);
		
		sizeSqrt = s;
		
		setIdentity();
		
	}
	
	public MatrixF(float... info)
	{
		this((int)Math.sqrt(info.length));
		
		set(info);
		
	}
	
	public MatrixF(int size, FloatBuffer buf)
	{
		this(size);
		
		set(buf);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MatrixF(MatrixF m)
	{
		super(m);
		
		sizeSqrt = m.sizeSqrt;
		
	}
	
	public int sizeSqrt()
	{
		return this.sizeSqrt;
	}
	
	public float get(int x, int y)
	{
		int index = (y + (x * this.sizeSqrt));
		
		Logger.debug("Setting. X: %s, Y: %s, Index: %s", x, y, index);
		
		return this.get(index);
	}
	
	public MatrixF set(int x, int y, float f)
	{
		int index = (y + (x * this.sizeSqrt));
		
		Logger.debug("Getting. X: %s, Y: %s, Index: %s", x, y, index);
		
		return (MatrixF)this.set(y + (x * this.sizeSqrt), f);
	}
	
	public MatrixF setRow(int r, float... fs)
	{
		int i = Math.min(this.sizeSqrt, fs.length);
		
		for (int c = 0; c < i; c++)
		{
			this.set(c, r, fs[c]);
			
		}
		
		return this;
	}
	
	public MatrixF setRow(int r, VectorF vec)
	{
		return this.setRow(r, vec, true);
	}
	
	public MatrixF setRow(int r, VectorF vec, boolean local)
	{
		MatrixF ret = local ? this : new MatrixF(this);
		
		int i = Math.min(ret.sizeSqrt, vec.size());
		
		for (int c = 0; c < i; c++)
		{
			ret.set(c, r, vec.get(c));
			
		}
		
		return ret;
	}
	
	public MatrixF setIdentity()
	{
		for (int c = 0; c < this.sizeSqrt; c++)
		{
			this.set(c, c, 1f);
			
		}
		
		return this;
	}
	
	public MatrixF resetIdentity()
	{
		for (int x = 0; x < this.sizeSqrt; x++)
		{
			for (int y = 0; y < this.sizeSqrt; y++)
			{
				float f = x == y ? 0f : 1f;
				
				if (this.get(x, y) != f)
				{
					this.set(x, y, f);
					
				}
				
			}
			
		}
		
		return this;
	}
	
	public MatrixF add(int x, int y, float f)
	{
		return this.add(x, y, f, this);
	}
	
	public MatrixF add(int x, int y, float f, boolean local)
	{
		return this.add(x, y, f, local ? this : new MatrixF(this));
	}
	
	public MatrixF add(int x, int y, float f, MatrixF dest)
	{
		dest.set(x, y, this.get(x, y) + f);
		
		return dest;
	}
	
	public MatrixF sub(int x, int y, float f)
	{
		return this.sub(x, y, f, this);
	}
	
	public MatrixF sub(int x, int y, float f, boolean local)
	{
		return this.sub(x, y, f, local ? this : new MatrixF(this));
	}
	
	public MatrixF sub(int x, int y, float f, MatrixF dest)
	{
		dest.set(x, y, this.get(x, y) - f);
		
		return dest;
	}
	
	public MatrixF div(int x, int y, float f)
	{
		return this.div(x, y, f, this);
	}
	
	public MatrixF div(int x, int y, float f, boolean local)
	{
		return this.div(x, y, f, local ? this : new MatrixF(this));
	}
	
	public MatrixF div(int x, int y, float f, MatrixF dest)
	{
		dest.set(x, y, this.get(x, y) / f);
		
		return dest;
	}
	
	public MatrixF mul(int x, int y, float f)
	{
		return this.mul(x, y, f, this);
	}
	
	public MatrixF mul(int x, int y, float f, boolean local)
	{
		return this.mul(x, y, f, local ? this : new MatrixF(this));
	}
	
	public MatrixF mul(int x, int y, float f, MatrixF dest)
	{
		dest.set(x, y, this.get(x, y) * f);
		
		return dest;
	}
	
	public MatrixF invert()
	{
		return this.invert(this);
	}
	
	public MatrixF invert(boolean local)
	{
		return this.invert(local ? this : new MatrixF(this.sizeSqrt));
	}
	
	public MatrixF invert(MatrixF ret)//FIXME
	{
		
		
		return ret;
	}
	
	public MatrixF transpose()
	{
		return this.transpose(this);
	}
	
	public MatrixF transpose(boolean local)
	{
		return this.transpose(local ? this : new MatrixF(this.sizeSqrt));
	}
	
	public MatrixF transpose(MatrixF m)
	{
		int s = Math.min(this.sizeSqrt, m.sizeSqrt);
		
		MatrixF tmp = new MatrixF(m);
		
		for (int a = 0; a < s; a++)
		{
			for (int b = 0; b < s; b++)
			{
				m.set(a, b, tmp.get(b, a));
				
			}
			
		}
		
		return m;
	}
	
	public VectorF transform(VectorF vec)
	{
		return this.transform(vec, vec);
	}
	
	public VectorF transform(VectorF vec, boolean local)
	{
		return this.transform(vec, local ? vec : new VectorF(vec.size()));
	}
	
	public VectorF transform(VectorF vec, VectorF dest)
	{
		for (int x = 0; x < this.sizeSqrt; x++)
		{
			for (int y = 0; y < this.sizeSqrt; y++)
			{
				dest.add(y, this.get(x, y) * vec.get(x));
				
			}
			
		}
		
		return dest;
	}
	
	public MatrixF euler(VectorF euler)
	{
		return euler(euler.get(X), euler.get(Y), euler.get(Z));
	}
	
	public MatrixF euler(float eulerX, float eulerY, float eulerZ)
	{
		float cx = (float)Math.cos(eulerX);
		float cy = (float)Math.cos(eulerY);
		float cz = (float)Math.cos(eulerZ);
		float sx = (float)Math.sin(eulerX);
		float sy = (float)Math.sin(eulerY);
		float sz = (float)Math.sin(eulerZ);
		
		float cxz = cx * cz;
		float cxsz = cx * sz;
		float sxcz = sx * cz;
		float sxz = sx * sz;
		
		this.setRow(0, cy * cz, sy * sxcz - cxsz, sy * cxz + sxz);
		this.setRow(1, cy * sz, sy * sxz + cxz, sy * cxsz - sxcz);
		this.setRow(2, -sy, cy * sx, cy * cx);
		
		return this;
	}
	
	public MatrixF rotate(float radians, VectorF axis)
	{
		return this.rotate(radians, axis, this);
	}
	
	public MatrixF rotate(float radians, VectorF axis, boolean local)
	{
		return this.rotate(radians, axis, local ? this : new MatrixF(this.sizeSqrt));
	}
	
	public MatrixF rotate(float radians, VectorF axis, MatrixF ret)
	{
		return this.rotate(radians, axis.get(0), axis.get(1), axis.get(2), ret);
	}
	
	public MatrixF rotate(float radians, float x, float y, float z)
	{
		return this.rotate(radians, x, y, z, this);
	}
	
	public MatrixF rotate(float radians, float x, float y, float z, boolean local)
	{
		return this.rotate(radians, x, y, z, local ? this : new MatrixF(this.sizeSqrt));
	}
	
	public MatrixF rotate(float radians, float x, float y, float z, MatrixF ret)
	{
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);
		float omc = 1.0f - c;
		
		float xy = x * y;
		float yz = y * z;
		float xz = x * z;
		float xs = x * s;
		float ys = y * s;
		float zs = z * s;
		
		float[][] calc = new float[][]
				{{x * x * omc + c,	xy * omc + zs,		xz * omc - ys},
				{xy * omc - zs,		y * y * omc + c,	yz * omc + xs},
				{xz * omc + ys,		yz * omc - xs,		z * z * omc + c}};
		
		MatrixF tmp = new MatrixF(this.sizeSqrt);
		
		Logger.debug("Size of tmp: %s", tmp.size());
		
		for (int ix = 0; x < tmp.sizeSqrt - 1; ix++)
		{
			for (int iy = 0; y < tmp.sizeSqrt - 1; iy++)
			{
				tmp.set(ix, iy, this.get(0, iy) * calc[ix][0] + this.get(1, iy) * calc[ix][1] + this.get(2, iy) * calc[ix][2]);
				
			}
			
		}
		
		ret.set(tmp);
		
		return ret;
	}
	
	public MatrixF rotate(QuaternionF q)//TODO Convert into algorithm
	{
		QuaternionF quat = q.normalize(new QuaternionF());
		
		float s = 2f / MathHelper.length(quat);
		
		this.set(0, 0, 1 - s * (square(quat.get(Y)) + square(quat.get(Z))));
		this.set(1, 0, s * (quat.get(X) * quat.get(Y) + quat.get(W) * quat.get(Z)));
		this.set(2, 0, s * (quat.get(X) * quat.get(Z) - quat.get(W) * quat.get(Y)));
		
		this.set(0, 1, s * (quat.get(X) * quat.get(Y) - quat.get(W) * quat.get(Z)));
		this.set(1, 1, 1 - s * (square(quat.get(X)) + square(quat.get(Z))));
		this.set(2, 1, s * (quat.get(Y) * quat.get(Z) + quat.get(W) * quat.get(X)));
		
		this.set(0, 2, s * (quat.get(X) * quat.get(Z) + quat.get(W) * quat.get(Y)));
		this.set(1, 2, s * (quat.get(Y) * quat.get(Z) - quat.get(W) * quat.get(X)));
		this.set(2, 2, 1 - s * (square(quat.get(X)) + square(quat.get(Y))));
		
		return this;
	}
	
	public MatrixF scale(VectorF vec)
	{
		return this.scale(vec.get(X), vec.get(Y), vec.get(Z));
	}
	
	public MatrixF scale(float x, float y, float z)
	{
		this.set(0, x);
		this.set(5, y);
		this.set(10, z);
		
		return this;
	}
	
	public MatrixF lookAt(VectorF pos, VectorF lookAt)
	{
		return this.lookAt(pos, lookAt, MathConst.Y_AXIS_F);
	}
	
	public MatrixF lookAt(VectorF pos, VectorF target, VectorF up)
	{
		assert pos.size() == 3 : "Position size is not 3";
		assert target.size() == 3 : "Size of target vector is not size 3";
		assert up.size() == 3 : "Upwards vector size is not 3";
		
		VectorF up0 = up.clone();
		VectorF forward = new VectorF(4);
		
		for (int c = 0; c < 3; c++)
		{
			forward.set(c, target.get(c) - pos.get(c));
			
		}
		
		forward.normalize();
		
		VectorF side = MathHelper.cross(forward, up0);
		side.normalize();
		
		MathHelper.cross(up0, side, forward);
		
		this.setRow(0, side);
		this.setRow(1, up0);
		this.setRow(2, forward.negate());
		
		this.invert();
		
		return this;
	}
	
	public MatrixF translate(VectorF vec)
	{
		return this.translate(vec.get(X), vec.get(Y), vec.get(Z));
	}
	
	public MatrixF translate(float x, float y, float z)
	{
		this.set(3, x);
		this.set(7, y);
		this.set(11, z);
		
		return this;
	}
	
	public MatrixF projection(float fov, float aspect, float zFar, float zNear)
	{
		float yScale = 1 / (float)Math.tan(toRadians(fov / 2f));
		float xScale = yScale / aspect;
		float frustumLength = zFar - zNear;
		
		this.set(0, xScale);
		this.set(5, yScale);
		this.set(10, -((zFar + zNear) / frustumLength));
		this.set(11, -((2 * zFar * zNear) / frustumLength));
		this.set(14, -1f);
		
		return this;
	}
	
	public MatrixF ortho(float left, float right, float bottom, float top, float near, float far)
	{
		this.set(0, 0, 2 * (right - left));
		this.set(1, 1, 2 * (top - bottom));
		this.set(2, 2, 2 * (far - near));
		
		this.set(3, 0, -((right + left) * (right - left)));
		this.set(3, 1, -((top + bottom) * (top - bottom)));
		this.set(3, 2, -((far + near) * (far - near)));
		
		return this;
	}
	
	public MatrixF homogenous(VectorF euler, VectorF scale, VectorF trans)
	{
		MatrixF r = new MatrixF().euler(euler);
		MatrixF s = new MatrixF().scale(scale);
		MatrixF t = new MatrixF().translate(trans);
		
		return (MatrixF)r.mul(s).mul(t, this);
	}
	
	public MatrixF homogenous(QuaternionF rot, VectorF scale, VectorF trans)
	{
		MatrixF r = new MatrixF().rotate(rot);
		MatrixF s = new MatrixF().scale(scale);
		MatrixF t = new MatrixF().translate(trans);
		
		return (MatrixF)r.mul(s).mul(t, this);
	}
	
	public MatrixD toMatD()
	{
		MatrixD ret = new MatrixD(this.sizeSqrt());
		
		for (int c = 0; c < this.size(); c++)
		{
			ret.set(c, this.get(c));
			
		}
		
		ret.setIsDirty(false);
		
		return ret;
	}
	
}
