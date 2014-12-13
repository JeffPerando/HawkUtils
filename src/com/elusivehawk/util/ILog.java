
package com.elusivehawk.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILog extends Closeable
{
	void log(LogInfo info, boolean verbose);
	
	//XXX Default methods
	
	@Override
	default void close() throws IOException{}
	
}
