package model;

import java.util.Observable;

public class ObservableTile extends Observable {

	private Tile tile;
	
	public ObservableTile(int row, int column){
		tile = new Tile(row,column);
	}
	
	public void resetTile(){
		tile.populateGrid();
		setChanged();
		notifyObservers();
	}
	
	// region: Getter and Setter methods.
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

		// endregion
}
