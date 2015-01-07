
package com.elusivehawk.util.parse.json;

import java.io.File;
import java.io.Reader;
import java.util.List;
import com.elusivehawk.util.io.IOHelper;
import com.elusivehawk.util.parse.ParseHelper;
import com.elusivehawk.util.parse.Token;
import com.elusivehawk.util.parse.Tokenizer;
import com.elusivehawk.util.storage.Buffer;
import com.elusivehawk.util.storage.Tuple;

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
	
	public static void skipWhitespace(Buffer<Token> buf)
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
	
	public static JsonValue<?> parse(File file) throws JsonParseException
	{
		return parse(IOHelper.readText(file));
	}
	
	public static JsonValue<?> parse(Reader r) throws JsonParseException
	{
		return parse(IOHelper.readText(r));
	}
	
	public static JsonValue<?> parse(List<String> strs) throws JsonParseException
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
		
		if (!(ret instanceof JsonValue))
		{
			throw new JsonParseException("Was expecting a JSON value (Object, array), got \"%s\"", ret);
		}
		
		skipWhitespace(buf);
		
		if (buf.hasNext())
		{
			throw new JsonParseException("");
		}
		
		return (JsonValue<?>)ret;
	}
	
	public static Tuple<String, Object> parseKeypair(Buffer<Token> buf)  throws JsonParseException
	{
		skipWhitespace(buf);
		
		String name = parseString(buf);
		
		skipWhitespace(buf);
		
		Token tkn = buf.next();
		
		if (!":".equalsIgnoreCase(tkn.str))
		{
			throw new JsonParseException("Found \"%s\" for keypair %s at line %s, col %s; Was expecting \":\"", tkn.str, name, tkn.line, tkn.col);
		}
		
		skipWhitespace(buf);
		
		return Tuple.create(name, parseValue(buf));
	}
	
	public static Object parseValue(Buffer<Token> buf) throws JsonParseException
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
	
	public static String parseString(Buffer<Token> buf)
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
	
	public static JsonObject parseObj(Buffer<Token> buf) throws JsonParseException
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
			Tuple<String, Object> v = parseKeypair(buf);
			
			if (!ret.add(v.one, v.two))
			{
				throw new JsonParseException("Duplicate key: %s", v.one);
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
	
	public static JsonArray parseArray(Buffer<Token> buf) throws JsonParseException
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
	
	public static Number parseInt(Buffer<Token> buf) throws JsonParseException
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
	
	public static String gatherInts(Buffer<Token> buf)
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
