package model;

import controller.SoundEffect;
import controller.SumFunController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;



public class TimedGamemode extends Gamemode {

	public static final int START_TIME = 300;
	private static TimedGamemode gamemode;
	private JLabel lblTimer;
	private int timeLeft;
	private static SumFunController controller;
	private Timer timer;
	
	private TimedGamemode() {
		super();
		timer = new Timer(1000, new UpdateTime());
		timeLeft = START_TIME;
	}

	public static TimedGamemode getGamemode() {
		 if (gamemode == null) {
			 gamemode = new TimedGamemode();
		 }
		 return gamemode;
	}
	
	public void setController(SumFunController controller) {
		TimedGamemode.controller = controller;
	}
	
	public void startTime(JLabel lblTimer) {
		timeLeft = START_TIME;
		this.lblTimer = lblTimer;
		timer.start();
	}
	
	private String convertToString() {
		int minutes = timeLeft / 60;
		int seconds = timeLeft % 60;
		
		String time = "";
		
		if (seconds < 10) {
			time = minutes + ":0" + seconds;
		} else {
			time = minutes + ":" + seconds;
		}
		return "Time Left: " + time;
	}
	
	public static String convertToString(int timeInSeconds) {
		int minutes = timeInSeconds / 60;
		int seconds = timeInSeconds % 60;
		
		String time = "";
		
		if (seconds < 10) {
			time = minutes + ":0" + seconds;
		} else {
			time = minutes + ":" + seconds;
		}
		return "" + time;
	}
	
	private class UpdateTime implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if (timeLeft != -1) {
				lblTimer.setText(convertToString());
				timeLeft--;
			} else {
				SoundEffect.LOSE.play();
				controller.gameOver("Game Over! You ran out of time! New Game?", JOptionPane.ERROR_MESSAGE);
			}
				
		}
		
	}
	
	public void stopTimer() {
		timer.stop();
	}
	
	public int getTime() {
		return timeLeft;
	}
	
	public void setTime(int time) {
		timeLeft = time;
	}
	
}
