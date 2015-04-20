
package com.elusivehawk.util.math;

import com.elusivehawk.util.RNG;
import static com.elusivehawk.util.math.MathConst.*;

/**
 * 
 * Convenience class for math functions.
 * 
 * @author Elusivehawk
 */
public final class MathHelper
{
	public static final float PI = 3.141592653589793238f;
	
	private MathHelper(){}
	
	public static boolean bounds(double d, double min, double max)
	{
		return d >= min && d < max;
	}
	
	public static boolean bounds(float f, float min, float max)
	{
		return f >= min && f < max;
	}
	
	public static boolean bounds(int i, int min, int max)
	{
		return i >= min && i < max;
	}
	
	public static boolean bounds(long l, long min, long max)
	{
		return l >= min && l < max;
	}
	
	public static boolean bounds(VectorD d, VectorD min, VectorD max)
	{
		for (int c = 0; c < d.size(); c++)
		{
			if (!bounds(d.get(c), min.get(c), max.get(c)))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public static boolean bounds(VectorF v, VectorF min, VectorF max)
	{
		for (int c = 0; c < v.size(); c++)
		{
			if (!bounds(v.get(c), min.get(c), max.get(c)))
			{
				return false;
			}
			
		}
		
		return true;
	}
	
	public static VectorF calcNormal(VectorF one, VectorF two, VectorF three)
	{
		return cross(one.sub(two, false), one.sub(three, false)).normalize();
	}
	
	public static double clamp(double f, double min, double max)
	{
		return Math.min(max, Math.max(f, min));
	}
	
	public static float clamp(float f, float min, float max)
	{
		return Math.min(max, Math.max(f, min));
	}
	
	public static int clamp(int i, int min, int max)
	{
		return Math.min(max, Math.max(i, min));
	}
	
	public static long clamp(long l, long min, long max)
	{
		return Math.min(max, Math.max(l, min));
	}
	
	public static VectorD clamp(VectorD v, VectorD min, VectorD max)
	{
		VectorD ret = new VectorD(v.size());
		
		for (int c = 0; c < v.size(); c++)
		{
			ret.set(c, clamp(v.get(c), min.get(c), max.get(c)));
			
		}
		
		return ret;
	}
	
	public static VectorF clamp(VectorF v, VectorF min, VectorF max)
	{
		VectorF ret = new VectorF(v.size());
		
		for (int c = 0; c < v.size(); c++)
		{
			ret.set(c, clamp(v.get(c), min.get(c), max.get(c)));
			
		}
		
		return ret;
	}
	
	public static VectorD cross(VectorD one, VectorD two)
	{
		return cross(one, two, new VectorD());
	}
	
	public static VectorD cross(VectorD one, VectorD two, VectorD ret)
	{
		assert one.size() <= 3 && two.size() <= 3 && ret.size() <= 3;
		
		double ax = one.get(X);
		double ay = one.get(Y);
		double az = one.get(Z);
		
		double bx = two.get(X);
		double by = two.get(Y);
		double bz = two.get(Z);
		
		ret.set(0, (ay * bz) - (az * by));
		ret.set(1, (az * bx) - (ax * bz));
		ret.set(2, (ax * by) - (ay * bx));
		
		return ret;
	}
	
	public static VectorF cross(VectorF one, VectorF two)
	{
		return cross(one, two, new VectorF());
	}
	
	public static VectorF cross(VectorF one, VectorF two, VectorF ret)
	{
		assert one.size() <= 3 && two.size() <= 3 && ret.size() <= 3;
		
		float ax = one.get(X);
		float ay = one.get(Y);
		float az = one.get(Z);
		
		float bx = two.get(X);
		float by = two.get(Y);
		float bz = two.get(Z);
		
		ret.set(0, (ay * bz) - (az * by));
		ret.set(1, (az * bx) - (ax * bz));
		ret.set(2, (ax * by) - (ay * bx));
		
		return ret;
	}
	
	public static double dist(VectorD from, VectorD to)
	{
		return Math.sqrt(distSquared(from, to));
	}
	
	public static double distSquared(VectorD from, VectorD to)
	{
		assert from.size() == to.size();
		
		double ret = 0f;
		
		for (int c = 0; c < to.size(); c++)
		{
			ret += square(to.get(c) - from.get(c));
			
		}
		
		return ret;
	}
	
	public static float dist(VectorF from, VectorF to)
	{
		return (float)Math.sqrt(distSquared(from, to));
	}
	
	public static float distSquared(VectorF from, VectorF to)
	{
		assert from.size() == to.size();
		
		float ret = 0f;
		
		for (int c = 0; c < to.size(); c++)
		{
			ret += square(to.get(c) - from.get(c));
			
		}
		
		return ret;
	}
	
	public static double dot(VectorD one, VectorD two)
	{
		int size = Math.min(one.size(), two.size());
		float ret = 0f;
		
		for (int c = 0; c < size; c++)
		{
			ret += one.get(c) * two.get(c);
			
		}
		
		return ret;
	}
	
	public static float dot(VectorF one, VectorF two)
	{
		int size = Math.min(one.size(), two.size());
		float ret = 0f;
		
		for (int c = 0; c < size; c++)
		{
			ret += one.get(c) * two.get(c);
			
		}
		
		return ret;
	}
	
	public static double interpolate(double one, double two, double factor)
	{
		assert bounds(factor, 0.0, 1.0);
		
		return ((two * factor) + ((1f - factor) * one));
	}
	
	public static float interpolate(float one, float two, float factor)
	{
		assert bounds(factor, 0.0f, 1.0f);
		
		return ((two * factor) + ((1f - factor) * one));
	}
	
	public static VectorD interpolate(VectorD one, VectorD two, double factor)
	{
		return interpolate(one, two, factor, new VectorD(two.size()));
	}
	
	public static VectorD interpolate(VectorD one, VectorD two, double factor, VectorD dest)
	{
		for (int c = 0; c < dest.size(); c++)
		{
			dest.set(c, interpolate(one.get(c), two.get(c), factor));
			
		}
		
		return dest;
	}
	
	public static VectorF interpolate(VectorF one, VectorF two, float factor)
	{
		return interpolate(one, two, factor, new VectorF(two.size()));
	}
	
	public static VectorF interpolate(VectorF one, VectorF two, float factor, VectorF dest)
	{
		for (int c = 0; c < dest.size(); c++)
		{
			dest.set(c, interpolate(one.get(c), two.get(c), factor));
			
		}
		
		return dest;
	}
	
	public static boolean isOdd(int i)
	{
		return (i & 1) == 1;
	}
	
	public static double length(DoubleArithmetic d)
	{
		return Math.sqrt(lengthSquared(d));
	}
	
	public static float length(FloatArithmetic v)
	{
		return (float)Math.sqrt(lengthSquared(v));
	}
	
	public static double lengthSquared(DoubleArithmetic v)
	{
		double ret = 0f;
		
		for (int c = 0; c < v.size(); c++)
		{
			ret += square(v.get(c));
			
		}
		
		return ret;
	}
	
	public static float lengthSquared(FloatArithmetic v)
	{
		float ret = 0f;
		
		for (int c = 0; c < v.size(); c++)
		{
			ret += square(v.get(c));
			
		}
		
		return ret;
	}
	
	public static int percent(int i, int max)
	{
		return (int)(((float)i / max) * 100);
	}
	
	public static double pow(double d, int pow)
	{
		if (pow == 0)
		{
			return 1;
		}
		
		double ret = d;
		
		for (int c = 0; c < pow; c++)
		{
			ret *= d;
			
		}
		
		return ret;
	}
	
	public static float pow(float f, int pow)
	{
		if (pow == 0)
		{
			return 1;
		}
		
		float ret = f;
		
		for (int c = 0; c < pow; c++)
		{
			ret *= f;
			
		}
		
		return ret;
	}
	
	public static int pow(int i, int pow)
	{
		if (pow == 0)
		{
			return 1;
		}
		
		int ret = i;
		
		for (int c = 0; c < pow; c++)
		{
			ret *= i;
			
		}
		
		return ret;
	}
	
	public static boolean rollDice(float weight)
	{
		return weight > RNG.rng().nextFloat();
	}
	
	public static double square(double d)
	{
		return d * d;
	}
	
	public static float square(float f)
	{
		return f * f;
	}
	
	public static int square(int i)
	{
		return i * i;
	}
	
	public static double toDegrees(double radian)
	{
		return (radian * 180) / Math.PI;
	}
	
	public static float toDegrees(float radian)
	{
		return (radian * 180) / PI;
	}
	
	public static double toRadians(double degree)
	{
		return (degree * Math.PI) / 180;
	}
	
	public static float toRadians(float degree)
	{
		return (degree * PI) / 180;
	}
	
	public static VectorD toRadians(VectorD vec)
	{
		VectorD ret = new VectorD(vec);
		
		for (int c = 0; c < ret.size(); c++)
		{
			ret.set(c, toRadians(ret.get(c)));
			
		}
		
		return ret;
	}
	
	public static VectorF toRadians(VectorF vec)
	{
		VectorF ret = new VectorF(vec);
		
		for (int c = 0; c < ret.size(); c++)
		{
			ret.set(c, toRadians(ret.get(c)));
			
		}
		
		return ret;
	}
	
	public static int max(int... is)
	{
		int ret = Integer.MIN_VALUE;
		
		for (int i : is)
		{
			ret = Math.max(ret, i);
			
		}
		
		return ret;
	}
	
	public static int min(int... is)
	{
		int ret = Integer.MAX_VALUE;
		
		for (int i : is)
		{
			ret = Math.min(ret, i);
			
		}
		
		return ret;
	}
	
}
