package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.FileHandler;
import model.TimedGamemode;

public class HighScoreBoard extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlTop;
	
	private JLabel lblTitle;
	private JLabel[] lblName;
	private JLabel[] lblExtra;
	private JLabel[] lblScore;
	
	private final String timeFile = "BestTimes.txt";
	private final String scoreFile = "BestScores.txt";
	
	private FileHandler fileHandler;
		
	//The version argument should either be 0 or 1.  This will determine
	//which view needs to be shown.
	public HighScoreBoard(String version) {
		pnlMain = new JPanel();
		pnlCenter = new JPanel();
		pnlTop = new JPanel();
		
		lblTitle = new JLabel("Highscores");
		lblName = new JLabel[10];
		lblExtra = new JLabel[10];
		lblScore = new JLabel[10];
		
		fileHandler = new FileHandler();
		
		BorderLayout bl = new BorderLayout();
		pnlMain.setLayout(bl);
		
		GridLayout gl = new GridLayout(10,3);
		pnlCenter.setLayout(gl);
		
		for (int i = 0; i < 10; i++) {
			lblName[i] = new JLabel();
			lblExtra[i] = new JLabel();
			lblScore[i] = new JLabel();
			pnlCenter.add(lblName[i]);
			pnlCenter.add(lblExtra[i]);
			pnlCenter.add(lblScore[i]);
		}
		
		generateView(version);
		
		pnlTop.add(lblTitle, SwingConstants.CENTER);
		pnlMain.add(pnlCenter);
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlMain);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	public void generateView(String version) {
		
		String[][] scores;
		if (version.equals("Timed")) {
			scores = fileHandler.getScores(timeFile);
			lblTitle.setText("Best Times");
		} else {
			scores = fileHandler.getScores(scoreFile);
			lblTitle.setText("Best Scores");
		}
		
		clearScoreBoard();
				
		for (int i = 0; i < scores.length; i++) {
			if (!scores[i][0].equals("name")) {
				lblName[i].setText(scores[i][0]);
				
				if (version.equals("Timed")) {
					lblExtra[i].setText(TimedGamemode.convertToString(Integer.parseInt(scores[i][1])));
				} else {
					lblExtra[i].setText(scores[i][1]);
				}
				
				lblScore[i].setText(scores[i][2]);
			}
		}
	}
	
	private void clearScoreBoard() {
		for (int i = 0; i < 10; i++) {
			lblName[i].setText("");
			lblExtra[i].setText("");
			lblScore[i].setText("");
		}
	}
}
