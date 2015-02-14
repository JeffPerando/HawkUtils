
package com.elusivehawk.util.math;

import java.nio.FloatBuffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class MatrixF extends FloatArithmetic
{
	public final int sizeSqrt;
	
	public MatrixF()
	{
		this(4);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public MatrixF(int s)
	{
		super(s * s);
		
		sizeSqrt = s;
		
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
	
	public float get(int x, int y)
	{
		return this.get(x + (y * this.sizeSqrt));
	}
	
	public MatrixF set(int x, int y, float f)
	{
		return (MatrixF)this.set(x + (y * this.sizeSqrt), f);
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
		return this.invert(local ? this : new MatrixF(this.size()));
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
		return this.transpose(local ? this : new MatrixF(this.size()));
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
		float[] fl = new float[dest.size()];
		
		for (int x = 0; x < this.sizeSqrt; x++)
		{
			for (int y = 0; y < this.sizeSqrt; y++)
			{
				fl[y] += (this.get(x, y) * vec.get(x));
				
			}
			
		}
		
		dest.set(fl);
		
		return dest;
	}
	
	public MatrixF rotate(float radians, VectorF axis)
	{
		return this.rotate(radians, axis, this);
	}
	
	public MatrixF rotate(float radians, VectorF axis, boolean local)
	{
		return this.rotate(radians, axis, local ? this : new MatrixF(this.size()));
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
		return this.rotate(radians, x, y, z, local ? this : MatrixHelper.identity());
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
		
		MatrixF tmp = new MatrixF(this.size());
		
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
	
}
