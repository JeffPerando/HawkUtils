
package com.elusivehawk.util.parse;

import com.elusivehawk.util.MakeStruct;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@MakeStruct
public class Token
{
	public final String str;
	public final int line, col;
	
	@SuppressWarnings("unqualified-field-access")
	public Token(String s, int l, int c)
	{
		str = s;
		line = l;
		col = c;
		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof String)
		{
			return this.str.equals(obj);
		}
		else if (obj instanceof Token)
		{
			Token tkn = (Token)obj;
			
			if (!this.str.equals(tkn.str))
			{
				return false;
			}
			
			if (this.line != tkn.line)
			{
				return false;
			}
			
			if (this.col != tkn.col)
			{
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		int ret = 31;
		
		ret *= (31 + this.str.hashCode());
		ret *= (31 + this.line);
		ret *= (31 + this.col);
		
		return ret;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%s:%s", this.str, this.line, this.col);
	}
	
}
