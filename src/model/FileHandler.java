package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
	
	private final String timedFile = "TimedHighScores.txt";
	private final String untimedFile = "UntimedHighScores.txt";
	
	public FileHandler(){}
	
	public boolean isHighScore(int score, boolean timed) {
		boolean isHighScore = false;
		String fileName;
		
		if (timed) {
			fileName = timedFile;
		} else {
			fileName = untimedFile;
		}
		
		String[][] scores = getScores(fileName);
		
		int i = 0;
		
		while (scores[i] != null) {
			if (score > Integer.parseInt(scores[i][2])) {
				isHighScore = true;
				break;
			}
			i++;
		}
		
		return isHighScore;
	}
	
	public void addScore(String name, String extra, int score, boolean timed) {
		String fileName;
		
		
		if (timed) {
			fileName = timedFile;
		} else {
			fileName = untimedFile;
		}
		
		int index = 0;
		int i = 0;
		
		String[][] scores = getScores(fileName);
		while (scores[i] != null) {
			if (score > Integer.parseInt(scores[i][2])) {
				index = i;
				break;
			}
			i++;
		}
		
		String[][] newScores = new String[10][3];
		i = 0;
		int k = 0;
		while (scores[k] != null) {
			if (i != index) {
				newScores[i] = scores[k];
			} else {
				String[] newScore = {name, extra, Integer.toString(score)};
				newScores[i] = newScore;
				k--;
			}
			i++;
			k++;
		}
		
		FileWriter fw;
		
		try {
			File file = new File(fileName);
			fw = new FileWriter(file);
			
			i = 0;
			
			while (newScores[i][0] != null) {
				String temp = "";
				for (int j = 0; j < newScores[i].length; j++) {
					temp += newScores[i][j] + ",";
				}
				fw.write(temp.substring(0, temp.length() - 1) + "\n");
				i++;
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
		} catch (FileNotFoundException ex) {
			System.out.println(ex);
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
}
