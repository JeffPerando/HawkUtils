
package com.elusivehawk.util.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@FunctionalInterface
public interface IStreamProvider
{
	InputStream getInStream(String str);
	
	default OutputStream getOutStream(String str)
	{
		return null;
	}
	
}
