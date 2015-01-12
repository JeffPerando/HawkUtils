
package com.elusivehawk.util.parse.json;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import com.elusivehawk.util.io.IByteReader;
import com.elusivehawk.util.io.IOHelper;
import com.elusivehawk.util.parse.ParseHelper;
import com.elusivehawk.util.parse.Token;
import com.elusivehawk.util.parse.Tokenizer;
import com.elusivehawk.util.storage.Buffer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class JsonParser
{
	public static final String[] SEPARATORS = {"\"", ":", ",", "{", "}", "[", "]", "e", "E", "+", "-", "true", "false"};
	
	private JsonParser(){}
	
	public static JsonObject parse(File file) throws JsonParseException
	{
		return parse(IOHelper.readText(file));
	}
	
	public static JsonObject parse(IByteReader r) throws JsonParseException
	{
		return parse(IOHelper.readText(r));
	}
	
	public static JsonObject parse(InputStream in) throws JsonParseException
	{
		return parse(IOHelper.readText(in));
	}
	
	public static JsonObject parse(Reader r) throws JsonParseException
	{
		return parse(IOHelper.readText(r));
	}
	
	public static JsonObject parse(byte[] bs) throws JsonParseException
	{
		return parse(IOHelper.readText(bs));
	}
	
	public static JsonObject parse(List<String> strs) throws JsonParseException
	{
		if (strs == null || strs.isEmpty())
		{
			return null;
		}
		
		Tokenizer t = new Tokenizer();
		
		t.addTokens(SEPARATORS);
		t.addTokens(ParseHelper.NUMBERS);
		t.addTokens(ParseHelper.WHITESPACE);
		
		Buffer<Token> buf = new Buffer<Token>(t.tokenize(strs));
		
		Object ret = parseValue(buf);
		
		if (!(ret instanceof JsonObject))
		{
			throw new JsonParseException("Was expecting a JSON object, got \"%s\"", ret);
		}
		
		skipWhitespace(buf);
		
		if (buf.hasNext())
		{
			throw new JsonParseException("");
		}
		
		return (JsonObject)ret;
	}
	
	private static void skipWhitespace(Buffer<Token> buf)
	{
		Token tkn;
		
		while (buf.hasNext())
		{
			tkn = buf.next(false);
			
			if (ParseHelper.isWhitespace(tkn.str))
			{
				buf.skip();
				
				continue;
				
			}
			
			return;
		}
		
	}
	
	private static Object parseValue(Buffer<Token> buf) throws JsonParseException
	{
		Token tkn = buf.next(false);
		
		if (tkn == null)
		{
			throw new JsonParseException(new NullPointerException());
		}
		
		String str = tkn.str.toLowerCase();
		
		switch (str)
		{
			case "\"": return parseString(buf);
			case "{": return parseObj(buf);
			case "[": return parseArray(buf);
			case "null": return null;
			case "true": return true;
			case "false": return false;
			
		}
		
		if (ParseHelper.isInt(str) || "-".equalsIgnoreCase(str))
		{
			return parseInt(buf);
		}
		
		throw new JsonParseException("Invalid value found at line %s, col %s: \"%s\"", tkn.line, tkn.col, ParseHelper.sanitizeEscapeSequence(str));
	}
	
	private static String parseString(Buffer<Token> buf)
	{
		Token tkn = buf.next();
		
		if (!"\"".equalsIgnoreCase(tkn.str))
		{
			throw new JsonParseException("Found \"%s\" at line %s, col %s; Was expecting quote mark", tkn.str, tkn.line, tkn.col);
		}
		
		StringBuilder b = new StringBuilder();
		
		while (!"\"".equalsIgnoreCase((tkn = buf.next()).str))
		{
			b.append(ParseHelper.valueOf(tkn.str));
			
		}
		
		return b.toString();
	}
	
	private static JsonObject parseObj(Buffer<Token> buf) throws JsonParseException
	{
		Token tkn = buf.next();
		
		if (!"{".equalsIgnoreCase(tkn.str))
		{
			throw new JsonParseException("Found \"%s\" at line %s, col %s; Was expecting \"{\"", tkn.str, tkn.line, tkn.col);
		}
		
		JsonObject ret = new JsonObject();
		boolean kill = false;
		
		while (!"}".equalsIgnoreCase((tkn = buf.next(false)).str))
		{
			skipWhitespace(buf);
			
			String name = parseString(buf);
			
			skipWhitespace(buf);
			
			tkn = buf.next();
			
			if (!":".equalsIgnoreCase(tkn.str))
			{
				throw new JsonParseException("Found \"%s\" for keypair %s at line %s, col %s; Was expecting \":\"", tkn.str, name, tkn.line, tkn.col);
			}
			
			skipWhitespace(buf);
			
			Object data = parseValue(buf);
			
			if (!ret.add(name, data))
			{
				throw new JsonParseException("Duplicate key: %s", name);
			}
			
			skipWhitespace(buf);
			
			tkn = buf.next();
			
			if (!",".equalsIgnoreCase(tkn.str))
			{
				if (kill)
				{
					throw new JsonParseException("Found \"%s\" at line %s, col %s; Was expecting a comma", tkn.str, tkn.line, tkn.col);
				}
				
				kill = true;
				
			}
			
			skipWhitespace(buf);
			
		}
		
		return ret;
	}
	
	private static JsonArray parseArray(Buffer<Token> buf) throws JsonParseException
	{
		Token tkn = buf.next();
		
		if (!"[".equalsIgnoreCase(tkn.str))
		{
			throw new JsonParseException("Found \"%s\" at line %s, col %s; Was expecting \"[\"", tkn.str, tkn.line, tkn.col);
		}
		
		JsonArray ret = new JsonArray();
		boolean kill = false;
		
		while (!"]".equalsIgnoreCase((tkn = buf.next(false)).str))
		{
			skipWhitespace(buf);
			
			ret.add(parseValue(buf));
			
			skipWhitespace(buf);
			
			if (!",".equalsIgnoreCase((tkn = buf.next()).str))
			{
				if (kill)
				{
					throw new JsonParseException("Found \"%s\" at line %s, col %s; Was expecting \",\"!", tkn.str, tkn.line, tkn.col);
				}
				
				kill = true;
				
			}
			
			skipWhitespace(buf);
			
		}
		
		return ret;
	}
	
	private static Number parseInt(Buffer<Token> buf) throws JsonParseException
	{
		Token tkn = buf.next(false);
		boolean neg = tkn.str.equalsIgnoreCase("-"), isFloat = false;
		
		if (neg)
		{
			buf.skip();
			
		}
		
		StringBuilder b = new StringBuilder();
		
		String i = gatherInts(buf);
		
		if (i.equalsIgnoreCase(""))
		{
			throw new JsonParseException("Invalid integer found! what.");
		}
		
		b.append(gatherInts(buf));
		
		tkn = buf.next();
		
		if (".".equalsIgnoreCase(tkn.str))
		{
			b.append(".");
			
			i = gatherInts(buf);
			
			if ("".equalsIgnoreCase(i))
			{
				throw new JsonParseException("Invalid floating point found on line %s, column %s", tkn.line, tkn.col);
			}
			
			b.append(i);
			
			tkn = buf.next();
			
			isFloat = true;
			
		}
		
		if ("e".equalsIgnoreCase(tkn.str))
		{
			b.append(tkn.str);
			
			tkn = buf.next(false);
			
			if ("+".equalsIgnoreCase(tkn.str) || "-".equalsIgnoreCase(tkn.str))
			{
				b.append(tkn.str);
				
				buf.skip();
				
			}
			
			i = gatherInts(buf);
			
			if (i.equalsIgnoreCase(""))
			{
				throw new JsonParseException("Invalid integer found after finding E on line %s, column %s", tkn.line, tkn.col);
			}
			
			b.append(i);
			
		}
		
		return isFloat ? Double.parseDouble(b.toString()) : Long.parseLong(b.toString());
	}
	
	private static String gatherInts(Buffer<Token> buf)
	{
		Token tkn = buf.next();
		StringBuilder b = new StringBuilder();
		boolean empty = true;
		
		while (ParseHelper.isInt(tkn.str))
		{
			b.append(tkn.str);
			empty = false;
			tkn = buf.next();
			
		}
		
		if (empty)
		{
			throw new JsonParseException("Found \"%s\" at line %s, col %s; Was expecting a number literal", tkn.str, tkn.line, tkn.col);
		}
		
		return b.toString();
	}
	
}
