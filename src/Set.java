
import java.io.Serializable;
import java.util.Arrays;

public class Set<T extends Answers> implements Serializable {
	private int numOfAnswers;
	private int arraySize = 10;
	private Object[] totAnswers;

	public Set() {
		this.totAnswers = new Object[arraySize];
		this.numOfAnswers = 0;
	}

	public boolean add(T theAnswer) throws myException {
		if (!theAnswer.getClass().getSimpleName().equalsIgnoreCase("Answers")) {
			throw new myException("no answer has entered");
		}
		if (arraySize - numOfAnswers <= 1) {
			arreyExtend();
		}
		if (theAnswer != null && !Exists(theAnswer)) {
			totAnswers[numOfAnswers++] = theAnswer;
			return true;
		}
		return false;

	}

	public void arreyExtend() {
		arraySize *= 2;
		Object[] temp = new Object[arraySize];
		for (int i = 0; i < numOfAnswers; i++)
			temp[i] = totAnswers[i];
		this.totAnswers = temp;
	}

	public boolean Exists(T theAnswer) {
		if (lastIndexOf(theAnswer) != -1) {
			return true;
		}
		return false;
	}

	public int lastIndexOf(T theAnswer) {
		T temp;
		for (int i = 0; i < numOfAnswers; i++) {
			temp = (T) totAnswers[i];
			if (temp.getAnswer().equals(theAnswer.getAnswer())) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return "set [numOfAnswers=" + numOfAnswers + ", arraySize=" + arraySize + ", totAnswers="
				+ Arrays.toString(totAnswers) + "]";
	}

}
