package data;

import io.IO;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class QuestionController {

	// TODO Create properties file system
	private static String PATH;
	
	private ArrayList<Question> _questionList = new ArrayList<Question>();
	
	public QuestionController() {
		PATH = Question.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "resources/questions.txt";
	}
	
	public void loadQuestions() {
		
		Question objQuestion = null;
		String qFileOutput = IO.readFile(PATH);
		String[] buffer;
		
		buffer = qFileOutput.split(Pattern.quote("$~"));
		
		for(int i = 0; i < buffer.length; i++) {
			
			if (buffer[i].matches("\\[[0-99]]")) {
				
				if (objQuestion != null)
					_questionList.add(objQuestion);
				
				objQuestion = new Question();
				objQuestion.setAnswerIndex(Integer.parseInt(buffer[i].replaceAll("[\\[\\]]", "")));
				
			} else {
				if (objQuestion != null) {
					
					if (buffer[i].contains("[") && buffer[i].contains("]")) {
						objQuestion.setQuestionText(buffer[i].replaceAll("[\\[\\]]", ""));
					} else {
						objQuestion.addQuestionItem(buffer[i]);
					}
				}
			}
		}
		
		
	}
	
	public void transmitQuestions() {
		
		
		
	}
	
}
