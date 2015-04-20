
package com.elusivehawk.util.io;

import java.util.UUID;
import com.elusivehawk.util.Logger;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
@SuppressWarnings("boxing")
public final class Serializers
{
	private Serializers(){}
	
	public static final Serializer<Short> SHORT = new Serializer<Short>()
			{
				@Override
				public int toBytes(Short s, IByteWriter w)
				{
					return w.write((byte)(s & 255), (byte)((s >> 8) & 255));
				}
				
				@Override
				public Short fromBytes(IByteReader b)
				{
					try
					{
						return (short)(b.read() | (b.read() << 8));
					}
					catch (Throwable e)
					{
						Logger.err(e);
						
					}
					
					return 0;
				}
				
			};
	public static final Serializer<Integer> INTEGER = new Serializer<Integer>()
			{
				@Override
				public int toBytes(Integer i, IByteWriter w)
				{
					byte[] bytes = new byte[4];
					
					for (int c = 0; c < 4; c++)
					{
						bytes[c] = (byte)((i >> (c * 8)) & 0xFF);
						
					}
					
					return w.write(bytes);
				}
				
				@Override
				public Integer fromBytes(IByteReader b)
				{
					int ret = 0;
					
					for (int c = 0; c < 4; c++)
					{
						try
						{
							ret &= b.read() << (c * 8);
							
						}
						catch (Throwable e)
						{
							Logger.err(e);
							break;
						}
						
					}
					
					return ret;
				}
				
			};
	public static final Serializer<Long> LONG  = new Serializer<Long>()
			{
				@Override
				public int toBytes(Long l, IByteWriter w)
				{
					byte[] bytes = new byte[8];
					
					for (int c = 0; c < 8; c++)
					{
						bytes[c] = (byte)((l >> (c * 8)) & 0xFF);
						
					}
					
					return w.write(bytes);
				}
				
				@Override
				public Long fromBytes(IByteReader b)
				{
					long ret = 0;
					
					for (int c = 0; c < 8; c++)
					{
						try
						{
							ret &= b.read() << (c * 8);
							
						}
						catch (Throwable e)
						{
							Logger.err(e);
							break;
						}
						
					}
					
					return ret;
				}
				
			};
	public static final Serializer<Float> FLOAT = new Serializer<Float>()
			{
				@Override
				public int toBytes(Float f, IByteWriter w)
				{
					return INTEGER.toBytes(Float.floatToRawIntBits(f), w);
				}
				
				@Override
				public Float fromBytes(IByteReader b)
				{
					return Float.intBitsToFloat(INTEGER.fromBytes(b));
				}
				
			};
	public static final Serializer<Double> DOUBLE = new Serializer<Double>()
			{
				@Override
				public int toBytes(Double d, IByteWriter w)
				{
					return LONG.toBytes(Double.doubleToRawLongBits(d), w);
				}
				
				@Override
				public Double fromBytes(IByteReader b)
				{
					return Double.longBitsToDouble(LONG.fromBytes(b));
				}
				
			};
	public static final Serializer<String> STRING = new Serializer<String>()
			{
				@Override
				public int toBytes(String str, IByteWriter w)
				{
					INTEGER.toBytes(str.length(), w);
					
					for (int c = 0; c < str.length(); c++)
					{
						SHORT.toBytes((short)str.charAt(c), w);
						
					}
					
					return (str.length() * 2) + 4;
				}
				
				@Override
				public String fromBytes(IByteReader b)
				{
					char[] str = new char[INTEGER.fromBytes(b)];
					
					for (int c = 0; c < str.length; c++)
					{
						str[c] = (char)(short)SHORT.fromBytes(b);
						
					}
					
					return new String(str);
				}
				
			};
	public static final Serializer<UUID> UUID = new Serializer<UUID>()
			{
				@Override
				public int toBytes(UUID uuid, IByteWriter w)
				{
					return LONG.toBytes(uuid.getMostSignificantBits(), w) + LONG.toBytes(uuid.getLeastSignificantBits(), w);
				}
				
				@Override
				public UUID fromBytes(IByteReader r)
				{
					return new java.util.UUID(LONG.fromBytes(r), LONG.fromBytes(r));
				}
				
			};
	
}
