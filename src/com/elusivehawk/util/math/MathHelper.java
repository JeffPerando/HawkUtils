
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
	
	public static boolean bounds(float f, float min, float max)
	{
		return f >= min && f <= max;
	}
	
	public static boolean bounds(int i, int min, int max)
	{
		return i >= min && i <= max;
	}
	
	public static boolean bounds(long l, long min, long max)
	{
		return l >= min && l <= max;
	}
	
	public static boolean bounds(VectorF v, VectorF min, VectorF max)
	{
		int size = min(v.size(), min.size(), max.size());
		
		for (int c = 0; c < size; c++)
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
	
	public static VectorF clamp(VectorF v, VectorF min, VectorF max)
	{
		int size = min(v.size(), min.size(), max.size());
		
		VectorF ret = new VectorF(size);
		
		for (int c = 0; c < size; c++)
		{
			ret.set(c, clamp(v.get(c), min.get(c), max.get(c)));
			
		}
		
		return ret;
	}
	
	public static VectorF cross(VectorF one, VectorF two)
	{
		VectorF ret = new VectorF();
		
		cross(ret, one, two);
		
		return ret;
	}
	
	public static void cross(VectorF ret, VectorF one, VectorF two)
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
		
	}
	
	public static float dist(VectorF from, VectorF to)
	{
		return (float)Math.sqrt(distSquared(from, to));
	}
	
	public static float distSquared(VectorF from, VectorF to)
	{
		int size = Math.min(from.size(), to.size());
		float ret = 0f;
		
		for (int c = 0; c < size; c++)
		{
			ret += square(to.get(c) - from.get(c));
			
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
	
	public static float interpolate(float one, float two, float factor)
	{
		assert bounds(factor, 0.0f, 1.0f);
		
		return ((two * factor) + ((1f - factor) * one));
	}
	
	public static VectorF interpolate(VectorF one, VectorF two, float factor)
	{
		return interpolate(one, two, factor, new VectorF(Math.min(one.size(), two.size())));
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
	
	public static float length(FloatArithmetic v)
	{
		return (float)Math.sqrt(lengthSquared(v));
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
	
	public static float square(float f)
	{
		return f * f;
	}
	
	public static int square(int i)
	{
		return i * i;
	}
	
	public static float toDegrees(float radian)
	{
		return (radian * 180) / PI;
	}
	
	public static float toRadians(float degree)
	{
		return (degree * PI) / 180;
	}
	
	public static VectorF toRadians(VectorF vec)
	{
		VectorF ret = new VectorF(vec);
		
		for (int c = 0; c < ret.size(); c++)
		{
			ret.set(c, toRadians(ret.get(c)));
			
		}
		
		ret.onChanged();
		
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
