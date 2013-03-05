package data;

import io.IO;

import java.util.ArrayList;

public class Question {

	private String[] questionList = new String[5];
	
	public Question(String q) {
		this.questionList[0] = q;
		this.questionList[1] = ", ";
		this.questionList[2] = "this ";
		this.questionList[3] = "is ";
		this.questionList[4] = "byte communication!";
	}
	
	public String[] getQuestionArray() {
		return questionList;
	}
	
}
