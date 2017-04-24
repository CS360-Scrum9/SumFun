package model;

import java.util.Observable;

public class ObservableTile extends Observable {

	private Tile tile;

	public ObservableTile(int row, int column){
		tile = new Tile(row,column);
		tile.setReset(false);
	}

	public void resetTile(){
		tile.populateGrid();
		setChanged();
		notifyObservers();
		tile.setReset(false);
	}

	// region: Getter and Setter methods.
	
	public void startFlash(){
		tile.setFlash(true);
		setChanged();
		notifyObservers();
	}
	
	public void stopFlash(){
		tile.setFlash(false);
	}
	
	public boolean doFlash(){
		return tile.isFlash();
	}
	
	public Tile getTile(){
		return tile;
	}

	public int getNumber() {
		return tile.getNumber();
	}
	public int getRow() {
		return tile.getRow();
	}
	public int getColumn(){
		return tile.getColumn();
	}
	public void setNumber(int number){
		tile.setNumber(number);
		setChanged();
		notifyObservers();
	}

	public void setOccupied(boolean isOccupied){
		tile.setOccupied(isOccupied);
	}

	public boolean isOccupied(){
		return tile.isOccupied();
	}
	
	public boolean isReset() {
		return this.tile.isReset();
	}

	public void setReset(boolean reset) {
		this.tile.setReset(reset);
	}
	
	public void toggleEnable(boolean toggle){
		if(tile.isOccupied()){
			tile.setEnabled(!toggle);
		} else {
			tile.setEnabled(toggle);
		}
	}

	// endregion
}
