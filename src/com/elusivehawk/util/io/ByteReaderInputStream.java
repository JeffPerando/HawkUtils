
package com.elusivehawk.util.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteReaderInputStream extends InputStream
{
	private final IByteReader r;
	private final boolean unsigned;
	
	public ByteReaderInputStream(IByteReader reader)
	{
		this(reader, false);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public ByteReaderInputStream(IByteReader reader, boolean unsign)
	{
		r = reader;
		unsigned = unsign;
		
	}
	
	@Override
	public int read() throws IOException
	{
		try
		{
			byte b = this.r.read();
			
			if (this.unsigned)
			{
				return Byte.toUnsignedInt(b);
			}
			
			return b;
		}
		catch (Throwable e)
		{
			throw new IOException(e);
		}
		
	}
	
	@Override
	public void close() throws IOException
	{
		if (this.r instanceof Closeable)
		{
			((Closeable)this.r).close();
			
		}
		
	}
	
}
