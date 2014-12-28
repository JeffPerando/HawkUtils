
package com.elusivehawk.util.parse;

import java.net.URL;
import java.util.Calendar;
import java.util.List;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.Logger;
import com.elusivehawk.util.storage.Pair;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ParseHelper
{
	public static final String[] NUMBERS =	{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public static final String[] HEX =		{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
	
	public static final String[] WHITESPACE=	{" ", "\t", "\n"};
	public static final String[] ESCAPES =		{"\b", "\t", "\n", "\f", "\r", "\"", "\'", "\\"};
	public static final String[] S_ESCAPES =	{"\\b", "\\t", "\\n", "\\f", "\\r", "\\\"", "\\\'", "\\\\"};
	
	public static String replaceLast(String str, String in, String out)
	{
		int lastIn = str.lastIndexOf(in);
		
		if (lastIn == -1)
		{
			Logger.log(EnumLogType.ERROR, "Failed to remove last instance of %s from %s", in, str);
			
			return str;
		}
		
		StringBuilder b = new StringBuilder(str);
		b.replace(lastIn, lastIn + in.length(), out);
		
		return b.toString();
	}
	
	public static String concat(String separator, String d, String... strs)
	{
		return concat(separator, "", d, strs);
	}
	
	public static String concat(String separator, String endWith, String d, String... strs)
	{
		if (strs == null || strs.length == 0)
		{
			return d;
		}
		
		StringBuilder b = new StringBuilder(strs.length * 2);
		
		for (int c = 0; c < strs.length; ++c)
		{
			if (c > 0)
			{
				b.append(separator);
				
			}
			
			b.append(strs[c]);
			
		}
		
		b.append(endWith);
		
		return b.toString();
	}
	
	public static String concat(List<String> strs, String separator, String d)
	{
		return concat(strs, separator, "", d);
	}
	
	public static String concat(List<String> strs, String separator, String endWith, String d)
	{
		if (strs == null || strs.size() == 0)
		{
			return d;
		}
		
		StringBuilder b = new StringBuilder(strs.size() * 2);
		
		for (int c = 0; c < strs.size(); ++c)
		{
			if (c > 0)
			{
				b.append(separator);
				
			}
			
			b.append(strs.get(c));
			
		}
		
		b.append(endWith);
		
		return b.toString();
	}
	
	public static Pair<String> splitFirst(String str, String out)
	{
		if (str == null || "".equals(str))
		{
			return null;
		}
		
		if (str.length() < out.length())
		{
			return null;
		}
		
		if (str.equalsIgnoreCase(out))
		{
			return new Pair<String>("", "");
		}
		
		int ind = str.indexOf(out);
		
		if (ind == -1)
		{
			return null;
		}
		
		return new Pair<String>(str.substring(0, ind), str.substring(ind + out.length()));
	}
	
	public static Pair<String> splitLast(String str, String out)
	{
		if (str == null || "".equals(str))
		{
			return null;
		}
		
		if (str.length() < out.length())
		{
			return null;
		}
		
		if (str.equalsIgnoreCase(out))
		{
			return new Pair<String>("", str);
		}
		
		int ind = str.lastIndexOf(out);
		
		if (ind == -1)
		{
			return null;
		}
		
		return new Pair<String>(str.substring(0, ind), str.substring(ind + out.length()));
	}
	
	public static String getPrefix(String str, String out)
	{
		if (str == null || str.length() == 0)
		{
			return null;
		}
		
		int i = str.indexOf(out);
		
		if (i == -1)
		{
			return str;
		}
		
		return str.substring(0, i);
	}
	
	public static String getSuffix(String str, String out)
	{
		if (str == null || str.length() == 0)
		{
			return null;
		}
		
		int i = str.lastIndexOf(out);
		
		if (i == -1)
		{
			return null;
		}
		
		return str.substring(i + out.length());
	}
	
	public static String parseDate(Calendar cal, String dateSep, String timeSep)
	{
		StringBuilder b = new StringBuilder(16);
		
		b.append(cal.get(Calendar.DATE));
		b.append(dateSep);
		b.append(cal.get(Calendar.MONTH) + 1);
		b.append(dateSep);
		b.append(cal.get(Calendar.YEAR));
		b.append(" ");
		
		int minute = cal.get(Calendar.MINUTE);
		
		b.append(cal.get(Calendar.HOUR));
		b.append(timeSep);
		b.append((minute < 10 ? "0" : ""));
		b.append(minute);
		b.append(timeSep);
		b.append(cal.get(Calendar.SECOND));
		b.append(timeSep);
		b.append(cal.get(Calendar.MILLISECOND));
		b.append(" ");
		b.append(cal.get(Calendar.AM_PM) == Calendar.PM ? "PM" : "AM");
		
		return b.toString();
	}
	
	public static String valueOf(String str)
	{
		String ret = str;
		
		if (str.startsWith("\\u"))
		{
			ret = ((Character)((char)Short.parseShort(String.format("0x%s", str.substring(3))))).toString();
			
		}
		else
		{
			for (int c = 0; c < S_ESCAPES.length; c++)
			{
				if (S_ESCAPES[c].equalsIgnoreCase(str))
				{
					ret = ESCAPES[c];
					break;
				}
				
			}
			
		}
		
		return ret;
	}
	
	public static String sanitizeEscapeSequence(String str)
	{
		String ret = str;
		
		for (int c = 0; c < ESCAPES.length; c++)
		{
			ret = ret.replace(ESCAPES[c], S_ESCAPES[c]);
			
		}
		
		return ret;
	}
	
	public static boolean isInt(String str)
	{
		if (str == null || "".equalsIgnoreCase(str))
		{
			return false;
		}
		
		for (String n : NUMBERS)
		{
			if (str.equalsIgnoreCase(n))
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	public static boolean isHexInt(String str)
	{
		if (str == null || "".equalsIgnoreCase(str))
		{
			return false;
		}
		
		for (String n : HEX)
		{
			if (str.equalsIgnoreCase(n))
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	public static String[] asArray(List<String> strs)
	{
		return strs.toArray(new String[strs.size()]);
	}
	
	public static boolean isWhitespace(String str)
	{
		for (int c = 0; c < WHITESPACE.length; c++)
		{
			if (WHITESPACE[c].equals(str))
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	public static String substring(String str, String prefix, String suffix)
	{
		int pIn = str.indexOf(prefix);
		
		if (pIn == -1)
		{
			return "";
		}
		
		pIn += prefix.length();
		
		int sIn = str.indexOf(suffix, pIn);
		
		if (sIn == -1)
		{
			return "";
		}
		
		return str.substring(pIn, sIn);
	}
	
	public static URL asURL(String str)
	{
		URL ret = null;
		
		try
		{
			ret = new URL(str);
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		
		return ret;
	}
	
	public static String asHexString(byte[] bytes)
	{
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		
		for (byte b : bytes)
		{
			ret.append(HEX[(b & 0b11110000) >> 4]);
			ret.append(HEX[(b & 0b00001111)]);
			
		}
		
		return ret.toString();
	}
	
}
