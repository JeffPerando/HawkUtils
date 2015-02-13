
package com.elusivehawk.util.io;

import java.nio.ByteBuffer;
import java.util.UUID;
import com.elusivehawk.util.Logger;

/**
 * 
 * Convenience interface for writing bytes.
 * 
 * @author Elusivehawk
 */
public interface IByteWriter
{
	int write(byte... bytes);
	
	default void flush(){}
	
	default int write(ByteBuffer bb)
	{
		return this.write(bb, bb.remaining());
	}
	
	default int write(ByteBuffer bb, int count)
	{
		byte[] bs = new byte[Math.min(bb.remaining(), count)];
		
		bb.get(bs);
		
		return this.write(bs);
	}
	
	default int write(IByteReader r)
	{
		return this.write(r, r.remaining());
	}
	
	default int write(IByteReader r, int count)
	{
		try
		{
			byte[] b = r.read(count);
			
			return this.write(b);
		}
		catch (Throwable e)
		{
			Logger.err(e);
			
		}
		
		return -1;
	}
	
	default void writeBool(boolean b)
	{
		this.write((byte)(b ? 1 : 0));
		
	}
	
	default void writeDouble(double d)
	{
		Serializers.DOUBLE.toBytes(d, this);
		
	}
	
	default void writeFloat(float f)
	{
		Serializers.FLOAT.toBytes(f, this);
	}
	
	default void writeInt(int i)
	{
		Serializers.INTEGER.toBytes(i, this);
		
	}
	
	default void writeLong(long l)
	{
		Serializers.LONG.toBytes(l, this);
		
	}
	
	default void writeShort(short s)
	{
		Serializers.SHORT.toBytes(s, this);
		
	}
	
	default void writeString(String str)
	{
		Serializers.STRING.toBytes(str, this);
		
	}
	
	default void writeUUID(UUID uuid)
	{
		Serializers.UUID.toBytes(uuid, this);
		
	}
	
}
