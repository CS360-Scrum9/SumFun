package model;

import java.util.Observable;

public class MoveCounter extends Observable{
	
	private int moveCount, tileCount;

	public MoveCounter(){
		moveCount = 50;
		tileCount = 49;
	}
	
	public int getMoveCount(){
		return moveCount;
	}

	public void setMoveCount(int moveCount){
		this.moveCount = moveCount;
		setChanged();
		notifyObservers();
	}

	public int getTileCount(){
		return tileCount;
	}

	public void setTileCount(int tileCount){
		this.tileCount = tileCount;
	}

	public void decrementCount(){
		moveCount = moveCount - 1;
		setChanged();
		notifyObservers();
	}
}
