
package com.elusivehawk.util.storage;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class TriTuple<A, B, C> extends Tuple<A, B>
{
	public C three;
	
	@SuppressWarnings("unqualified-field-access")
	public TriTuple(A first, B second, C third)
	{
		super(first, second);
		three = third;
		
	}
	
	@Override
	public TriTuple<A, B, C> clone()
	{
		return new TriTuple<A, B, C>(this.one, this.two, this.three);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof TriTuple))
		{
			return false;
		}
		
		if (!super.equals(obj))
		{
			return false;
		}
		
		return ((TriTuple)obj).three.equals(this.three);
	}
	
	@Override
	public int hashCode()
	{
		return super.hashCode() * (31 + this.three.hashCode());
	}
	
	public static <O, T, TH> TriTuple<O, T, TH> create(O one, T two, TH three)
	{
		return new TriTuple<O, T, TH>(one, two, three);
	}
	
}
