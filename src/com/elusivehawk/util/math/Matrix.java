
package com.elusivehawk.util.math;

import java.nio.FloatBuffer;
import com.elusivehawk.util.storage.Buffer;
import com.elusivehawk.util.storage.BufferHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class Matrix extends MathArray<Float>
{
	private final float[] data;
	public final int w, h;
	
	public Matrix()
	{
		this(4, 4);
		
	}
	
	public Matrix(int size)
	{
		this((int)Math.sqrt(size), (int)Math.sqrt(size));
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Matrix(int x, int y)
	{
		data = new float[x * y];
		w = x;
		h = y;
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public Matrix(float[] info)
	{
		this(info.length);
		
		for (int c = 0; c < info.length; c++)
		{
			data[c] = info[c];
			
		}
		
	}
	
	public Matrix(Buffer<Float> buf, int x, int y)
	{
		this(x, y);
		
		int size = Math.min(size(), buf.remaining());
		
		for (int c = 0; c < size; c++)
		{
			set(c, buf.next());
			
		}
		
	}
	
	public Matrix(FloatBuffer buf, int x, int y)
	{
		this(x, y);
		
		load(buf);
		
	}
	
	public Matrix(Matrix m)
	{
		this(m.data);
		
	}
	
	@Override
	public int size()
	{
		return this.w * this.h;
	}
	
	@Override
	public Float get(int pos)
	{
		return this.data[pos];
	}
	
	@Override
	public Matrix set(int pos, Number num, boolean notify)
	{
		this.data[pos] = num.floatValue();
		
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
		
		//FIXME
		
		return dest;
	}
	
	@Override
	public MathArray<Float> add(MathArray<Float> obj, MathArray<Float> dest)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, this.get(c) + buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return dest;
	}
	
	@Override
	public MathArray<Float> div(MathArray<Float> obj, MathArray<Float> dest)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, this.get(c) / buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return dest;
	}
	
	@Override
	public MathArray<Float> sub(MathArray<Float> obj, MathArray<Float> dest)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, this.get(c) - buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return dest;
	}
	
	@Override
	public MathArray<Float> mul(MathArray<Float> obj, MathArray<Float> dest)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.size(); c++)
		{
			dest.set(c, this.get(c) * buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return dest;
	}
	
	@Override
	public Float[] multiget(int bitmask)
	{
		throw new UnsupportedOperationException("Matrices do not currently support multiget().");
	}
	
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder((this.h + 1) * ((this.w * 2) + 2));
		
		for (int y = 0; y < this.h; y++)
		{
			b.append("[");
			
			for (int x = 0; x < this.w; x++)
			{
				b.append(this.get(x, y));
				
				if (x != (this.w - 1))
				{
					b.append(", ");
					
				}
				
			}
			
			b.append("]");
			
			if (y != (this.h - 1))
			{
				b.append("\n");
				
			}
			
		}
		
		return b.toString();
	}
	
	@Override
	public Matrix set(MathArray<Float> obj)
	{
		Buffer<Float> buf = new Buffer<Float>(obj);
		
		for (int c = 0; c < this.size(); c++)
		{
			this.set(c, buf.next());
			
			if (!buf.hasNext())
			{
				buf.rewind();
				
			}
			
		}
		
		return this;
	}
	
	public Matrix load(FloatBuffer buf)
	{
		int l = Math.min(this.size(), buf.remaining());
		
		for (int c = 0; c < l; c++)
		{
			this.data[c] = buf.get();
			
		}
		
		return this;
	}
	
	public Matrix save(FloatBuffer buf)
	{
		buf.put(this.data);
		
		return this;
	}
	
	public FloatBuffer asBuffer()
	{
		FloatBuffer ret = BufferHelper.createFloatBuffer(this.w * this.h);
		
		this.save(ret);
		
		return ret;
	}
	
	public float get(int x, int y)
	{
		return this.get(x + (y * this.h));
	}
	
	public Matrix set(int x, int y, float f)
	{
		return this.set(x, y, f, true);
	}
	
	public Matrix set(int x, int y, float f, boolean notify)
	{
		this.data[x + (y * this.h)] = f;
		
		this.setIsDirty(true);
		
		if (notify)
		{
			this.onChanged();
			
		}
		
		return this;
	}
	
	public Matrix setRow(int r, float... fs)
	{
		int i = Math.min(this.w, fs.length);
		
		for (int c = 0; c < i; c++)
		{
			this.set(c, r, fs[c]);
			
		}
		
		return this;
	}
	
	public Matrix setRow(int r, Vector vec)
	{
		return this.setRow(r, vec, true);
	}
	
	public Matrix setRow(int r, Vector vec, boolean local)
	{
		Matrix ret = local ? this : new Matrix(this);
		
		int i = Math.min(ret.w, vec.size());
		
		for (int c = 0; c < i; c++)
		{
			ret.set(c, r, vec.get(c));
			
		}
		
		return ret;
	}
	
	public Matrix setIdentity()
	{
		int count = Math.min(this.w, this.h);
		
		for (int c = 0; c < count; c++)
		{
			this.set(c, c, 1f);
			
		}
		
		return this;
	}
	
	public Matrix add(int x, int y, float f)
	{
		return this.add(x, y, f, this);
	}
	
	public Matrix add(int x, int y, float f, boolean local)
	{
		return this.add(x, y, f, local ? this : new Matrix(this));
	}
	
	public Matrix add(int x, int y, float f, Matrix dest)
	{
		dest.set(x, y, this.get(x, y) + f);
		
		return dest;
	}
	
	public Matrix sub(int x, int y, float f)
	{
		return this.sub(x, y, f, this);
	}
	
	public Matrix sub(int x, int y, float f, boolean local)
	{
		return this.sub(x, y, f, local ? this : new Matrix(this));
	}
	
	public Matrix sub(int x, int y, float f, Matrix dest)
	{
		dest.set(x, y, this.get(x, y) - f);
		
		return dest;
	}
	
	public Matrix div(int x, int y, float f)
	{
		return this.div(x, y, f, this);
	}
	
	public Matrix div(int x, int y, float f, boolean local)
	{
		return this.div(x, y, f, local ? this : new Matrix(this));
	}
	
	public Matrix div(int x, int y, float f, Matrix dest)
	{
		dest.set(x, y, this.get(x, y) / f);
		
		return dest;
	}
	
	public Matrix mul(int x, int y, float f)
	{
		return this.mul(x, y, f, this);
	}
	
	public Matrix mul(int x, int y, float f, boolean local)
	{
		return this.mul(x, y, f, local ? this : new Matrix(this));
	}
	
	public Matrix mul(int x, int y, float f, Matrix dest)
	{
		dest.set(x, y, this.get(x, y) * f);
		
		return dest;
	}
	
	public Matrix invert()
	{
		return this.invert(this);
	}
	
	public Matrix invert(boolean local)
	{
		return this.invert(local ? this : new Matrix(this.w, this.h));
	}
	
	public Matrix invert(Matrix ret)//FIXME
	{
		
		
		return ret;
	}
	
	public Matrix transpose()
	{
		return this.transpose(this);
	}
	
	public Matrix transpose(boolean local)
	{
		return this.transpose(local ? this : new Matrix(this.w, this.h));
	}
	
	public Matrix transpose(Matrix m)
	{
		int x = Math.min(m.w, this.w);
		int y = Math.min(m.h, this.h);
		
		Matrix tmp = new Matrix(m);
		
		for (int a = 0; a < x; a++)
		{
			for (int b = 0; b < y; b++)
			{
				m.set(a, b, tmp.get(b, a));
				
			}
			
		}
		
		return m;
	}
	
	public Vector transform(Vector vec)
	{
		return this.transform(vec, vec);
	}
	
	public Vector transform(Vector vec, boolean local)
	{
		return this.transform(vec, local ? vec : new Vector(vec.size()));
	}
	
	public Vector transform(Vector vec, Vector dest)
	{
		float[] fl = new float[dest.size()];
		
		for (int x = 0; x < this.w; x++)
		{
			for (int y = 0; y < this.h; y++)
			{
				fl[y] += (this.get(x, y) * vec.get(x));
				
			}
			
		}
		
		dest.set(fl);
		
		return dest;
	}
	
	public Matrix rotate(float radians, Vector axis)
	{
		return this.rotate(radians, axis, this);
	}
	
	public Matrix rotate(float radians, Vector axis, boolean local)
	{
		return this.rotate(radians, axis, local ? this : new Matrix(this.w, this.h));
	}
	
	public Matrix rotate(float radians, Vector axis, Matrix ret)
	{
		return this.rotate(radians, axis.get(0), axis.get(1), axis.get(2), ret);
	}
	
	public Matrix rotate(float radians, float x, float y, float z)
	{
		return this.rotate(radians, x, y, z, this);
	}
	
	public Matrix rotate(float radians, float x, float y, float z, boolean local)
	{
		return this.rotate(radians, x, y, z, local ? this : MatrixHelper.identity());
	}
	
	public Matrix rotate(float radians, float x, float y, float z, Matrix ret)
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
		
		Matrix tmp = new Matrix(ret.w, ret.h);
		
		for (int ix = 0; x < tmp.w - 1; ix++)
		{
			for (int iy = 0; y < tmp.h - 1; iy++)
			{
				tmp.set(ix, iy, this.get(0, iy) * calc[ix][0] + this.get(1, iy) * calc[ix][1] + this.get(2, iy) * calc[ix][2]);
				
			}
			
		}
		
		ret.set(tmp);
		
		return ret;
	}
	
}
