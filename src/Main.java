
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static final String F_Name = "exam.txt";
	public static Scanner s = new Scanner(System.in);
	public static SQL mySql = new SQL();

	/**
	 * to have the ability to do a test for a subject separately we would change the
	 * type of Manager newTest to Manager[] newTest that its cell in the array is a
	 * stock of questions for a certain subject
	 */
	public static void main(String[] args) {
		int ans = 0;
		Manager Manager = new Manager();
		ArrayList<Manager> totalTests = new ArrayList<Manager>();
		Set<Answers> SetAnswer = new Set<Answers>();
		File file = new File(F_Name);

		mySql.SchemaCreate();
		mySql.getInfo(Manager);
		/*
		 * if (file.exists()) { try { totalTests = (ArrayList<Manager>)
		 * Manager.readAll().clone();
		 * 
		 * System.out.println("load test"); } catch (ClassNotFoundException |
		 * IOException e) { System.out.println("cant load new test"); } }
		 */
		if (Manager.getNumberOfQUestions() == 0) {
			try {
				// 1
				Manager.addQuestion(new OpenQuestion("4 * 5 = ?", new Answers("20", true)));
				// 2
				Manager.addQuestion(new ChoiceQuestion("30 * 5 = ?"));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("5", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("150", true));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("200", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("105", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("160", false));
				// 3
				Manager.addQuestion(new OpenQuestion("is today wednesday?", new Answers("yes", true)));
				// 4
				Manager.addQuestion(new OpenQuestion("is today monday?", new Answers("no", true)));
				// 5
				Manager.addQuestion(new OpenQuestion("when to trains coliding", new Answers("16:30", true)));
				// 6
				Manager.addQuestion(new ChoiceQuestion("Square root of 49 is?"));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("7", true));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("-7", true));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("5", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("13.6", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("8", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("150", false));
				// 7
				Manager.addQuestion(
						new OpenQuestion("does this project deserves 100?", new Answers("of course", true)));
				// 8
				Manager.addQuestion(new ChoiceQuestion("if KOKO and TOTO fought for a cake who deserve it?"));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("KOKO", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("TOTO", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("no one deserve to get it", false));
				Manager.addAnswer(Manager.getNumberOfQUestions() - 1, new Answers("Keren Kalif", true));
				mySql.createTables();
				mySql.createTest(Manager);
				mySql.addToQuestion(Manager);
				mySql.insertAnswers(Manager);

			} catch (myException e) {
				System.out.println(e.getMessage());
			}
		}
		try {
			totalTests.add(Manager);
			totalTests.get(totalTests.size() - 1).saveTest(totalTests);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			for (int i = 0; i < Manager.getNumberOfQUestions(); i++) {
				for (int j = 0; j < Manager.getQuestion(i).getNumberOfAnswers(); j++) {
					SetAnswer.add(Manager.getQuestion(i).getAnAnswer(j));
				}
			}
		} catch (myException e) {
			System.out.println(e.getMessage());
		}

		do {
			System.out.println();
			System.out.println("Choose one of the following options:");
			System.out.println("1)  show all questions and answers");
			System.out.println("2)  add a question and answers");
			System.out.println("3)  update a question");
			System.out.println("4)  update an answer");
			System.out.println("5)  delete an answer");
			System.out.println("6)  manual build a quiz");
			System.out.println("7)  automatic build a quiz");
			System.out.println("8) 	EXIT");
			System.out.print("Enter your choice --> ");
			try {
				ans = Integer.parseInt(s.nextLine());
				switch (ans) {
				case 1:
					// System.out.println("\n" + Manager);// printing all the questing and its
					// answers
					mySql.TablesToString();
					break;
				case 2:
					System.out.println();
					addQuesting(Manager, SetAnswer); // adding a questing and its answer/s
					break;
				case 3:
					showQuestins(Manager);
					System.out.println();
					updateQuestion(Manager);
					// Updating a question"
					break;
				case 4:
					showQuestins(Manager);
					System.out.println();
					System.out.println("please choose the number question to change:");
					updateAnswer(Manager, Integer.parseInt(s.nextLine()) - 1);
					// Updating an answer
					break;
				case 5:
					showQuestins(Manager);
					System.out.println();
					removeAnswer(Manager);
					break;

				case 6:
					System.out.println();
					manualTest(Manager, totalTests);
					// manual build a quiz
					break;
				case 7:
					System.out.println();
					automaticTest(Manager, totalTests);
					// automatic build a quiz
					break;
				case 8:
					System.out.println();
					System.out.println("goodbye :)");
					break;
				default:
					System.out.println();
					System.out.println("Invalid option");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("\nno number has entered please enter a number\n");
			}
		} while (ans != 8);

	}

	public static void addQuesting(Manager theManager, Set<Answers> setAnswer) {
		String ans;
		String quest;
		String questingAnswer;
		boolean flag = false;
		boolean hasAdded = false;
		int answerIndex;
		do {
			try {
				System.out.println(
						"what kind of question is it? \nchoice question or an open question \nwrite open or choice");
				ans = s.nextLine();
				if (ans.equalsIgnoreCase("open")) {
					System.out.println("write the question:");
					quest = s.nextLine();
					System.out.println("write the answer:");
					questingAnswer = s.nextLine();

					if (theManager.addQuestion(new OpenQuestion(quest, new Answers(questingAnswer, true)))) {
						System.out.println("the question added successfully");
						hasAdded = true;
						mySql.addQ(theManager, theManager.getQuestion(theManager.getNumberOfQUestions() - 1), 1);
						mySql.addAnswer(theManager.getQuestion(theManager.getNumberOfQUestions() - 1),
								theManager.getQuestion(theManager.getNumberOfQUestions() - 1).getAnAnswer(0));
						setAnswer.add(theManager.getQuestion(theManager.getNumberOfQUestions() - 1).getAnAnswer(0));
					} else {
						System.out.println("the question wasn't added EXITING");
						return;
					}

				} else if (ans.equalsIgnoreCase("choice")) {
					System.out.println("write the question:");
					quest = s.nextLine();
					System.out.println("how many answers are there?: ");
					answerIndex = Integer.parseInt(s.nextLine());
					if (answerIndex < 1 || answerIndex > 8) {
						System.out.println("incorrect input EXITING");
						return;
					}
					theManager.addQuestion(new ChoiceQuestion(quest));
					mySql.addQ(theManager, theManager.getQuestion(theManager.getNumberOfQUestions() - 1), answerIndex);
					for (int i = 0; i < answerIndex;) {// מספר תשובות
						System.out.println("enter an answer: ");
						questingAnswer = s.nextLine();
						System.out.println("is it the correct answer? (yes / no) ");
						ans = s.nextLine();
						if (ans.equalsIgnoreCase("yes")) {
							flag = true;
						} else if (ans.equalsIgnoreCase("no")) {
							flag = false;
						}
						if (!(theManager.addAnswer(theManager.getNumberOfQUestions() - 1,
								new Answers(questingAnswer, flag)))) {
							System.out.println("the answer wasn't added");
						} else {
							System.out.println("answer added");
							mySql.addAnswer(theManager.getQuestion(theManager.getNumberOfQUestions() - 1),
									theManager.getQuestion(theManager.getNumberOfQUestions() - 1)
											.getAnAnswer(theManager.getQuestion(theManager.getNumberOfQUestions() - 1)
													.getNumberOfAnswers() - 1));
							setAnswer.add(theManager.getQuestion(theManager.getNumberOfQUestions() - 1).getAnAnswer(i));
							i++;
						}
					}

					System.out.println("the question added successfully");
					hasAdded = true;
				} else {
					System.out.println("!!! incorrct input try again !!!\n");

				}
			} catch (myException e) {
				System.out.println(e.getMessage() + "\n");
			}

		} while (!hasAdded);
	}

	public static void updateQuestion(Manager theManager) {
		boolean hasUpdate = false;
		String newQuestion;
		System.out.println("please choose the number question to change:");
		int ans = Integer.parseInt(s.nextLine());
		do {
			try {
				if (theManager.getQuestion(ans - 1) != null) {
					System.out.println("this is a " + theManager.getQuestion(ans - 1).getClass().getSimpleName()
							+ "\nwhat is the new question:");
					newQuestion = s.nextLine();
					if (theManager.getQuestion(ans - 1).setDescription(newQuestion)) {
						System.out.println("the question changed succrssfully");
						mySql.editQuestion(theManager.getQuestion(ans - 1));
						hasUpdate = true;
					}
				} else {
					System.out.println("there is no question here");
					return;
				}

			} catch (myException e) {
				System.out.println(e.getMessage());
			} catch (IndexOutOfBoundsException e) {
				System.out.println("out of bound");
			}
		} while (!hasUpdate);
	}

	public static void updateAnswer(Manager theManager, int index) {
		int answerIndex;
		String ans;
		boolean flag;
		boolean hasUpdate = false;
		try {
			System.out.println(theManager.getQuestion(index));
			System.out.println("please write the new answer:");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("out of bound EXITING");
			return;

		}
		do {
			try {
				String newAnswer = s.nextLine();
				if (theManager.getQuestion(index).getClass().getSimpleName().equalsIgnoreCase("OpenQuestion")) {
					if (theManager.getQuestion(index).setAnswer(newAnswer, true, 0)) {
						System.out.println("the answer was changed successfully");
						mySql.editAnswer(theManager.getQuestion(index), 0);
						hasUpdate = true;
					}
				} else if (theManager.getQuestion(index).getClass().getSimpleName()
						.equalsIgnoreCase("ChoiceQuestion")) {
					System.out.println("is it the correct answer? (yes/no)");
					ans = s.nextLine();
					if (ans.equalsIgnoreCase("yes")) {
						flag = true;
					} else if (ans.equalsIgnoreCase("no")) {
						flag = false;
					} else {
						System.out.println("incorrect input EXITING");
						return;

					}
					System.out.println("which answer you wish to change: ");
					answerIndex = Integer.parseInt(s.nextLine());
					if (theManager.getQuestion(index).setAnswer(newAnswer, flag, answerIndex - 1)) {
						System.out.println("the answer was changed successfully");
						mySql.editAnswer(theManager.getQuestion(index), answerIndex - 1);
						hasUpdate = true;
					} else {
						System.out.println("the answer was not changed successfully");
					}
				} else {
					System.out.println("there are no question here");
					return;
				}
			} catch (myException e) {
				System.out.println(e.getMessage());
			} catch (IndexOutOfBoundsException e) {
				System.out.println("out of bound");
			}

		} while (!hasUpdate);
	}

	public static void showQuestins(Manager theManager) {
		for (int i = 0, k = 1; i < theManager.getNumberOfQUestions(); i++) {
			if (theManager.getQuestion(i) != null) {
				System.out.println(k + ") " + theManager.getQuestion(i).getDescription());
				k++;
			}
		}
	}

	public static void removeAnswer(Manager theManager) {
		boolean hasRemove = false;
		do {
			System.out.println("please choose the number question to change enter 0 to exit:");
			try {
				int ans = Integer.parseInt(s.nextLine());
				if (ans == 0) {
					return;
				}
				if ((theManager.getQuestion(ans - 1).getClass().getSimpleName().equalsIgnoreCase("OpenQuestion"))) {
					System.out.println("its an open question can't remove that answer");

				} else {
					ChoiceQuestion temp = (ChoiceQuestion) theManager.getQuestion(ans - 1);
					if (temp.getNumberOfAnswers() == 0) {
						System.out.println("cant remove an answer in a question with no answers in it");

					} else {
						System.out.println("These are the question: \n");
						System.out.println(theManager.getQuestion(ans - 1).toString());
						System.out.println("Which answer do you want to remove? \n");
						ans = Integer.parseInt(s.nextLine());
						if (temp.removeAnswer(ans - 1, mySql, theManager.getTestID())) {
							System.out.println("succeed");
							hasRemove = true;
						} else {
							System.out.println("you can't remove that answer");

						}
					}
				}

			} catch (NumberFormatException e) {
				System.out.println("no number has entered try again");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("out of bound");
			}
		} while (!hasRemove);
	}

	public static void manualTest(Manager theManager, ArrayList<Manager> totalTests) {
		int numQ;
		int num;
		int numAnswer;
		String ans;
		boolean moreThenOne;
		Manager newTest = new Manager();
		ChoiceQuestion tempChoice;
		OpenQuestion tempOpen;
		Answers tempAnswer;
		System.out.println("do u want to create a new test with an old test? (yes/no)");
		ans = s.nextLine();
		if (ans.equalsIgnoreCase("yes")) {
			System.out.println("choose a test:\n");
			for (int i = 0; i < totalTests.size(); i++) {
				System.out.println("test" + (i + 1) + ":\n" + totalTests.get(i) + "\n");
			}
			System.out.println("choose want test you want to use:\n");
			try {
				num = Integer.parseInt(s.nextLine());
				if (num > totalTests.size() || num < 1) {
					throw new myException("out of bound");
				}
				newTest = new Manager(totalTests.get(num - 1));
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("input out of bound");
			} catch (myException e) {
				System.out.println(e.getMessage());
			} catch (NumberFormatException | NullPointerException e) {
				System.out.println("invlid input");
			}
		} else if (ans.equalsIgnoreCase("no")) {
			System.out.println("how many questions do you want?");
			num = Integer.parseInt(s.nextLine());
			if (num > theManager.getNumberOfQUestions()) {
				System.out.println("there not enough questions please add more");
				return;
			}
			try {
				for (int i = 0; i < num;) {
					System.out.println(theManager);
					System.out.println("which question you want? ");
					numQ = Integer.parseInt(s.nextLine());
					if (numQ > theManager.getNumberOfQUestions() || numQ < 1) {
						System.out.println("there is no answer here");
						return;
					} else if (theManager.getQuestion(numQ - 1).getClass().getSimpleName()
							.equalsIgnoreCase("OpenQuestion")) {
						tempOpen = (OpenQuestion) theManager.getQuestion(numQ - 1);
						tempAnswer = (Answers) theManager.getQuestion(numQ - 1).getAnAnswer(0);
						if (newTest.addQuestion(new OpenQuestion(tempOpen))) {
							i++;
							newTest.getQuestion(newTest.getNumberOfQUestions() - 1).getAnAnswer(0)
									.setAid(tempAnswer.getID());
						} else {
							System.out.println("I couldn't add the question please try again\n");
						}
					} else {
						tempChoice = (ChoiceQuestion) theManager.getQuestion(numQ - 1);
						if (newTest.addQuestion(new ChoiceQuestion(tempChoice.getDescription()))) {
							newTest.getQuestion(newTest.getNumberOfQUestions() - 1).setQid(tempChoice.getQId());
							System.out.println("\nwhat answers do you want to use? (press 0 to finish)");
							numAnswer = Integer.parseInt(s.nextLine());
							while (numAnswer != 0) {
								tempAnswer = tempChoice.getAnAnswer(numAnswer - 1);

								if (numAnswer > tempChoice.getNumberOfAnswers()
										|| tempChoice.getAnAnswer(numAnswer - 1) == null
												| !(newTest.addAnswer(i, tempAnswer))) {
									System.out.println("i could not add the answer");
								} else {
									System.out.println("Answer added");
									newTest.getQuestion(i).getAnAnswer(newTest.getQuestion(i).getNumberOfAnswers() - 1)
											.setAid(tempAnswer.getID());
								}

								numAnswer = Integer.parseInt(s.nextLine());
							}
							tempChoice = (ChoiceQuestion) newTest.getQuestion(i);
							if ((tempChoice.numberOfCorrectAnswers() > 1)) {
								tempChoice.falseAnswers();
								moreThenOne = true;
							} else {
								moreThenOne = false;
							}
							newTest.addAnswer(i, new Answers("more then one answer is correct", moreThenOne));
							newTest.addAnswer(i,
									new Answers("no answer is correct", (tempChoice.numberOfCorrectAnswers() == 0)));

							i++;
						} else {
							System.out.println("I couldn't add the question please try again\n");
						}
					}
				}
				newTest.SelectionSortBySize(newTest);
				totalTests.add(newTest);
			} catch (myException e) {
				System.out.println(e.getMessage() + "EXITING\n");

				return;
			} catch (IndexOutOfBoundsException e) {
				System.out.println("error with out of bound. test no saved EXITING\n");

				return;
			} catch (NumberFormatException e) {
				System.out.println("invalid input EXITING\n");

				return;
			}

			try {
				totalTests.get(totalTests.size() - 1).saveTest(totalTests);
			} catch (FileNotFoundException e) {
				System.out.println("can't save the file");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("invlid input EXITING");
			return;
		}
		mySql.addNewTest(newTest);
		System.out.println(newTest.print());

	}

	public static void automaticTest(Manager theManager, ArrayList<Manager> totalTests) {
		Manager newTest = new Manager();
		ChoiceQuestion tempChoice;
		OpenQuestion tempOpen;
		Answers tempAnswer;
		int ans;
		boolean thereIsTrue;
		System.out.println("how many questions do you want?");
		int num = Integer.parseInt(s.nextLine());

		if (num > theManager.getNumberOfQUestions()) {
			System.out.println("there not enough questions please add more");
			return;
		}
		try {
			for (int i = 0; i < num;) {
				ans = (int) (Math.random() * theManager.getNumberOfQUestions());

				if (theManager.getQuestion(ans).getClass().getSimpleName().equals("OpenQuestion")) {
					tempOpen = (OpenQuestion) theManager.getQuestion(ans);
					if (newTest.addQuestion(new OpenQuestion(tempOpen))) {
						i++;
						newTest.getQuestion(newTest.getNumberOfQUestions() - 1).setQid(tempOpen.getQId());
					}

				} else {
					tempChoice = (ChoiceQuestion) theManager.getQuestion(ans);
					if (newTest.addQuestion(new ChoiceQuestion(tempChoice.getDescription()))) {
						thereIsTrue = false;
						newTest.getQuestion(newTest.getNumberOfQUestions() - 1).setQid(tempChoice.getQId());
						for (int j = 0; j < 4 && j != tempChoice.getNumberOfAnswers();) {
							int randomAnswer = (int) (Math.random() * tempChoice.getNumberOfAnswers());
							tempAnswer = (Answers) theManager
									.getTheQuestion(newTest.getQuestion(newTest.getNumberOfQUestions() - 1).getQId())
									.getAnAnswer(randomAnswer);
							if (tempChoice.getAnAnswer(randomAnswer) != null
									&& (thereIsTrue != true || tempChoice.getAnAnswer(randomAnswer).isCorrect() != true)
									&& newTest.addAnswer(i, tempAnswer)) {
								newTest.getQuestion(i).getAnAnswer(j).setAid(tempAnswer.getID());
								j++;
								if (tempChoice.getAnAnswer(randomAnswer).isCorrect()) {
									thereIsTrue = true;
								}
							}

						}
						tempChoice = (ChoiceQuestion) newTest.getQuestion(i);
						newTest.addAnswer(i, new Answers("more then one answer is correct", false));
						newTest.addAnswer(i,
								new Answers("no answer is correct", (tempChoice.numberOfCorrectAnswers() == 0)));
						i++;
					}
				}
			}
			mySql.addNewTest(newTest);
			newTest.SelectionSort(newTest);
			System.out.println(newTest.print());
			totalTests.add(newTest);
			totalTests.get(totalTests.size() - 1).saveTest(totalTests);
		} catch (FileNotFoundException e) {
			System.out.println("can't save the file");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (myException e) {
			System.out.println(e.getMessage());
		}

	}

}
