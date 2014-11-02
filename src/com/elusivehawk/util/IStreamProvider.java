
package com.elusivehawk.util;

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
	InputStream getIn(String name);
	
	default OutputStream getOut(String name)
	{
		return null;
	}
	
}
