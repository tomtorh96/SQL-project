
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface ManagerAble {
	public ArrayList<Questions> getallQuestions();

	public String getDate();

	public Questions getQuestion(int index);

	public int getNumberOfQUestions();

	public int legthOfAnswers(Questions theQestion);

	public boolean setQuestion(Questions theQuestion, int index) throws myException;

	public boolean addAnswer(int index, Answers theAnswer) throws myException;

	public boolean addQuestion(Questions theQuestion) throws myException;

	public void SelectionSort(Manager test);

	public void SelectionSortBySize(Manager test);

	public void swap(Manager test, int i, int j);

	public boolean saveTest(ArrayList<Manager> totalTest) throws IOException, FileNotFoundException;

	public ArrayList<Manager> readAll() throws FileNotFoundException, IOException, ClassNotFoundException;

	public String toString();

	public String printTest();

	public String printAnswers();

	public String print();

}
