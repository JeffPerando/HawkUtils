
package com.elusivehawk.util.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class IncomingPacket extends DataInputStream implements IPacket
{
	private final byte[] data;
	
	@SuppressWarnings("unqualified-field-access")
	public IncomingPacket(byte[] bytes)
	{
		super(new ByteArrayInputStream(bytes));
		
		data = bytes;
		
	}
	
	@Override
	public boolean canWrite()
	{
		return false;
	}
	
	@Override
	public byte[] getData()
	{
		return this.data;
	}
	
}
