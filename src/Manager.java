
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Manager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Questions> allQuestions;
	private int numberOfQUestions = 0;
	private LocalDate date;
	private static int counter = 0;
	private int tId;
	public static final String F_Name = "exam.txt";

	public Manager() {
		tId = ++counter;
		allQuestions = new ArrayList<Questions>();
		date = LocalDate.now();
	}

	public Manager(Manager other) {
		this.allQuestions = (ArrayList<Questions>) other.allQuestions.clone();
		this.numberOfQUestions = other.allQuestions.size();
		this.date = other.date;
		this.tId = other.tId;
	}

	public ArrayList<Questions> getallQuestions() {
		return allQuestions;
	}

	public LocalDate getLocalDate() {
		return date;
	}
	public String getDate() {

		return date.toString();
	}

	public Questions getQuestion(int index) {
		return allQuestions.get(index);
	}
	public Questions getTheQuestion(int id)
	{
		for (int i = 0; i < numberOfQUestions; i++) {
			if(allQuestions.get(i).getQId() == id)
			{
				return allQuestions.get(i);
			}
			
		}
		return null;
	}

	public int getTestID() {
		return tId;
	}

	public boolean setQuestion(Questions theQuestion, int index) throws myException {
		if (theQuestion.getDescription() == null || theQuestion.getDescription().equals("")) {
			throw new myException("no input has entered");
		}
		if (allQuestions.get(index) == null) {
			throw new myException("can't change an empty question");
		}
		allQuestions.set(index, theQuestion);
		return true;
	}

	public int getNumberOfQUestions() {
		return numberOfQUestions;
	}

	public boolean addAnswer(int index, Answers theAnswer) throws myException {
		return allQuestions.get(index).addAnswer(theAnswer);
	}

	public boolean addQuestion(Questions theQuestion) throws myException {
		if (theQuestion.getDescription().equals("") || theQuestion.getDescription() == null) {
			throw new myException("no input has entered");
		}
		if (allQuestions.isEmpty() || allQuestions.get(numberOfQUestions - 1) != null) {
			allQuestions.ensureCapacity(allQuestions.size() * 2);
		}
		for (int i = 0; i < allQuestions.size() && allQuestions.get(i) != null; i++) {
			if (allQuestions.get(i).equals(theQuestion)) {
				return false;
			}
		}
		allQuestions.add(theQuestion);
		numberOfQUestions++;
		return true;
	}

	public void SelectionSort(Manager test) throws myException {
		int n = test.getNumberOfQUestions();
		int i, IndMax; // IndMax is for the index of the maximum
		for (; n > 1; n--) {
			for (IndMax = 0, i = 1; i < n; i++)
				if (test.getQuestion(IndMax).compareTo(test.getQuestion(i)) < 0)
					IndMax = i;
			swap(test, n - 1, IndMax);
		}
	}

	public void SelectionSortBySize(Manager test) throws myException {
		int n = test.getNumberOfQUestions();
		int i, IndMax;
		for (; n > 1; n--) {
			for (IndMax = 0, i = 1; i < n; i++)
				if (legthOfAnswers(test.getQuestion(IndMax)) < legthOfAnswers(test.getQuestion(i))) {

					IndMax = i;
				}
			swap(test, n - 1, IndMax);
		}
	}

	public int legthOfAnswers(Questions theQestion) {
		return theQestion.getAllAnswers().length();

	}

	public boolean saveTest(ArrayList<Manager> totalTest) throws IOException, FileNotFoundException {
		String nameExam = "exam_" + totalTest.get(totalTest.size() - 1).getDate() + ".txt";
		String nameSolution = "Solution_" + totalTest.get(totalTest.size() - 1).getDate() + ".txt";
		PrintWriter pwE = new PrintWriter(nameExam);

		pwE.println("exam_" + totalTest.get(totalTest.size() - 1).getDate());
		pwE.println(totalTest.get(totalTest.size() - 1).printTest());
		pwE.close();
		PrintWriter pwS = new PrintWriter(nameSolution);

		pwS.println("exam_" + totalTest.get(totalTest.size() - 1).getDate());
		pwS.println(totalTest.get(totalTest.size() - 1).printAnswers());
		pwS.close();

		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(F_Name));
		o.writeObject(totalTest);
		o.close();
		return true;
	}

	public ArrayList<Manager> readAll() throws FileNotFoundException, IOException, ClassNotFoundException {
		try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(F_Name))) {
			return (ArrayList<Manager>) read.readObject();
		}
	}

	// Swap two elements in array
	public void swap(Manager test, int i, int j) throws myException {
		Questions tmp = test.getQuestion(i);
		test.setQuestion(test.getQuestion(j), i);
		test.setQuestion(tmp, j);

	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < numberOfQUestions; i++) {
			str += "question " + (i + 1) + ": " + allQuestions.get(i).toString() + "\n";
		}
		return str;
	}

	public String printTest() {
		String str = "here's your test: \n";
		for (int i = 0; i < numberOfQUestions; i++) {
			str += "\nQuestion " + (i + 1) + ") " + allQuestions.get(i).print() + "\n";
			if (allQuestions.get(i).getClass().getSimpleName().equalsIgnoreCase("ChoiceQuestion")) {
				str += allQuestions.get(i).getAllAnswers();
			}

		}
		return str;
	}

	public String printAnswers() {
		String str = "here's is your answers: \n";
		for (int i = 0; i < numberOfQUestions; i++) {
			str += "\nAnswer for question" + (i + 1) + ") " + allQuestions.get(i).getAnAnswer(0).toString() + "\n";
		}
		return str;
	}

	public String print() {
		String str = "here's your test: \n";
		for (int i = 0; i < numberOfQUestions; i++) {
			str += "\nQuestion " + (i + 1) + ") " + allQuestions.get(i).print() + "\n";
			if (allQuestions.get(i).getClass().getSimpleName().equalsIgnoreCase("ChoiceQuestion")) {
				str += allQuestions.get(i).getAllAnswers();
			}
			str += "\nthe correct answer is: " + allQuestions.get(i).getAnAnswer(0) + " \n";
		}
		return str;
	}

}
