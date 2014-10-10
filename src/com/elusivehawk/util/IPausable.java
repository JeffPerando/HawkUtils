
package com.elusivehawk.util;

/**
 * 
 * "Standardisation" interface for pausing things; Suspiciously similar to {@link IDirty}.
 * 
 * @author Elusivehawk
 */
public interface IPausable
{
	boolean isPaused();
	
	void setPaused(boolean p);
	
}
