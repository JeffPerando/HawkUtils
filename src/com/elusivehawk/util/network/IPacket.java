
package com.elusivehawk.util.network;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public interface IPacket
{
	boolean canWrite();
	
	byte[] getData();
	
}
