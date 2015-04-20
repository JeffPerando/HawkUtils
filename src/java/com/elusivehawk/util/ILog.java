
package com.elusivehawk.util;

import java.io.Closeable;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface ILog extends Closeable
{
	void log(LogInfo info, boolean verbose);
	
}
