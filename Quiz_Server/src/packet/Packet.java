package packet;

import io.IO;

import java.nio.ByteBuffer;

import data.Question;

public class Packet {
	
	private byte[] byte_id;
	private byte[] byte_header;
	private byte[] byte_data;
	
	private byte[] packetHeader;
	private byte[] packet;
	
	
	public Packet(int id, PacketHeaders header, String msg) {
		try {
			byte_id = ByteBuffer.allocate(4).putInt(id).array();
			byte_header = ByteBuffer.allocate(4).putInt(header.ordinal()).array();
			byte_data = msg.getBytes("ASCII");
		
			packetHeader = new byte[byte_id.length + byte_header.length];
			packet = new byte[packetHeader.length + byte_data.length];
			System.arraycopy(byte_id, 0, packetHeader, 0, byte_id.length);
			System.arraycopy(byte_header, 0, packetHeader, byte_id.length, byte_header.length);
			System.arraycopy(packetHeader, 0, packet, 0, packetHeader.length);
			System.arraycopy(byte_data, 0, packet, packetHeader.length, byte_data.length);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Packet(int id, PacketHeaders header, Question questions) {
		try {
			byte_id = ByteBuffer.allocate(4).putInt(id).array();
			byte_header = ByteBuffer.allocate(4).putInt(header.ordinal()).array();
			//byte_data = questions.convertToByteArray(questions.getQuestionArray());
		
			packetHeader = new byte[byte_id.length + byte_header.length];
			packet = new byte[packetHeader.length];
			System.arraycopy(byte_id, 0, packetHeader, 0, byte_id.length);
			System.arraycopy(byte_header, 0, packetHeader, byte_id.length, byte_header.length);
			System.arraycopy(packetHeader, 0, packet, 0, packetHeader.length);
			//System.arraycopy(byte_data, 0, packet, packetHeader.length, byte_data.length);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public byte[] getDataForTransmit() {
		IO.println(Integer.toString(packet.length));
		return packet;
	}
}
