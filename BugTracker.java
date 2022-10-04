/**
 * BugTracker is a bug tracking system which aims to document bugs and issues
 * that may come across during any sort of development.
 * The program lets the user create a text file to print out formatted data
 * pertaining to certains bugs/issues to keep track of any progress.
 * Further edits can be made to the text files by choosing to update the status of the
 * bug/issue at hand and you may even print out the text file from command line.
 * @author Anthony Jaramillo
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class BugTracker {
	// Static scanner so that all methods may reference this scanner 
	// without having to instantiate their own System.in scanner which
	// causes the user input stream to fail.
	private static Scanner input = new Scanner(System.in);
	
	/*
	 * The beginning of the program which prompts the user to select on of the 
	 * following options pertaining to the bug tracking system.
	 */
	public static void main(String args[]) throws IOException {
		System.out.println("--------- Bug Tracking System ---------");
		
		String userChoice = "";
		while(!userChoice.equals("4")) {
			System.out.println("Choose between one of the options below by entering a number and then pressing enter");
			System.out.println("1. File a new bug");
			System.out.println("2. Change status of an existing bug");
			System.out.println("3. Print the file of a bug");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			
			userChoice = input.nextLine();
			
			switch(userChoice) {
				case "1":
					createBugFile();
					break;
				
				case "2":
					changeBugStatus();
					break;
				
				case "3":
					printBugFile();
					break;
					
				case "4":
					System.out.println("System exiting.");
					break;
					
				default:
					System.out.println("Invalid input detected.");
					break;
			}
		}
		input.close();
	}
	
	/*
	 * createBugFile() takes the user on the process of creating a text file
	 * for the bug they wish to report. It asks a series of questions to gather
	 * information and writes it all out to the text file.
	 */
	public static void createBugFile() throws IOException {
		System.out.println("--------- File Creation ---------");
		System.out.print("Enter the name of the file to track the bug: ");
		String fileName = input.nextLine();
		File file = checkValidFile(fileName);
		FileWriter fileWriter = new FileWriter(file);
		
		Date date = new Date();
		fileWriter.write("DATE AND TIME:\t\t" + date.toString() + "\n");
		fileWriter.flush();
		
		System.out.print("Enter the file that contains the bug: ");
		String temp = input.nextLine();
		fileWriter.write("FILENAME:\t\t" + temp + "\n\n");
		fileWriter.flush();
		
		System.out.print("Enter your username to save into the file: ");
		temp = input.nextLine();
		fileWriter.write("USER:\t\t\t" + temp + "\n");
		fileWriter.flush();
		
		System.out.print("Enter the bug type: ");
		temp = input.nextLine();
		fileWriter.write("BUG TYPE:\t\t" + temp + "\n");
		fileWriter.flush();
		
		System.out.print("Enter the priority of the bug: ");
		temp = input.nextLine();
		fileWriter.write("BUG PRIORITY:\t\t" + temp + "\n");
		fileWriter.flush();
		
		System.out.print("Enter the bug description: ");
		temp = input.nextLine();
		fileWriter.write("BUG DESCRIPTION: \t");
		fileWriter.flush();
		
		// The following if statement executes should the user input a long string
		// which would need to be compressed into multiple lines for the sake of readability
		if(temp.length() > 50) {
			// Tokenize the entire input string to separate words
			String[] a = temp.split(" ");
			int lengthCounter = 0;
			
			// For loop counts the length of each token and prints a new line when the 
			// 50th character mark (disregarding tabs and "Bug Description:") in the line 
			// is about to be passed.
			for(int i = 0; i < a.length; i++) {
				lengthCounter += a[i].length();
				if(lengthCounter > 50) {
					lengthCounter = 0;
					fileWriter.write("\n\t\t\t");
					fileWriter.flush();
				}
				fileWriter.write(a[i] + " ");
				fileWriter.flush();
			}
			fileWriter.write("\n\n");
			fileWriter.flush();
		}
		
		fileWriter.write("BUG STATUS: \t\t");
		fileWriter.flush();
		assignStatus(fileWriter);
		fileWriter.close();
	}
	
	/*
	 * changeBugStatus() opens an existing text file and writes
	 * a status update regarding the bug/issue at hand.
	 */
	public static void changeBugStatus() throws IOException {
		System.out.println("--------- Bug Status Change ---------");
		System.out.print("Enter the name of the file to update the bug: ");
		String fileName = input.nextLine();
		File file = checkValidFile(fileName);
		FileWriter fileWriter = new FileWriter(file, true);
		Date date = new Date();
		
		fileWriter.write("----------------------------------\n");
		fileWriter.flush();
		fileWriter.write("DATE:\t\t\t" + date.toString() + "\n");
		fileWriter.flush();
		fileWriter.write("STATUS UPDATE:\t\t");
		fileWriter.flush();
		assignStatus(fileWriter);
		System.out.println();
	}
	
	/*
	 * printBugFile() opens a text file and prints out its contents
	 */
	public static void printBugFile() {
		System.out.println("--------- Print Bug File ---------");
		System.out.print("Enter the name of the bug file to print: ");
		String fileName = input.nextLine();
		File file = checkValidFile(fileName);
		Scanner scan;
		try {
			scan = new Scanner(file);
			System.out.println();
			while(scan.hasNext()) {
				System.out.println(scan.nextLine());
			}
			System.out.println();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/*
	 * checkValidFile attempts to open a new file object and 
	 * throws an exception should anything go wrong
	 * @param fileName		The string of the file's name
	 * @return 				The file if opened correctly
	 */
	public static File checkValidFile(String fileName) {
		File file = null;
		try {
			if(!fileName.endsWith(".txt")) {
				fileName = fileName.concat(".txt");
			}
			file = new File(fileName);
		} catch(Exception e) {
			System.out.println(e);
		}
		return file;
	}
	
	/*
	 * assignStatus lets the user choose one of the four status options
	 * which is printed out to the text file.
	 * @param fileWriter	The FileWriter object whoose output stream is a text file
	 */
	public static void assignStatus(FileWriter fileWriter) throws IOException {
		System.out.println("Choose one of the options to assign the bug's status:");
		System.out.println("1. Not yet assigned");
		System.out.println("2. In process");
		System.out.println("3. Fixed");
		System.out.println("4. Committed");
		System.out.print("Enter your choice: ");
		String status = input.nextLine();
		
		switch(status) {
		case "1":
			fileWriter.write("NOT YET ASSIGNED\n");
			break;
			
		case "2":
			fileWriter.write("IN PROCESS\n");
			break;
			
		case "3":
			fileWriter.write("FIXED\n");
			break;
			
		case "4":
			fileWriter.write("COMMITTED\n");
			break;
			
		default:
			System.out.println("Invalid input detected");
			break;
		}
		fileWriter.flush();
	}
}