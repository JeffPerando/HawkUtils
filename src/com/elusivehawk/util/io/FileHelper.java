
package com.elusivehawk.util.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.elusivehawk.util.CompInfo;
import com.elusivehawk.util.parse.ParseHelper;

/**
 * 
 * Helper class for handling {@link File}s.
 * 
 * @author Elusivehawk
 */
public final class FileHelper
{
	public static final String FILE_SEP = System.getProperty("file.separator");
	
	private FileHelper(){}
	
	public static File createFile(String path)
	{
		return new File(fixPath(path));
	}
	
	public static File createFile(String src, String path)
	{
		return new File(fixPath(src), fixPath(path));
	}
	
	public static File createFile(File src, String path)
	{
		return new File(src, fixPath(path));
	}
	
	public static File getRootResDir()
	{
		try
		{
			String urlpath = FileHelper.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			
			if (urlpath != null)
			{
				return new File(urlpath);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	public static File getResourceFile(String path)
	{
		File root = getRootResDir();
		
		if (root != null)
		{
			return new File(root, path);
		}
		
		return null;
	}
	
	public static InputStream getResourceStream(String path)
	{
		if (CompInfo.BUILT)
		{
			return FileHelper.class.getResourceAsStream(path);
		}
		
		return createInStream(createFile(CompInfo.JAR_DIR.getParentFile(), fixPath(path)));
	}
	
	public static FileInputStream createInStream(File file)
	{
		if (!canRead(file))
		{
			return null;
		}
		
		FileInputStream ret = null;
		
		try
		{
			ret = new FileInputStream(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static FileReader createReader(File file)
	{
		if (!canRead(file))
		{
			return null;
		}
		
		FileReader ret = null;
		
		try
		{
			ret = new FileReader(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static FileOutputStream createOutStream(File file, boolean create)
	{
		if (file == null)
		{
			return null;
		}
		
		if (!file.exists() && create)
		{
			try
			{
				if (!file.createNewFile())
				{
					return null;
				}
				
			}
			catch (Exception e)
			{
				return null;
			}
			
		}
		
		if (!file.isFile())
		{
			return null;
		}
		
		if (!file.canWrite())
		{
			return null;
		}
		
		FileOutputStream ret = null;
		
		try
		{
			ret = new FileOutputStream(file);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static FileWriter createWriter(File file, boolean create)
	{
		return createWriter(file, create, false);
	}
	
	public static FileWriter createWriter(File file, boolean create, boolean append)
	{
		if (file == null)
		{
			return null;
		}
		
		if (!file.isFile())
		{
			return null;
		}
		
		if (!file.exists() && create)
		{
			try
			{
				if (!file.createNewFile())
				{
					return null;
				}
				
			}
			catch (Exception e)
			{
				return null;
			}
			
		}
		
		if (!file.canWrite())
		{
			return null;
		}
		
		FileWriter ret = null;
		
		try
		{
			ret = new FileWriter(file, append);
			
		}
		catch (Exception e){}
		
		return ret;
	}
	
	public static boolean isReal(File file)
	{
		return file != null && file.exists();
	}
	
	public static boolean canRead(File file)
	{
		return isReal(file) && file.isFile() && file.canRead();
	}
	
	public static String fixPath(String path)
	{
		return path.replace("/", FILE_SEP);
	}
	
	public static String makePathGeneric(String path)
	{
		return path.replace(FILE_SEP, "/");
	}
	
	public static List<File> getFiles(File file)
	{
		return getFiles(file, null);
	}
	
	public static List<File> getFiles(File file, FileFilter filter)
	{
		List<File> ret = new ArrayList<File>();
		
		IOHelper.scanForFiles(file, ((f) ->
		{
			if (filter.accept(f))
			{
				ret.add(f);
				
			}
			
			return true;
		}));
		
		return ret;
	}
	
	public static String getExtensionlessName(File file)
	{
		return ParseHelper.getPrefix(file.getName(), ".");
	}
	
	public static String getExtension(File file)
	{
		String ret = ParseHelper.getSuffix(file.getName(), ".");
		
		if (ret == null)
		{
			return null;
		}
		
		return ret.toLowerCase();
	}
	
	public static File getChild(String name, File folder)
	{
		File[] children = folder.listFiles();
		
		if (children == null || children.length == 0)
		{
			return null;
		}
		
		for (File file : children)
		{
			if (file.getName().equals(name))
			{
				return file;
			}
			
		}
		
		return null;
	}
	
	public static File getChild(String name, List<File> files)
	{
		if (files == null || files.isEmpty())
		{
			return null;
		}
		
		for (File file : files)
		{
			if (file.getPath().endsWith(name))
			{
				return file;
			}
			
		}
		
		return null;
	}
	
}
