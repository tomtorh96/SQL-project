
public class OpenQuestion extends Questions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Answers answer;

	public OpenQuestion(String description, Answers answer) throws myException {
		super(description);
		addAnswer(answer);
	}

	public OpenQuestion(OpenQuestion other) {
		super(other.getDescription());
		this.answer = other.answer;
		
	}

	public Answers getAnAnswer(int index)
	{
		return answer;
	}

	public String getAllAnswers() {
		return answer.getAnswer();
	}


/*	public boolean setAnswer(Answers answer, int index) throws myException {

		if (answer.getAnswer() == null || answer.getAnswer().equals("")) {
			throw new myException("no input for an answer has entered");
		}
		this.answer = answer;
		return true;
	}*/
	@Override
	public boolean setAnswer(String answer, boolean correct, int index) throws myException {
		if(answer.equals(""))
		{
			throw new myException("no input for an answer has entered");
		}
		this.answer.setAnswer(answer);
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "\n" + answer.getAnswer()+"\n";
	}

	public String Print() {
		return super.print();
	}

	@Override
	public boolean addAnswer(Answers answer) throws myException {
		if (answer.getAnswer() == null || answer.getAnswer().equals("") ) {
			throw new myException("no input for an answer has entered");
		}
		this.answer= answer;
		return true;
	}

	@Override
	public int getNumberOfAnswers() {

		return 1;
	}



}
