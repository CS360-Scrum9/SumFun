package controller;

import static org.junit.Assert.assertEquals;

import model.ObservableTile;

import org.junit.Test;


public class SumFunControllerTest {

	private ObservableTile[][] mockTileGrid;
	private SumFunController sfc = SumFunController.getController();
	
	@Test
	public void testCheckNeighbors() {
		
		mockTileGrid = new ObservableTile[6][6];
		
		//create a mock 4x4 grid of tiles with
		//the inner 2x2 tiles occupied with numbers
		for(int i = 0; i < mockTileGrid.length; i++){
			for(int j = 0; j < mockTileGrid[0].length; j++){
				mockTileGrid[i][j] = new ObservableTile(i,j,6);
			}
		}
		mockTileGrid[2][2].setNumber(0);
		mockTileGrid[2][3].setNumber(1);
		mockTileGrid[3][2].setNumber(8);
		mockTileGrid[3][3].setNumber(9);
		sfc.createMockboard(mockTileGrid);
		
		// Black-box Testing
		
		/*
		 * Input condition:			Classes: 	  
		 * Value of queueValue:		0 <= int <= 9, int < 0, int > 10
		 * Value of row:			1 <= int <= length of tile grid (in this case 4), int < 1, int > length of tile grid
		 * Value of column:			1 <= int <= length of tile grid (in this case 4), int < 1, int > length of tile grid
		 *
		 * Input Condition:			Boundary Values:
		 * Value of queueValue:		-1,0,9,10
		 * Value of row:			0,1,4,5
		 * Value of column: 		0,1,4,5
		 * 
		 * */
		
		//Valid Equivalence Classes:
		assertEquals(true, sfc.checkNeighbors(1, 1, 0));
		assertEquals(true, sfc.checkNeighbors(4, 4, 9));
		
		
		//Invalid Equivalence Classes:
		try{
			assertEquals(false, sfc.checkNeighbors(0, 0, 9));
		}catch(IndexOutOfBoundsException e){

		}
		try{
			assertEquals(false, sfc.checkNeighbors(5, 5, 9));
		}catch(IndexOutOfBoundsException e){
			
		}
		try{
			assertEquals(false, sfc.checkNeighbors(5, 0, 9));
		}catch(IndexOutOfBoundsException e){
			
		}
		try{
			assertEquals(false, sfc.checkNeighbors(0, 5, 9));
		}catch(IndexOutOfBoundsException e){
			
		}
		
		assertEquals(false, sfc.checkNeighbors(3, 3, -1));
		assertEquals(false, sfc.checkNeighbors(3, 3, 10));
		
		// Glass-box Testing
		
		
		/* Two possible different paths in the checkNeighbors() method
		 * Path 1: If statement passes
		 * Path 2: If statement fails
		 */
		
		//Path 1: If statement passes
		assertEquals(true, sfc.checkNeighbors(4, 3, 7));
		
		//Path 2:  If statement fails
		assertEquals(false, sfc.checkNeighbors(2, 2, 5));
		
	}

	@Test
	public void testResetNeighbors() {
		// Black-box Testing
		
		/*
		 * Input condition:			Classes: 	  
		 * Value of row:			1 <= int <= length of tile grid (in this case 9), int < 1, int > length of tile grid
		 * Value of column:			1 <= int <= length of tile grid (in this case 9), int < 1, int > length of tile grid
		 *
		 * Input Condition:			Boundary Values
		 * Value of row:			0,1,9,10
		 * Value of column: 		0,1,9,10
		 * 
		 * */
		
		//Create 9X9 grid of tiles
		//populated with random numbers in the inner 7x7 grid
		mockTileGrid = new ObservableTile[11][11];
		
		for(int i = 0; i < mockTileGrid.length; i++){
			for(int j = 0; j < mockTileGrid[0].length; j++){
				mockTileGrid[i][j] = new ObservableTile(i,j,mockTileGrid.length);
			}
		}
		sfc.createMockboard(mockTileGrid);
		
		//List of input conditions covering boundary values of
		//valid and invalid equivalence classes
		int[][] list = {  {1,1},
						  {9,9},
						  {0,0},
					      {10,10},
						  {10,0},
						  {0,10}  };
		
		for(int i = 0; i < list.length; i++){
			try{
			sfc.resetNeighbors(list[i][0],list[i][1]);
			}catch(IndexOutOfBoundsException e){
				
			}
		}
		
		for(int a = 0; a < list.length; a++){
			for(int i = list[a][0] - 1; i < 2; i++){
				for(int j = list[a][1] - 1; j < 2; j++){
					try{
					assertEquals(false, mockTileGrid[i][j].isOccupied());
					assertEquals(0, mockTileGrid[i][j].getNumber());
					}catch(IndexOutOfBoundsException e){
						
					}
				}
			}
		}
		
		//Glass-box testing
		
		//Only one possible path in the resetNeighbors() function
		
		sfc.resetNeighbors(5,5);
		
		for(int i = 4; i < 7; i++){
			for(int j = 4; j < 7; j++){
				assertEquals(false, mockTileGrid[i][j].isOccupied());
				assertEquals(0, mockTileGrid[i][j].getNumber());
			}
		}
		
	}

}
