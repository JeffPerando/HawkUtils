
package com.elusivehawk.util.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteStreams implements IByteReader, IByteWriter, Closeable
{
	protected final InputStream in;
	protected final OutputStream out;
	
	public ByteStreams(InputStream is)
	{
		this(is, null);
		
	}
	
	public ByteStreams(OutputStream os)
	{
		this(null, os);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ByteStreams(InputStream is, OutputStream os)
	{
		in = is;
		out = os;
		
	}
	
	@Override
	public int remaining()
	{
		int ret = 0;
		
		try
		{
			ret = this.in.available();
		}
		catch (Exception e){}
		
		return ret;
	}
	
	@Override
	public byte read()
	{
		byte ret = -1;
		
		try
		{
			ret = (byte)this.in.read();
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		
		return ret;
	}
	
	@Override
	public byte[] readAll()
	{
		int rem = this.remaining();
		byte[] ret = new byte[rem];
		
		if (rem > 0)
		{
			try
			{
				this.in.read(ret);
				
			}
			catch (Exception e)
			{
				Logger.err(e);
				
			}
			
		}
		
		return ret;
	}
	
	@Override
	public void flush()
	{
		if (this.out != null)
		{
			try
			{
				this.out.flush();
			}
			catch (Exception e)
			{
				Logger.err(e);
				
			}
			
		}
		
	}
	
	@Override
	public int write(byte... bytes)
	{
		int written = 0;
		
		try
		{
			this.out.write(bytes);
			written = bytes.length;
			
		}
		catch (Exception e)
		{
			Logger.err(e);
			
		}
		
		return written;
	}
	
	@Override
	public void close() throws IOException
	{
		if (this.in != null)
		{
			this.in.close();
			
		}
		
		if (this.out != null)
		{
			this.out.close();
			
		}
		
	}
	
}
