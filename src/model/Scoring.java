package model;

import java.util.Observable;

	public class Scoring extends Observable{
		
		private static Scoring score;
		protected int playerScore;
		
		private Scoring(){
			playerScore = 0;
		}
		
		public static Scoring getInstance(){
			if(score == null)
				score = new Scoring();
			return score;
		}
		
		public int getScore(){
			return playerScore;
		}
		
		public void setScore(int playerScore){
			this.playerScore = playerScore;
			setChanged();
			notifyObservers();
		}
		
		@Override
		public String toString(){
			return " Score: " + playerScore + " ";
		}
	}