
import java.io.Serializable;
import java.util.Objects;

public abstract class Questions implements Comparable<Questions>,Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int counter = 0;
	private int qId;
	private String description;
	public Questions(String description) {
		qId = ++counter;
		this.description = description;
		
	}
	public Questions(Questions other) {
		this(other.description);
		this.qId = other.qId;

	}
	public void setQid(int id)
	{
		this.qId =id;
	}
	public int getQId() {
		return qId;
		
	}
	public String getDescription() {
		return description;
	}
	public boolean setDescription(String description) throws myException {
		
		if (description.equals("") || description == null) {
			throw new myException("no input has entered");
		}
		this.description = description;
		return true;
	}
	public String getAllAnswers() {
		return null;
	}
	public Answers getAnAnswer(int index) {
		return null;
	}
	public abstract boolean addAnswer(Answers answer) throws myException;
	//public abstract boolean setAnswer(Answers answer,int index) throws myException;
	public abstract boolean setAnswer(String answer,boolean correct,int index)throws myException;
	public abstract int getNumberOfAnswers();

	
	@Override
	public String toString() {
		return  getDescription();
	}
	
	public String print() {
		return  getDescription();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Questions other = (Questions) obj;
		return Objects.equals(description, other.description);
	}
	@Override
	public int compareTo(Questions o) {
		return description.compareTo(o.description);
	}
	
	
	
	
} 

