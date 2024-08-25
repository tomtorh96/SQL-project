
import java.io.Serializable;

public class Answers implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String answer;
	private boolean correct;
	private static int counter = 0;
	private int aId;
	public Answers(String answer, boolean correct) {
		aId = ++counter;
		this.answer = answer;
		this.correct = correct;
	}
	public Answers(Answers other) {
		this.answer = other.answer;
		this.correct = other.correct;
		this.aId=other.aId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	public void setAid(int id)
	{
		this.aId=id;
	}
	public int getID()
	{
		return aId;
	}
	@Override
	public String toString() {
		return answer;
	}
	
	
}

