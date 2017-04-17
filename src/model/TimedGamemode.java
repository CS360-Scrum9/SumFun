package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import controller.SumFunController;

public class TimedGamemode extends Gamemode {

	private static TimedGamemode gamemode;
	private JLabel lblTimer;
	private int timeLeft;
	private static SumFunController controller;
	private Timer timer;
	
	private TimedGamemode() {
		super();
		timer = new Timer(1000, new UpdateTime());
	}

	public static TimedGamemode getGamemode() {
		 if (gamemode == null) {
			 gamemode = new TimedGamemode();
		 }
		 return gamemode;
	}
	
	public void setController(SumFunController controller) {
		this.controller = controller;
	}
	
	public void startTime(JLabel lblTimer) {
		timeLeft = 300;
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
		return time;
	}
	
	private class UpdateTime implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if (timeLeft != -1) {
				lblTimer.setText(convertToString());
				timeLeft--;
			} else {
				controller.gameOver("Game Over! You ran out of time! New Game?");
			}
				
		}
		
	}
	
	public void stopTimer() {
		timer.stop();
	}
	
	public String getTime() {
		return convertToString();
	}
	
	public void setTime(int time) {
		timeLeft = time;
	}
	
}
