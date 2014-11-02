
package com.elusivehawk.util.network;

/**
 * 
 * The core interface for networking; Implement this to your game instance, or a dedicated networking manager.
 * 
 * @author Elusivehawk
 */
public interface INetworkMaster
{
	int getEncryptionBitCount();
	
	boolean handshake(Connection connection, IPacket pkt);
	
}
