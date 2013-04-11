package data;

import io.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Question {

	private ArrayList<String[]> questionList = new ArrayList<String[]>();
	private String[][] no_answers;
	private String[][] final_list;
	private ArrayList<String> answers = new ArrayList<String>();
	
	/**
	 * Creates a new Question object which will load and contain the
	 * questions for the quiz.
	 * @param num_questions Number of questions to load.
	 */
	public Question(int num_questions) {
		
		loadQuestions(Question.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "questions/questions.txt", num_questions);
	
	}
	
	/**
	 * Loads questions for the quiz from a text file (each question is 6 lines)
	 * @param path to the text file containing the questions
	 * @param num_questions number of questions to load from the text file
	 */
	public void loadQuestions(String path, int num_questions) {
		
		try {
			
			BufferedReader read = new BufferedReader(new FileReader(path));
			String current = "";
			
			String[] question = new String[6]; 
			int index = 0;
			int full_index = 0;
			
			
			while ((current = read.readLine()) != null) {
				
				if (full_index != (num_questions * 6)) {
					if (index <= 5) {
						question[index] = current;
						index++;
					} else {
						questionList.add(question);
						index = 0;
						
						question[index] = current;
						index++;
					}
				} else {
					break;
				}
				
				full_index++;
			}
			
			IO.println("Questions Loaded!");
			read.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	/*
	 * Removes the answers from the array so that, the answers
	 * are not sent to the client.
	 * @return Returns a 2D String of questions without answers.
	 */
	public String[][] removeAnswersFromList() {
		String[][] que = convertToStringArray(questionList);
		no_answers = new String[questionList.size()][5];
		/**
		 * Loops through the questionsList, if the first element
		 * of the second array is selected don't add it to the new array.
		 */
		for (int i = 0; i < que.length; i++) {
			for (int b = 0; b < 6; b++) {
				if (b != 0) {
					no_answers[i][b - 1] = que[i][b];
				} else {
					answers.add(que[i][b]);
				}
			}
		}
		
		return no_answers;
	}
	
	/**
	 * Converts the Question.class ArrayList into a 2D String array.
	 * @return Returns a 2D String array.
	 */
	private String[][] convertToStringArray(ArrayList<String[]> array) {
		final_list = new String[array.size()][5];
		for (int i = 0; i < array.size(); i++) {
			final_list[i] = array.get(i);
		}
		
		return final_list;
	}
	
	/**
	 * Returns the 2D String array of questions.
	 * @param removeAnswers If set to true the answers are removed from the 2D String array.
	 * @return Returns a 2D String array of questions.
	 */
	public String[][] getQuestionArray(boolean removeAnswers) {
		if (removeAnswers) {
			return removeAnswersFromList();
		}
		return convertToStringArray(questionList);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getAnswers() {
		return answers;
	}
}
