package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
	private JLabel[] lblDates;
	
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
		lblName = new JLabel[11];
		lblExtra = new JLabel[11];
		lblDates = new JLabel[11];
		
		fileHandler = new FileHandler();
		
		BorderLayout bl = new BorderLayout();
		pnlMain.setLayout(bl);
		
		GridLayout gl = new GridLayout(12,3);
		pnlCenter.setLayout(gl);
		
		for (int i = 0; i < 11; i++) {
			lblName[i] = new JLabel();
			lblExtra[i] = new JLabel();
			lblDates[i] = new JLabel();
			pnlCenter.add(lblName[i]);
			pnlCenter.add(lblExtra[i]);
			pnlCenter.add(lblDates[i]);
		}
		

		
		generateView(version);
		
		pnlTop.add(lblTitle, SwingConstants.CENTER);
		pnlMain.add(pnlCenter);
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlMain);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 500);
	}
	
	public void generateView(String version) {
		lblName[0].setText("Name");
		lblDates[0].setText("Date");
		
		String[][] scores;
		if (version.equals("Time")) {
			scores = fileHandler.getScores(timeFile);
			lblTitle.setText("Best Times");
			lblExtra[0].setText("Time");
		} else {
			scores = fileHandler.getScores(scoreFile);
			lblTitle.setText("Best Scores");
			lblExtra[0].setText("Score");
		}
		
		clearScoreBoard();
				
		for (int i = 1; i < scores.length + 1; i++) {
			if (!scores[i - 1][0].equals("name")) {
				lblName[i].setText(scores[i - 1][0]);
				
				if (version.equals("Time")) {
					lblExtra[i].setText(TimedGamemode.convertToString(Integer.parseInt(scores[i - 1][1])));
				} else {
					lblExtra[i].setText(scores[i - 1][1]);
				}
				
				lblDates[i].setText(scores[i - 1][2]);
			}
		}
	}
	
	private void clearScoreBoard() {
		for (int i = 1; i < 11; i++) {
			lblName[i].setText("");
			lblExtra[i].setText("");
			lblDates[i].setText("");
		}
	}
}
