
package com.elusivehawk.util.parse;

import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.util.storage.Pair;

/**
 * 
 * Because regex is evil! And so is stuffing.
 * 
 * @author Elusivehawk
 */
public class Tokenizer
{
	protected List<String> tokens = new ArrayList<String>();
	
	public Tokenizer(){}
	
	public Tokenizer(String... tks)
	{
		addTokens(tks);
		
	}
	
	public void addTokens(String... tks)
	{
		for (String tk : tks)
		{
			if (tk == null || tk.equalsIgnoreCase("") || tk.contains("\n") || this.tokens.contains(tk))
			{
				continue;
			}
			
			this.tokens.add(tk);
			
		}
		
	}
	
	public List<Token> tokenize(List<String> str)
	{
		List<Token> ret = new ArrayList<Token>();
		
		if (this.tokens.isEmpty())
		{
			return ret;
		}
		
		if (str == null || str.isEmpty())
		{
			return ret;
		}
		
		for (int l = 0; l < str.size(); l++)
		{
			String line = str.get(l);
			
			if (!line.isEmpty() && !this.tokenize(line, l, 0, ret))
			{
				ret.add(new Token(line, l, 0));
				
			}
			
			ret.add(new Token("\n", l, line.length()));
			
		}
		
		return ret;
	}
	
	private boolean tokenize(String str, int line, int off, List<Token> ret)
	{
		if (str.isEmpty())
		{
			return false;
		}
		
		if (this.tokens.contains(str))
		{
			ret.add(new Token(str, line, off));
			return true;
		}
		
		String rem = str;
		String tkn = null;
		int col = off;
		int index = -1;
		
		for (String token : this.tokens)
		{
			if (token.length() > rem.length())
			{
				continue;
			}
			
			int i = rem.indexOf(token);
			
			if (i == -1)
			{
				continue;
			}
			
			if (tkn == null || i < index || (index == i && token.length() > tkn.length()))
			{
				tkn = token;
				index = i;
				
			}
			
		}
		
		if (index == -1)
		{
			return false;
		}
		
		Pair<String> spl = ParseHelper.splitFirst(rem, tkn);
		
		if (!spl.one.isEmpty())
		{
			ret.add(new Token(spl.one, line, col));
			col += spl.one.length();
			
		}
		
		ret.add(new Token(tkn, line, col));
		
		col += tkn.length();
		
		rem = spl.two;
		
		if (!rem.isEmpty() && !this.tokenize(rem, line, col, ret))
		{
			ret.add(new Token(rem, line, col));
			
		}
		
		return true;
	}
	
}
