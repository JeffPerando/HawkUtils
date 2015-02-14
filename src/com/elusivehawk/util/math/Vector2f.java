
package com.elusivehawk.util.math;

/**
 * 
 * Compatibility class for porting over math libraries.
 * <p>
 * NOTICE: Always deprecated; Refactor your code to use more flexible {@link VectorF}s!
 * 
 * @author Elusivehawk
 */
@Deprecated
public class Vector2f extends VectorF
{
	public float x = 0f, y = 0f;
	
	public Vector2f()
	{
		this(2);
		
	}
	
	public Vector2f(int size)
	{
		super(size);
		
	}
	
	public Vector2f(float a, float b)
	{
		this();
		
		set(a, b);
		
	}
	
	public Vector2f(Vector2f vec)
	{
		super(vec);
		
	}
	
	@Override
	public float get(int pos)
	{
		switch (pos)
		{
			case 0: return this.x;
			case 1: return this.y;
			default: return 0f;
		}
		
	}
	
	@Override
	public Vector2f set(int pos, float f)
	{
		switch (pos)
		{
			case 0: this.x = f;
			case 1: this.y = f;
			
		}
		
		return this;
	}
	
	public void set(float a, float b)
	{
		this.x = a;
		this.y = b;
		
	}
	
	public void sub(Vector2f vec)
	{
		this.sub(vec, this);
		
	}
	
}
