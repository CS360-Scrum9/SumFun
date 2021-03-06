package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FileHandler {
	
	private final String timeFile = "BestTimes.txt";
	private final String scoreFile = "BestScores.txt";
	private	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	
	public FileHandler(){}
	
	public boolean isHighScore(int score, boolean timed) {
		boolean isHighScore = false;
		String fileName;
		
		if (timed) {
			fileName = timeFile;
		} else {
			fileName = scoreFile;
		}
		
		String[][] scores = getScores(fileName);
		
		
		for (int i = 0; i < scores.length; i++) {
			if (score > Integer.parseInt(scores[i][1])) {
				isHighScore = true;
				break;
			}
		}
		
		return isHighScore;
	}
	
	public void addScore(String name, int score, boolean timed) {	
		
		String fileName;
		int index = 0;
		int i = 0;
		
		if (timed) {
			fileName = timeFile;
		} else {
			fileName = scoreFile;
		}
		
		String[][] scores = getScores(fileName);
		
		while (scores[i] != null) {
			if (score > Integer.parseInt(scores[i][1])) {
				index = i;
				break;
			}
			i++;
		}
		
		String[][] newScores = new String[10][3];
		i = 0;
		int k = 0;
		for (i = 0; i < scores.length; i++) {
			if (i != index) {
				newScores[i] = scores[k];
			} else {
				String[] newScore = {name, Integer.toString(score), dtf.format(LocalDateTime.now())};
				newScores[i] = newScore;
				k--;
			}
			k++;
		}
		
		FileWriter fw;
		
		try {
			File file = new File(fileName);
			fw = new FileWriter(file);
						
			for (i = 0; i < newScores.length; i++) {
				String temp = "";
				for (int j = 0; j < newScores[i].length; j++) {
					temp += newScores[i][j] + ",";
				}
				fw.write(temp.substring(0, temp.length() - 1) + "\n");
			}
			
			fw.close();
		} catch (IOException e) {

		}

	}

	public String[][] getScores(String fileName) {
		File file = new File(fileName);
		Scanner input = null;
		
		try {
			input = new Scanner(file);
		} catch (IOException e) {
			
		}
		
		String[][] scores = new String[10][];
		int counter = 0;
		
		while (input.hasNext()) {
			String line = input.nextLine();
			String[] info = line.split(",");
			scores[counter] = info;
			counter++;
		}
		
		input.close();
		
		return scores;
	}
	
	public void checkFiles() {
		
		String[] files = {timeFile, scoreFile};
		for (int i = 0; i < files.length; i++) {
			File file = new File(files[i]);
			Scanner input = null;
		
			while (input == null) {
				try {
					input = new Scanner(file);
				} catch (FileNotFoundException ex) {
					try {
						file.createNewFile();
						FileWriter fw = new FileWriter(files[i]);
						for (int j = 0; j < 10; j++) {
							 fw.write("name,0,date\n");
						}
						fw.close();
					} catch (IOException e) {
						System.out.println(e);
					}
					
					System.out.println(ex);
				}
			}
		}
	}
}
