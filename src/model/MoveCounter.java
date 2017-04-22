package model;

import java.util.Observable;

public class MoveCounter extends Observable{
	
	private static MoveCounter mc;
	private int moveCount;
	private int tileCount;

	private MoveCounter(){
		moveCount = 50;
		tileCount = 49;
	}

	public static MoveCounter getInstance(){
		if(mc == null){
			mc = new MoveCounter();
		}
		return mc;
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
