
package com.elusivehawk.util.json;

import java.io.File;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.elusivehawk.util.storage.Buffer;
import com.elusivehawk.util.string.StringHelper;
import com.elusivehawk.util.string.Token;
import com.elusivehawk.util.string.Tokenizer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public final class JsonParser
{
	public static final String[] SEPARATORS = {"\"", ":", ",", "{", "}", "[", "]", "e", "E", "+", "-"};
	
	private JsonParser(){}
	
	public static void skipWhitespace(Buffer<Token> buf)
	{
		Token tkn;
		
		while (buf.hasNext())
		{
			tkn = buf.next();
			
			if (!StringHelper.isWhitespace(tkn.str))
			{
				buf.rewind(1);
				
				return;
			}
			
		}
		
	}
	
	public static JsonData parse(File file) throws JsonParseException
	{
		return parse(StringHelper.read(file));
	}
	
	public static JsonData parse(Reader r) throws JsonParseException
	{
		return parse(StringHelper.read(r));
	}
	
	public static JsonData parse(List<String> strs) throws JsonParseException
	{
		if (strs == null || strs.isEmpty())
		{
			return null;
		}
		
		Tokenizer t = new Tokenizer();
		
		t.addTokens(SEPARATORS);
		t.addTokens(StringHelper.NUMBERS);
		t.addTokens(StringHelper.WHITESPACE);
		
		Buffer<Token> buf = new Buffer<Token>(t.tokenize(strs));
		
		return parseContent("", buf);
	}
	
	public static JsonData parseKeypair(Buffer<Token> buf)  throws JsonParseException
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
		
		return parseContent(name, buf);
	}
	
	public static JsonData parseContent(String name, Buffer<Token> buf) throws JsonParseException
	{
		Token tkn = buf.next(false);
		
		if (tkn == null)
		{
			throw new JsonParseException(new NullPointerException());
		}
		
		String str = tkn.str.toLowerCase();
		
		switch (str)
		{
			case "\"": return new JsonData(EnumJsonType.STRING, name, parseString(buf));
			case "{": return parseObj(name, buf);
			case "[": return parseArray(name, buf);
			case "null": return new JsonData(EnumJsonType.STRING, name, null);
			case "true":
			case "false": return new JsonData(EnumJsonType.BOOL, name, str);
			
		}
		
		if (StringHelper.isInt(str) || "-".equalsIgnoreCase(str))
		{
			return parseInt(name, buf);
		}
		
		throw new JsonParseException("Invalid value for key \"%s\" found at line %s, col %s: \"%s\"", name, tkn.line, tkn.col, StringHelper.sanitizeEscapeSequence(str));
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
			b.append(StringHelper.valueOf(tkn.str));
			
		}
		
		return b.toString();
	}
	
	public static JsonObject parseObj(String name, Buffer<Token> buf) throws JsonParseException
	{
		Token tkn = buf.next();
		
		if (!"{".equalsIgnoreCase(tkn.str))
		{
			throw new JsonParseException("Found \"%s\" at line %s, col %s; Was expecting \"{\"", tkn.str, tkn.line, tkn.col);
		}
		
		Map<String, JsonData> m = new HashMap<String, JsonData>();
		boolean kill = false;
		
		while (!"}".equalsIgnoreCase((tkn = buf.next(false)).str))
		{
			JsonData v = parseKeypair(buf);
			
			if (m.containsKey(v.key))
			{
				throw new JsonParseException("Duplicate key: %s", v.key);
			}
			
			m.put(v.key, v);
			
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
		
		return new JsonObject(name, m.values());
	}
	
	public static JsonArray parseArray(String name, Buffer<Token> buf) throws JsonParseException
	{
		Token tkn = buf.next();
		
		if (!"[".equalsIgnoreCase(tkn.str))
		{
			throw new JsonParseException("Found \"%s\" at line %s, col %s; Was expecting \"[\"", tkn.str, tkn.line, tkn.col);
		}
		
		List<JsonData> list = new ArrayList<JsonData>();
		boolean kill = false;
		
		while (!"]".equalsIgnoreCase((tkn = buf.next(false)).str))
		{
			skipWhitespace(buf);
			
			list.add(parseContent("", buf));
			
			skipWhitespace(buf);
			
			if (!",".equalsIgnoreCase((tkn = buf.next()).str))
			{
				if (kill)
				{
					throw new JsonParseException("Found \"%s\" at line %s, col %s; Remove this token!", tkn.str, tkn.line, tkn.col);
				}
				
				kill = true;
				
			}
			
			skipWhitespace(buf);
			
		}
		
		return new JsonArray(name, list);
	}
	
	public static JsonData parseInt(String name, Buffer<Token> buf) throws JsonParseException
	{
		Token tkn = buf.next(false);
		boolean neg = tkn.str.equalsIgnoreCase("-"), isFloat = false;
		
		if (neg)
		{
			buf.skip(1);
			
		}
		
		String i = gatherInts(buf);
		
		StringBuilder b = new StringBuilder();
		
		b.append(i);
		
		String str = buf.next().str;
		
		if (".".equalsIgnoreCase(str))
		{
			b.append(".");
			
			str = gatherInts(buf);
			
			if ("".equalsIgnoreCase(str))
			{
				throw new JsonParseException("Invalid floating point found on %s", name);
			}
			
			b.append(str);
			
			str = buf.next().str;
			
			isFloat = true;
			
		}
		
		if ("e".equalsIgnoreCase(str))
		{
			b.append(str);
			
			str = buf.next().str;
			
			if ("+".equalsIgnoreCase(str) || "-".equalsIgnoreCase(str))
			{
				b.append(str);
				
			}
			
			str = gatherInts(buf);
			
			if ("".equalsIgnoreCase(str))
			{
				throw new JsonParseException("Invalid error code found on %s", name);
			}
			
			b.append(str);
			
		}
		
		return new JsonData(isFloat ? EnumJsonType.FLOAT : EnumJsonType.INT, name, b.toString());
	}
	
	public static String gatherInts(Buffer<Token> buf)
	{
		Token tkn = buf.next();
		StringBuilder b = new StringBuilder();
		boolean empty = true;
		
		while (StringHelper.isInt(tkn.str))
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
