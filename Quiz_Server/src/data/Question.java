package data;

import java.util.ArrayList;
import java.util.UUID;

public class Question {
	
	private final String _id;
	private String _questionText = "";
	private ArrayList<String> _questionData = new ArrayList<String>();
	private int _answerIndex;
	
	public Question() {
		UUID uid = UUID.randomUUID();
		this._id = uid.toString();
	}
	
	public String ID() {
		return _id;
	}
	
	public String getQuestionText() {
		return _questionText;
	}
	
	public void setQuestionText(String txt) {
		_questionText = txt;
	}
	
	public ArrayList<String> getData() {
		return _questionData;
	}
	
	public void addQuestionItem(String item) {
		_questionData.add(item);
	}
	
	public int getAnswerIndex() {
		return _answerIndex;
	}
	
	public void setAnswerIndex(int index) {
		_answerIndex = index;
	}
	
	public String getAnswer(int index) {
		return _questionData.get(index);
	}
}
