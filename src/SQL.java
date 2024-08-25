import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	public final String pass = "1346790";
	public final String user = "tomer";
	public final String tables[] = {
			"CREATE TABLE test\r\n" + " (tId INTEGER not NULL,\r\n" + " numberOfQuestions integer not NULL,\r\n"
					+ " date date,\r\n" + " PRIMARY KEY ( Tid ))\r\n" + " ENGINE = InnoDB;\r\n",

			"CREATE TABLE Answers\r\n" + "(aId integer not null,\r\n" + "answer varchar(255),\r\n"
					+ "primary key(aId))\r\n" + " ENGINE = InnoDB;",

			"CREATE TABLE question\r\n" + "(qId integer not null,\r\n" + "description varchar(225),\r\n"
					+ "qType varchar(20),\r\n" + "primary key(qId))\r\n" + "ENGINE = InnoDB;\r\n",

			"CREATE TABLE TestToQuestionTable \r\n" + "(tId INTEGER not NULL,\r\n" + "qId integer not null,\r\n"
					+ "primary key(tid,qid),\r\n" + "foreign key(tid) references test(tid),\r\n"
					+ "foreign key(qid) references question(qid))\r\n" + "ENGINE = InnoDB;\r\n",

			"CREATE TABLE AnswerToQuestionTable\r\n" + "(qId  INTEGER not NULL,\r\n" + " aId integer not null,\r\n"
					+ "correct bool,\r\n" + "primary key(qId,aId),\r\n"
					+ "foreign key(aId) references answers(aId),\r\n" + "foreign key(qid) references question(qid))\r\n"
					+ "ENGINE = InnoDB;\r\n",

			"CREATE TABLE choiceQuestion\r\n" + "(tId integer not null,\r\n" + "qId integer not null ,\r\n"
					+ "numberOfAnswers integer not null,\r\n" + "foreign key(qId)references question (qId),\r\n"
					+ "foreign key(tId)references test (tId))\r\n" + " ENGINE = InnoDB;\r\n" };

	public void SchemaCreate() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			System.out.println("Creating Schema...");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306", user, pass);
			Statement stmt = connect.createStatement();
			String dbName = "testcreator";
			String sql = "CREATE SCHEMA " + dbName;
			stmt.executeUpdate(sql);
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work\n creating Schema");
		}
	}

	public void getInfo(Manager manager) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("getting info from DB");
			Statement stmt = connect.createStatement();
			Statement stmt2 = connect.createStatement();
			ResultSet rs2;
			ResultSet rs = stmt.executeQuery("select qId,description,qType\r\n" + "from question");
			int id;
			String question;
			String qType;
			String answer;
			boolean correct;
			Answers newAnswer;
			while (rs.next()) {
				question = rs.getString(2);
				qType = rs.getString(3);
				id = rs.getInt(1);
				if (qType.equalsIgnoreCase("OpenQuestion")) {
					manager.addQuestion(new OpenQuestion(question, new Answers("bla", true)));
				} else {
					manager.addQuestion(new ChoiceQuestion(question));
				}
				rs2 = stmt2.executeQuery("select answer,correct\r\n" + "from answers,answertoquestiontable\r\n" + "\r\n"
						+ "where answertoquestiontable.qId =" + id + "\r\n"
						+ "AND answers.aId = answertoquestiontable.aId");
				while (rs2.next()) {
					answer = rs2.getString(1);
					correct = rs2.getBoolean(2);
					newAnswer = new Answers(answer, correct);
					manager.getQuestion(manager.getNumberOfQUestions() - 1).addAnswer(newAnswer);
				}
				rs2.close();
			}
			rs.close();
			stmt.close();
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( geting info form DB");
		}
	}

	public void createTables() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("conected create Tables");
			Statement stmt = connect.createStatement();
			for (int i = 0; i < tables.length; i++) {
				stmt.executeUpdate(tables[i]);
			}
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( creating tables");
		}
	}

	public void createTest(Manager manager) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("creating a new Test");
			PreparedStatement pst = connect
					.prepareStatement("insert into test(tid,numberOfQuestions,date) value(?,?,?);");
			pst.setInt(1, manager.getTestID());
			pst.setInt(2, manager.getNumberOfQUestions());
			pst.setDate(3, Date.valueOf(manager.getLocalDate()));
			pst.executeUpdate();
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( create a the first test");
		}
	}

	public void insertAnswers(Manager manager) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("inserting Answers");
			PreparedStatement pst = connect.prepareStatement("insert into Answers(aId,answer) value(?,?);");
			PreparedStatement pst2 = connect
					.prepareStatement("insert into answertoquestiontable(qid,aid,correct) value(?,?,?);");
			for (int i = 0; i < manager.getNumberOfQUestions(); i++) {
				for (int j = 0; j < manager.getQuestion(i).getNumberOfAnswers(); j++) {
					pst.setInt(1, manager.getQuestion(i).getAnAnswer(j).getID());
					pst.setString(2, manager.getQuestion(i).getAnAnswer(j).getAnswer());
					pst.executeUpdate();

					pst2.setInt(1, manager.getQuestion(i).getQId());
					pst2.setInt(2, manager.getQuestion(i).getAnAnswer(j).getID());
					pst2.setBoolean(3, manager.getQuestion(i).getAnAnswer(j).isCorrect());
					pst2.executeUpdate();
				}

			}
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( inserting answers");
		}
	}

	public void addToQuestion(Manager manager) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("conected add to Question");
			PreparedStatement pst = connect
					.prepareStatement("INSERT INTO question(qid,description,qtype) value(?,?,?);");
			PreparedStatement pst2 = connect
					.prepareStatement("INSERT INTO choicequestion(tid,qid,numberOfAnswers) value(?,?,?);");
			PreparedStatement pst3 = connect.prepareStatement("INSERT INTO testtoquestiontable(tid,qid) value(?,?);");
			for (int i = 0; i < manager.getNumberOfQUestions(); i++) {
				pst.setInt(1, manager.getQuestion(i).getQId());
				pst.setString(2, manager.getQuestion(i).getDescription());
				String className = manager.getQuestion(i).getClass().getSimpleName();
				pst.setString(3, className);
				pst.executeUpdate();
				if (className.equalsIgnoreCase("ChoiceQuestion")) {
					pst2.setInt(1, manager.getTestID());
					pst2.setInt(2, manager.getQuestion(i).getQId());
					pst2.setInt(3, manager.getQuestion(i).getNumberOfAnswers());
					pst2.executeUpdate();
				}
				pst3.setInt(1, manager.getTestID());
				pst3.setInt(2, manager.getQuestion(i).getQId());
				pst3.executeUpdate();
			}
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( adding all questions to DB");
		}
	}

	public void addAnswer(Questions question, Answers a) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			PreparedStatement pst = connect.prepareStatement("INSERT INTO answers(aId,answer) value(?,?);");
			PreparedStatement pst2 = connect
					.prepareStatement("INSERT INTO answertoquestiontable(qid,aId,correct) value(?,?,?);");
			pst.setInt(1, a.getID());
			pst.setString(2, a.getAnswer());
			pst.executeUpdate();
			pst2.setInt(1, question.getQId());
			pst2.setInt(2, a.getID());
			pst2.setBoolean(3, a.isCorrect());
			pst2.executeUpdate();
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( add all answers to DB");
		}
	}

	public void addAnswerToQuestion(Manager manager, Questions q, Answers a) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			PreparedStatement pst = connect
					.prepareStatement("INSERT INTO answertoquestiontable(qId,aId,correct) value(?,?,?);");
			pst.setInt(1, q.getQId());
			pst.setInt(2, a.getID());
			pst.setBoolean(1, a.isCorrect());

			pst.executeUpdate();
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( add an answer to DB");
		}
	}

	public void addQ(Manager manager, Questions q, int numOfAnswers) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			PreparedStatement pst = connect
					.prepareStatement("INSERT INTO question(qid,description,qtype) value(?,?,?);");
			PreparedStatement pst2 = connect
					.prepareStatement("INSERT INTO choicequestion(tid,qid,numberOfAnswers) value(?,?,?);");
			pst.setInt(1, q.getQId());
			pst.setString(2, q.getDescription());
			String className = q.getClass().getSimpleName();
			pst.setString(3, className);
			pst.executeUpdate();
			if (className.equalsIgnoreCase("ChoiceQuestion")) {
				pst2.setInt(1, manager.getTestID());
				pst2.setInt(2, q.getQId());
				pst2.setInt(3, numOfAnswers);
				pst2.executeUpdate();
			}
			pst2 = connect.prepareStatement("update test set numberOfQuestions = ? where tid = ?");
			pst2.setInt(1, manager.getNumberOfQUestions());
			pst2.setInt(2, manager.getTestID());
			pst2.executeUpdate();
			pst2 = connect.prepareStatement("INSERT INTO testtoquestiontable(tid,qid) value(?,?);");
			pst2.setInt(2, q.getQId());
			pst2.setInt(1, manager.getTestID());
			pst2.executeUpdate();
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( a Question to DB");
		}
	}

	public void TablesToString() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("Printing table");
			Statement stmt = connect.createStatement();
			Statement stmt2 = connect.createStatement();
			ResultSet rs2;
			ResultSet rs = stmt.executeQuery("select qid,description\r\n" + "from question");
			int id;
			String question;
			String answer;
			boolean correct;
			while (rs.next()) {
				question = rs.getString(2);
				System.out.println(question);
				id = rs.getInt(1);
				rs2 = stmt2.executeQuery("select answer,correct\r\n" + "from answers,answertoquestiontable\r\n"
						+ "where answertoquestiontable.qId =" + id + "\r\n"
						+ "AND answers.aId = answertoquestiontable.aId");
				while (rs2.next()) {
					answer = rs2.getString(1);
					correct = rs2.getBoolean(2);
					System.out.println(answer + " " + correct);
				}
				rs2.close();
				System.out.println();
			}
			rs.close();
			stmt.close();
		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( Printing all question from db");
		}
	}

	public void editAnswer(Questions question, int index) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("edit answer");
			PreparedStatement pst = connect.prepareStatement("update Answers set answer = ? where aid = ?;");
			PreparedStatement pst2 = connect
					.prepareStatement("update answertoquestiontable set correct = ? where qid = ? and aid = ?;");

			pst.setString(1, question.getAnAnswer(index).getAnswer());
			pst.setInt(2, question.getAnAnswer(index).getID());
			pst2.setBoolean(1, question.getAnAnswer(index).isCorrect());
			pst2.setInt(2, question.getQId());
			pst2.setInt(3, question.getAnAnswer(index).getID());
			pst.executeUpdate();
			pst2.executeUpdate();

		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( edit answer");
		}

	}

	public void deleteAnswer(Answers answer, int qid, int tid, int newAmount) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("edit answer");
			PreparedStatement pst = connect.prepareStatement(" DELETE from Answers where aid = ?;");
			PreparedStatement pst2 = connect
					.prepareStatement("DELETE from answertoquestiontable  where qid = ? and aid = ?;");
			PreparedStatement pst3 = connect
					.prepareStatement("update choicequestion set numberOfAnswers = ? where qid = ? and tid = ?");

			pst.setInt(1, answer.getID());

			pst2.setInt(1, qid);
			pst2.setInt(2, answer.getID());

			pst3.setInt(1, newAmount);
			pst3.setInt(2, qid);
			pst3.setInt(3, tid);
			pst2.executeUpdate();
			pst.executeUpdate();
			pst3.executeUpdate();

		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( deleting answer");
		}

	}

	public void editQuestion(Questions question) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("edit answer");
			PreparedStatement pst = connect.prepareStatement("update question set description = ? where qid = ?;");

			pst.setString(1, question.getDescription());
			pst.setInt(2, question.getQId());
			pst.executeUpdate();

		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( edit question");
		}

	}

	public void addNewTest(Manager test) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver worked :)");
			Connection connect;
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testcreator", user, pass);
			System.out.println("edit answer");
			PreparedStatement pst = connect.prepareStatement("INSERT INTO testtoquestiontable(tid,qid) value(?,?);");
			PreparedStatement pst2 = connect
					.prepareStatement("INSERT INTO choicequestion(tid,qid,numberOfAnswers) value(?,?,?);");
			PreparedStatement pst3 = connect
					.prepareStatement("insert into test(tid,numberOfQuestions,date) value(?,?,?);");
			pst3.setInt(1, test.getTestID());
			pst3.setInt(2, test.getNumberOfQUestions());
			pst3.setDate(3, Date.valueOf(test.getLocalDate()));
			pst3.executeUpdate();
			for (int i = 0; i < test.getNumberOfQUestions(); i++) {
				pst.setInt(1, test.getTestID());
				pst.setInt(2, test.getQuestion(i).getQId());
				pst.executeUpdate();
				if (test.getQuestion(i).getClass().getSimpleName().equals("ChoiceQuestion")) {
					pst2.setInt(1, test.getTestID());
					pst2.setInt(2, test.getQuestion(i).getQId());
					pst2.setInt(3, test.getQuestion(i).getNumberOfAnswers());
					pst2.executeUpdate();
				}
			}

		} catch (SQLException esql) {
			System.out.println(esql.getMessage());
		} catch (Exception e) {
			System.out.println("driver didnt work :( creating a new test");
		}
	}

}
