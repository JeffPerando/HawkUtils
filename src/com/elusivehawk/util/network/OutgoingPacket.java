
package com.elusivehawk.util.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import com.elusivehawk.util.ReflectionHelper;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class OutgoingPacket extends DataOutputStream implements IPacket
{
	private final byte[] data;
	
	public OutgoingPacket()
	{
		this(NetworkConst.DATA_LENGTH);
		
	}
	
	@SuppressWarnings("unqualified-field-access")
	public OutgoingPacket(int size)
	{
		super(new ByteArrayOutputStream(size));
		
		data = (byte[])ReflectionHelper.getField(ByteArrayOutputStream.class, "buf", this.out);
		
	}
	
	@Override
	public boolean canWrite()
	{
		return true;
	}
	
	@Override
	public byte[] getData()
	{
		return this.data;
	}
	
}
