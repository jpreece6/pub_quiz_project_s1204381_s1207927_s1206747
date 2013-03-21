package data;

import io.IO;

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
		String[] test1 = new String[6];
		String[] test2 = new String[6];
		
		test1[0] = "$~Windows";
		test1[1] = "$~Which OS was built my Microsoft?";
		test1[2] = "$~OSX";
		test1[3] = "$~Linux";
		test1[4] = "$~Windows";
		test1[5] = "$~DOS";
		
		test2[0] = "$~JavaScript";
		test2[1] = "$~Which of these is not a programming language";
		test2[2] = "$~Java";
		test2[3] = "$~C++";
		test2[4] = "$~JavaScript";
		test2[5] = "$~C#";
		
		questionList.add(test1);
		questionList.add(test2);
	}
	
	public void loadQuestions(String path) {
		
	}
	
	/**************************************************
	 * FIX NEED TO CORRECT REMOVAL OF ANSWERS
	 **************************************************/
	/**
	 * Removes the answers from the array so that, the answers
	 * are not sent to the client.
	 * @return Returns a 2D String of questions without answers.
	 */
	public String[][] removeAnswersFromList() {
		no_answers = new String[questionList.size()][6];
		/**
		 * Loops through the questionsList, if the first element
		 * of the second array is selected don't add it to the new array.
		 */
		for (int i = 0; i < questionList.size(); i++) {
			for (int b = 0; b < 6; b++) {
				if (b != 0) {
					/***********************************************
					 * 2D ARRAY ONLY ACCESSING FIRST ARRAY ELEMENTS
					 ***********************************************/
					no_answers[i] = questionList.get(i);
				} else {
					//answers.add(questionList.get(b));
				}
			}
		}
		
		for (int i = 0; i < answers.size(); i++) {
			IO.println(answers.get(i));
		}
		
		return no_answers;
	}
	
	/**
	 * Converts the Question.class ArrayList into a 2D String array.
	 * @return Returns a 2D String array.
	 */
	private String[][] convertToStringArray() {
		final_list = new String[questionList.size()][6];
		for (int i = 0; i < questionList.size(); i++) {
			final_list[i] = questionList.get(i);
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
		return convertToStringArray();
	}
	
}
