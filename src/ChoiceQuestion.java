
import java.util.ArrayList;

public class ChoiceQuestion extends Questions {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	private Answers[] answers;
	private ArrayList<Answers> answers;

	public ChoiceQuestion(String description) {
		super(description);
		this.answers = new ArrayList<Answers>();

	}

	public ChoiceQuestion(ChoiceQuestion other) {
		super(other.getDescription());
		other.answers = new ArrayList<Answers>();
	}

	public void falseAnswers() {
		for (int i = 0; i < answers.size(); i++) {
			answers.get(i).setCorrect(false);
		}
	}

	public String getAllAnswers() {
		String Str = "";
		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i) != null) {
				Str += (i + 1) + ") " + answers.get(i).getAnswer() + "\n";
			}
		}
		return Str;
	}


	public Answers getAnAnswer(int index) {
		if (index < 0 || index > answers.size()) {
			return null;
		}
		return answers.get(index);
	}

	public boolean removeAnswer(int index,SQL mySQl,int tid) {
		if (answers.get(index) != null) {
			mySQl.deleteAnswer(getAnAnswer(index),getQId(),tid,getNumberOfAnswers()-1);
			for (int i = index; i < answers.size()-1; i++) {

				answers.set(i, answers.get(i + 1));
			}
			answers.remove(answers.size()-1);
			
			return true;
		}
		return false;
	}

	@Override
	public boolean addAnswer(Answers theAnswer) throws myException {
		// in answers[answerIndex] == null there is an error add answer

		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i).getAnswer().equals(theAnswer.getAnswer())) {
				return false;
			}

		}
		if (theAnswer.getAnswer().equals("") || theAnswer.getAnswer() == null) {
			throw new myException("no input for an answer has entered");
		}
		answers.add(theAnswer);
		
		return true;

	}

	public int numberOfCorrectAnswers() {
		int num = 0;
		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i).isCorrect()) {
				num++;
			}

		}
		return num;
	}
	/*public boolean setAnswer(Answers TheAnswer, int index) throws myException {
		if (TheAnswer.getAnswer() == null || TheAnswer.getAnswer().equals("")) {
			throw new myException("no input for an answer has entered");
		}
		answers.set(index, TheAnswer);
		return true;
	}*/
	@Override
	public boolean setAnswer(String answer, boolean correct, int index)throws myException {
		if(answer.equals(""))
		{
			throw new myException("no input for an answer has entered");
		}
		answers.get(index).setAnswer(answer);
		answers.get(index).setCorrect(correct);
		return true;
	}

	public Answers getAnswer() {
		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i).isCorrect()) {
				return answers.get(i);
			}

		}
		return null;
	}

	@Override
	public String toString() {
		String str = super.toString() + "\n";
		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i) != null) {
				str += (i + 1) + ") " + answers.get(i).getAnswer() + " [" + answers.get(i).isCorrect() + "]\n";
			}
		}
		return str;
	}

	public String Print() {
		String str = super.print() + "\n";
		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i) != null) {
				str += (i + 1) + ") " + answers.get(i).getAnswer() + "\n";
			}
		}
		return str;
	}

	@Override
	public int getNumberOfAnswers() {
		return answers.size();
	}

	

	

}
