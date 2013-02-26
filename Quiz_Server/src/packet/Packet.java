package packet;

import java.io.Serializable;

public class Packet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private PacketHeaders header;
	
	public Packet(int clientId, PacketHeaders head) {
		this.id = clientId;
		this.header = head;
	}
	
	public PacketHeaders getHeader() {
		return header;
	}
	
	public int getClientId() {
		return id;
	}
	
}
