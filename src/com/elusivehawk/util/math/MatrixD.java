
package com.elusivehawk.util.math;

import static com.elusivehawk.util.math.MathConst.W;
import static com.elusivehawk.util.math.MathConst.X;
import static com.elusivehawk.util.math.MathConst.Y;
import static com.elusivehawk.util.math.MathConst.Z;
import static com.elusivehawk.util.math.MathHelper.square;
import static com.elusivehawk.util.math.MathHelper.toRadians;
import java.nio.DoubleBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MatrixD extends DoubleArithmetic
{
	private final int sizeSqrt;
	
	public MatrixD()
	{
		this(4);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MatrixD(int s)
	{
		super(s * s);
		
		sizeSqrt = s;
		
		setIdentity();
		
	}
	
	public MatrixD(double... info)
	{
		this((int)Math.sqrt(info.length));
		
		set(info);
		
	}
	
	public MatrixD(int size, DoubleBuffer buf)
	{
		this(size);
		
		set(buf);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MatrixD(MatrixD m)
	{
		super(m);
		
		sizeSqrt = m.sizeSqrt;
		
	}
	
	public int sizeSqrt()
	{
		return this.sizeSqrt;
	}
	
	public double get(int x, int y)
	{
		return this.get(y + (x * this.sizeSqrt));
	}
	
	public MatrixD set(int x, int y, double f)
	{
		return (MatrixD)this.set(y + (x * this.sizeSqrt), f);
	}
	
	public MatrixD setRow(int r, double... fs)
	{
		int i = Math.min(this.sizeSqrt, fs.length);
		
		for (int c = 0; c < i; c++)
		{
			this.set(c, r, fs[c]);
			
		}
		
		return this;
	}
	
	public MatrixD setRow(int r, VectorD vec)
	{
		return this.setRow(r, vec, true);
	}
	
	public MatrixD setRow(int r, VectorD vec, boolean local)
	{
		MatrixD ret = local ? this : new MatrixD(this);
		
		int i = Math.min(ret.sizeSqrt, vec.size());
		
		for (int c = 0; c < i; c++)
		{
			ret.set(c, r, vec.get(c));
			
		}
		
		return ret;
	}
	
	public MatrixD setIdentity()
	{
		for (int c = 0; c < this.sizeSqrt; c++)
		{
			this.set(c, c, 1f);
			
		}
		
		return this;
	}
	
	public MatrixD resetIdentity()
	{
		for (int x = 0; x < this.sizeSqrt; x++)
		{
			for (int y = 0; y < this.sizeSqrt; y++)
			{
				double f = x == y ? 0f : 1f;
				
				if (this.get(x, y) != f)
				{
					this.set(x, y, f);
					
				}
				
			}
			
		}
		
		return this;
	}
	
	public MatrixD add(int x, int y, double f)
	{
		return this.add(x, y, f, this);
	}
	
	public MatrixD add(int x, int y, double f, boolean local)
	{
		return this.add(x, y, f, local ? this : new MatrixD(this));
	}
	
	public MatrixD add(int x, int y, double f, MatrixD dest)
	{
		dest.set(x, y, this.get(x, y) + f);
		
		return dest;
	}
	
	public MatrixD sub(int x, int y, double f)
	{
		return this.sub(x, y, f, this);
	}
	
	public MatrixD sub(int x, int y, double f, boolean local)
	{
		return this.sub(x, y, f, local ? this : new MatrixD(this));
	}
	
	public MatrixD sub(int x, int y, double f, MatrixD dest)
	{
		dest.set(x, y, this.get(x, y) - f);
		
		return dest;
	}
	
	public MatrixD div(int x, int y, double f)
	{
		return this.div(x, y, f, this);
	}
	
	public MatrixD div(int x, int y, double f, boolean local)
	{
		return this.div(x, y, f, local ? this : new MatrixD(this));
	}
	
	public MatrixD div(int x, int y, double f, MatrixD dest)
	{
		dest.set(x, y, this.get(x, y) / f);
		
		return dest;
	}
	
	public MatrixD mul(int x, int y, double f)
	{
		return this.mul(x, y, f, this);
	}
	
	public MatrixD mul(int x, int y, double f, boolean local)
	{
		return this.mul(x, y, f, local ? this : new MatrixD(this));
	}
	
	public MatrixD mul(int x, int y, double f, MatrixD dest)
	{
		dest.set(x, y, this.get(x, y) * f);
		
		return dest;
	}
	
	public MatrixD invert()
	{
		return this.invert(this);
	}
	
	public MatrixD invert(boolean local)
	{
		return this.invert(local ? this : new MatrixD(this.sizeSqrt));
	}
	
	public MatrixD invert(MatrixD ret)//FIXME
	{
		
		
		return ret;
	}
	
	public MatrixD transpose()
	{
		return this.transpose(this);
	}
	
	public MatrixD transpose(boolean local)
	{
		return this.transpose(local ? this : new MatrixD(this.sizeSqrt));
	}
	
	public MatrixD transpose(MatrixD m)
	{
		int s = Math.min(this.sizeSqrt, m.sizeSqrt);
		
		MatrixD tmp = new MatrixD(m);
		
		for (int a = 0; a < s; a++)
		{
			for (int b = 0; b < s; b++)
			{
				m.set(a, b, tmp.get(b, a));
				
			}
			
		}
		
		return m;
	}
	
	public VectorD transform(VectorD vec)
	{
		return this.transform(vec, vec);
	}
	
	public VectorD transform(VectorD vec, boolean local)
	{
		return this.transform(vec, local ? vec : new VectorD(vec.size()));
	}
	
	public VectorD transform(VectorD vec, VectorD dest)
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
	
	public MatrixD euler(VectorD euler)
	{
		return euler(euler.get(X), euler.get(Y), euler.get(Z));
	}
	
	public MatrixD euler(double eulerX, double eulerY, double eulerZ)
	{
		double cx = Math.cos(eulerX);
		double cy = Math.cos(eulerY);
		double cz = Math.cos(eulerZ);
		double sx = Math.sin(eulerX);
		double sy = Math.sin(eulerY);
		double sz = Math.sin(eulerZ);
		
		double cxz = cx * cz;
		double cxsz = cx * sz;
		double sxcz = sx * cz;
		double sxz = sx * sz;
		
		this.setRow(0, cy * cz, sy * sxcz - cxsz, sy * cxz + sxz);
		this.setRow(1, cy * sz, sy * sxz + cxz, sy * cxsz - sxcz);
		this.setRow(2, -sy, cy * sx, cy * cx);
		
		return this;
	}
	
	public MatrixD rotate(double radians, VectorD axis)
	{
		return this.rotate(radians, axis, this);
	}
	
	public MatrixD rotate(double radians, VectorD axis, boolean local)
	{
		return this.rotate(radians, axis, local ? this : new MatrixD(this.sizeSqrt));
	}
	
	public MatrixD rotate(double radians, VectorD axis, MatrixD ret)
	{
		return this.rotate(radians, axis.get(0), axis.get(1), axis.get(2), ret);
	}
	
	public MatrixD rotate(double radians, double x, double y, double z)
	{
		return this.rotate(radians, x, y, z, this);
	}
	
	public MatrixD rotate(double radians, double x, double y, double z, boolean local)
	{
		return this.rotate(radians, x, y, z, local ? this : new MatrixD(this.sizeSqrt));
	}
	
	public MatrixD rotate(double radians, double x, double y, double z, MatrixD ret)
	{
		double c = Math.cos(radians);
		double s = Math.sin(radians);
		double omc = 1.0f - c;
		
		double xy = x * y;
		double yz = y * z;
		double xz = x * z;
		double xs = x * s;
		double ys = y * s;
		double zs = z * s;
		
		double[][] calc = new double[][]
				{{x * x * omc + c,	xy * omc + zs,		xz * omc - ys},
				{xy * omc - zs,		y * y * omc + c,	yz * omc + xs},
				{xz * omc + ys,		yz * omc - xs,		z * z * omc + c}};
		
		MatrixD tmp = new MatrixD(this.sizeSqrt);
		
		for (int ix = 0; ix < tmp.sizeSqrt - 1; ix++)
		{
			for (int iy = 0; iy < tmp.sizeSqrt - 1; iy++)
			{
				tmp.set(ix, iy, this.get(0, iy) * calc[ix][0] + this.get(1, iy) * calc[ix][1] + this.get(2, iy) * calc[ix][2]);
				
			}
			
		}
		
		ret.set(tmp);
		
		return ret;
	}
	
	public MatrixD rotate(QuaternionD q)//TODO Convert into algorithm
	{
		QuaternionD quat = q.normalize(new QuaternionD());
		
		double s = 2f / MathHelper.length(quat);
		
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
	
	public MatrixD scale(VectorD vec)
	{
		return this.scale(vec.get(X), vec.get(Y), vec.get(Z));
	}
	
	public MatrixD scale(double x, double y, double z)
	{
		this.set(0, x);
		this.set(5, y);
		this.set(10, z);
		
		return this;
	}
	
	public MatrixD lookAt(VectorD pos, VectorD lookAt)
	{
		return this.lookAt(pos, lookAt, MathConst.Y_AXIS_D);
	}
	
	public MatrixD lookAt(VectorD pos, VectorD target, VectorD up)
	{
		assert pos.size() == 3 : "Position size is not 3";
		assert target.size() == 3 : "Size of target vector is not size 3";
		assert up.size() == 3 : "Upwards vector size is not 3";
		
		VectorD up0 = up.clone();
		VectorD forward = new VectorD(4);
		
		for (int c = 0; c < 3; c++)
		{
			forward.set(c, target.get(c) - pos.get(c));
			
		}
		
		forward.normalize();
		
		VectorD side = MathHelper.cross(forward, up0);
		side.normalize();
		
		MathHelper.cross(up0, side, forward);
		
		this.setRow(0, side);
		this.setRow(1, up0);
		this.setRow(2, forward.negate());
		
		this.invert();
		
		return this;
	}
	
	public MatrixD translate(double x, double y, double z)
	{
		return this.translate(new VectorD(x, y, z));
	}
	
	public MatrixD translate(VectorD vec)
	{
		for (int y = 0; y < this.sizeSqrt - 1; y++)
		{
			double d = 0;
			
			for (int x = 0; x < this.sizeSqrt - 1; x++)
			{
				d += (this.get(x, y) * vec.get(x));
				
			}
			
			this.set(this.sizeSqrt - 1, y, d);
			
		}
		
		return this;
	}
	
	public MatrixD projection(double fov, double aspect, double zFar, double zNear)
	{
		double yScale = 1 / Math.tan(toRadians(fov / 2f));
		double xScale = yScale / aspect;
		double frustumLength = zFar - zNear;
		
		this.set(0, xScale);
		this.set(5, yScale);
		this.set(10, -((zFar + zNear) / frustumLength));
		this.set(11, -((2 * zFar * zNear) / frustumLength));
		this.set(14, -1f);
		
		return this;
	}
	
	public MatrixD ortho(double left, double right, double bottom, double top, double near, double far)
	{
		this.set(0, 0, 2 * (right - left));
		this.set(1, 1, 2 * (top - bottom));
		this.set(2, 2, 2 * (far - near));
		
		this.set(3, 0, -((right + left) * (right - left)));
		this.set(3, 1, -((top + bottom) * (top - bottom)));
		this.set(3, 2, -((far + near) * (far - near)));
		
		return this;
	}
	
	public MatrixD homogenous(VectorD euler, VectorD scale, VectorD trans)
	{
		MatrixD r = new MatrixD().euler(euler);
		MatrixD s = new MatrixD().scale(scale);
		MatrixD t = new MatrixD().translate(trans);
		
		return (MatrixD)r.mul(s).mul(t, this);
	}
	
	public MatrixD homogenous(QuaternionD rot, VectorD scale, VectorD trans)
	{
		MatrixD r = new MatrixD().rotate(rot);
		MatrixD s = new MatrixD().scale(scale);
		MatrixD t = new MatrixD().translate(trans);
		
		return (MatrixD)r.mul(s).mul(t, this);
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
