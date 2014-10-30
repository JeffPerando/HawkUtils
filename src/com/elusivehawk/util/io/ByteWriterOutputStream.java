
package com.elusivehawk.util.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class ByteWriterOutputStream extends OutputStream
{
	private final IByteWriter writer;
	
	@SuppressWarnings("unqualified-field-access")
	public ByteWriterOutputStream(IByteWriter w)
	{
		assert w != null;
		
		writer = w;
		
	}
	
	@Override
	public void write(int i) throws IOException
	{
		this.writer.write((byte)(i & 0xFF));
		
	}
	
}
