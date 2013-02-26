package packet;

import java.io.Serializable;
import java.util.ArrayList;

import data.Question;

public class Packet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private PacketHeaders header;
	
	private String message;
	private ArrayList<Question> questionList;
	
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
	
	public String getMessage() {
		return message;
	}
	
	public ArrayList<Question> getQuestions() {
		return questionList;
	}
	
}
