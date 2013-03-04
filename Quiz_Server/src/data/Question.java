package data;

import java.util.ArrayList;

public class Question {

	private String[] questionList = new String[5];
	
	public Question(String q) {
		this.questionList[0] = q;
		this.questionList[1] = ", ";
		this.questionList[2] = "this ";
		this.questionList[3] = "is ";
		this.questionList[4] = "byte communication";
	}
	
	public String[] convertToStringArray(Question[] questions) {
		String[] final_array = new String[questions.length];
		for (int i = 0; i < questions.length; i++) {
			final_array[i] = (String) questions[i].toString();
		}
		
		return final_array;
	}
	
	public String[] getQuestionArray() {
		return questionList;
	}
	
	public byte[] convertToByteArray(String[] questions) {
		ArrayList<byte[]> byteList = new ArrayList<byte[]>();
		
		for (int i = 0; i < questions.length; i++) {
			byteList.add(questions[i].getBytes());
		}
		
		byte[] byte_list = new byte[1010];
		byte[][] list = new byte[5][1010];
		for (int i = 0; i < byteList.size(); i++) {

		}
		
		
		
		System.arraycopy(list[1], list[0].length, byte_list, list[0].length + 1, list[1].length);
		System.arraycopy(list[2], list[1].length, byte_list, list[1].length + 1, list[2].length);
		System.arraycopy(list[3], list[2].length, byte_list, list[2].length + 1, list[3].length);
		System.arraycopy(list[4], list[3].length, byte_list, list[3].length + 1, list[4].length);
		
		return byte_list;
	}
	
}
