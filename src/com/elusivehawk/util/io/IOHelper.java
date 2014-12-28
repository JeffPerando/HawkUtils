
package com.elusivehawk.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.elusivehawk.util.EnumLogType;
import com.elusivehawk.util.IObjFilter;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class IOHelper
{
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final Charset ASCII = Charset.forName("ASCII");
	
	public static void scanForFiles(File file, IFileScanner sc)
	{
		assert FileHelper.isReal(file);
		
		File[] files = file.listFiles();
		
		if (files == null || files.length == 0)
		{
			return;
		}
		
		for (File f : files)
		{
			if (f.isDirectory())
			{
				scanForFiles(f, sc);
				
			}
			
			if (!sc.scan(f))
			{
				return;
			}
			
		}
		
	}
	
	public static void readZip(File file, IZipScanner sc)
	{
		if (!FileHelper.canRead(file))
		{
			return;
		}
		
		if (file.isDirectory())
		{
			return;
		}
		
		String ext = FileHelper.getExtension(file);
		
		ZipFile zip = null;
		
		try
		{
			if ("zip".equals(ext))
			{
				zip = new ZipFile(file);
				
			}
			else if ("jar".equals(ext))
			{
				zip = new JarFile(file);
				
			}
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		
		if (zip != null)
		{
			Enumeration<? extends ZipEntry> entries = zip.entries();
			ZipEntry entry;
			
			while (entries.hasMoreElements())
			{
				entry = entries.nextElement();
				
				if (entry.isDirectory())
				{
					continue;
				}
				
				sc.take(zip, entry, entry.getName());
				
			}
			
			try
			{
				zip.close();
				
			}
			catch (Exception e)
			{
				Logger.err(e);
				
			}
			
		}
		
	}
	
	public static byte[] readBytes(File file)
	{
		return readBytes(FileHelper.createInStream(file), true);
	}
	
	public static byte[] readBytes(InputStream is)
	{
		return readBytes(is, true);
	}
	
	public static byte[] readBytes(InputStream is, boolean close)
	{
		if (is == null)
		{
			return new byte[0];
		}
		
		BufferedInputStream in = (is instanceof BufferedInputStream) ? (BufferedInputStream)is : new BufferedInputStream(is);
		byte[] ret = null;
		int off = 0;
		int i = 0;
		
		try
		{
			ret = new byte[in.available()];
			
			while ((i = in.read(ret, off, Math.min(1024, ret.length - off))) > 0)
			{
				off += i;
				
			}
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		finally
		{
			if (close)
			{
				try
				{
					in.close();
					
				}
				catch (Exception e)
				{
					Logger.err(e);
					
				}
				
			}
			
		}
		
		if (ret == null)
		{
			return new byte[0];
		}
		
		return ret;
	}
	
	public static List<String> readText(File file)
	{
		return readText(file, UTF_8);
	}
	
	public static List<String> readText(File file, Charset encoding)
	{
		return readText(file, encoding, null);
	}
	
	public static List<String> readText(File file, IObjFilter<String> filter)
	{
		return readText(file, UTF_8, filter);
	}
	
	public static List<String> readText(File file, Charset encoding, IObjFilter<String> filter)
	{
		return readText(FileHelper.createInStream(file), encoding, filter);
	}
	
	public static List<String> readText(byte[] bs)
	{
		return readText(bs, UTF_8);
	}
	
	public static List<String> readText(byte[] bs, Charset encoding)
	{
		return readText(bs, encoding, null);
	}
	
	public static List<String> readText(byte[] bs, IObjFilter<String> filter)
	{
		return readText(bs, UTF_8, filter);
	}
	
	public static List<String> readText(byte[] bs, Charset encoding, IObjFilter<String> filter)
	{
		return readText(new ByteArrayInputStream(bs), encoding, filter);
	}
	
	public static List<String> readText(IByteReader r)
	{
		return readText(r, UTF_8);
	}
	
	public static List<String> readText(IByteReader r, Charset encoding)
	{
		return readText(r, encoding, null);
	}
	
	public static List<String> readText(IByteReader r, IObjFilter<String> filter)
	{
		return readText(r, UTF_8, filter);
	}
	
	public static List<String> readText(IByteReader r, Charset encoding, IObjFilter<String> filter)
	{
		return readText(new ByteReaderInputStream(r), encoding, filter);
	}
	
	public static List<String> readText(InputStream is)
	{
		return readText(is, UTF_8);
	}
	
	public static List<String> readText(InputStream is, Charset encoding)
	{
		return readText(is, encoding, null);
	}
	
	public static List<String> readText(InputStream is, IObjFilter<String> filter)
	{
		return readText(is, UTF_8, filter);
	}
	
	public static List<String> readText(InputStream is, Charset encoding, IObjFilter<String> filter)
	{
		return readText(new InputStreamReader(is, encoding), filter);
	}
	
	public static List<String> readText(Reader r)
	{
		return readText(r, null);
	}
	
	public static List<String> readText(Reader r, IObjFilter<String> filter)
	{
		if (r == null)
		{
			return null;
		}
		
		List<String> text = new ArrayList<String>();
		BufferedReader br = new BufferedReader(r);
		
		try
		{
			for (String line = br.readLine(); line != null; line = br.readLine())
			{
				text.add(line);
				
			}
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		
		try
		{
			br.close();
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		
		if (filter != null)
		{
			for (int c = 0; c < text.size(); c++)
			{
				text.set(c, filter.filter(c, text.get(c)));
				
			}
			
		}
		
		return text;
	}
	
	public static String readTextToOneLine(File file)
	{
		return readTextToOneLine(file, UTF_8);
	}
	
	public static String readTextToOneLine(File file, Charset encoding)
	{
		return readTextToOneLine(FileHelper.createInStream(file), encoding);
	}
	
	public static String readTextToOneLine(byte[] bs)
	{
		return readTextToOneLine(bs, UTF_8);
	}
	
	public static String readTextToOneLine(byte[] bs, Charset encoding)
	{
		return readTextToOneLine(new ByteArrayInputStream(bs), encoding);
	}
	
	public static String readTextToOneLine(IByteReader r)
	{
		return readTextToOneLine(r, UTF_8);
	}
	
	public static String readTextToOneLine(IByteReader r, Charset encoding)
	{
		return readTextToOneLine(new ByteReaderInputStream(r), encoding);
	}
	
	public static String readTextToOneLine(InputStream is)
	{
		return readTextToOneLine(is, UTF_8);
	}
	
	public static String readTextToOneLine(InputStream is, Charset encoding)
	{
		return readTextToOneLine(new InputStreamReader(is, encoding));
	}
	
	public static String readTextToOneLine(Reader r)
	{
		if (r == null)
		{
			return null;
		}
		
		StringBuilder b = new StringBuilder();
		BufferedReader br = new BufferedReader(r);
		boolean prev = false;
		
		try
		{
			for (String line = br.readLine(); line != null; line = br.readLine())
			{
				if (prev)
				{
					b.append("\n");
					
				}
				else prev = true;
				
				b.append(line);
				
			}
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		finally
		{
			try
			{
				br.close();
				
			}
			catch (Exception e)
			{
				Logger.err(e);
				
			}
			
		}
		
		return b.toString();
	}
	
	public static boolean write(String path, boolean append, boolean makeFileIfNotFound, String... text)
	{
		return write(FileHelper.createFile(path), append, makeFileIfNotFound, text);
	}
	
	public static boolean write(String path, List<String> text, boolean append, boolean makeFileIfNotFound)
	{
		return write(FileHelper.createFile(path), text, append, makeFileIfNotFound);
	}
	
	public static boolean write(File file, boolean append, boolean makeFileIfNotFound, String... text)
	{
		return write(file, Arrays.asList(text), append, makeFileIfNotFound);
	}
	
	public static boolean write(File file, List<String> text, boolean append, boolean makeFileIfNotFound)
	{
		if (!file.exists() && makeFileIfNotFound)
		{
			try
			{
				file.createNewFile();
				
			}
			catch (Exception e)
			{
				Logger.err(e);
				
				return false;
			}
			
		}
		
		if (!file.canWrite())
		{
			Logger.log(EnumLogType.ERROR, "File with path %s cannot be written to! This is a bug!", file);
			
			return false;
		}
		
		BufferedWriter writer = new BufferedWriter(FileHelper.createWriter(file, true, append));
		
		try
		{
			for (String line : text)
			{
				writer.write(line);
				writer.newLine();
				
			}
			
			return true;
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		finally
		{
			try
			{
				writer.close();
				
			}
			catch (Exception e)
			{
				Logger.err(e);
				
			}
			
		}
		
		return false;
	}
	
	public static void filter(File txt, IObjFilter<String> filter)
	{
		List<String> text = readText(txt, filter);
		
		if (text != null)
		{
			write(txt, text, false, false);
			
		}
		
	}
	
	public static boolean write(byte[] bytes, File dest)
	{
		FileOutputStream fos = FileHelper.createOutStream(dest, true);
		
		if (fos == null)
		{
			return false;
		}
		
		BufferedOutputStream out = new BufferedOutputStream(fos);
		
		try
		{
			out.write(bytes);
			out.flush();
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
			return false;
		}
		
		return true;
	}
	
	public static class FilenameFilterWrapper implements FileFilter
	{
		private final FilenameFilter filter;
		
		@SuppressWarnings("unqualified-field-access")
		public FilenameFilterWrapper(FilenameFilter f)
		{
			filter = f;
			
		}
		
		@Override
		public boolean accept(File file)
		{
			return this.filter.accept(file, file.getName());
		}
		
	}
	
	@FunctionalInterface
	public static interface IFileScanner
	{
		boolean scan(File file);
		
	}
	
	public static interface IZipScanner
	{
		void take(ZipFile zip, ZipEntry entry, String name);
		
	}
	
}
