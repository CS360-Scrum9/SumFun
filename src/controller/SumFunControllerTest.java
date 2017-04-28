package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import model.ObservableTile;

import org.junit.Test;


public class SumFunControllerTest {

	private ObservableTile[][] mockTileGrid;
	private SumFunController sfc = SumFunController.getController();
	
	@Test
	public void testCheckNeighbors() {
		
		mockTileGrid = new ObservableTile[6][6];
		
		/*
		 * Input condition:				Class: 	  
		 * Value of queueValue:		0 <= int <= 9, int < 0, int > 10
		 * Value of row:			1 <= int <= length of tilegrid (in this case 4), int < 1, int > length of tilegrid
		 * Value of column:			1 <= int <= length of tilegrid (in this case 4), int < 1, int > length of tilegrid
		 * 
		 * */
		 
		//create a mock 4x4 grid of tiles with
		//the inner 2x2 tiles occupied with numbers
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				mockTileGrid[i][j] = new ObservableTile(i,j,6);
			}
		}
		mockTileGrid[2][2].setNumber(0);
		mockTileGrid[2][3].setNumber(1);
		mockTileGrid[3][2].setNumber(8);
		mockTileGrid[3][4].setNumber(9);
		sfc.createMockboard(mockTileGrid);
		assertEquals(true, sfc.checkNeighbors(1, 1, 0));
		assertEquals(true, sfc.checkNeighbors(3, 3, 9));
		
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
		
		// Black-box Testing
		// assertEquals("Error", false, sfc.checkNeighbors(tiles, row, column, queueValue));
	}

	@Test
	public void testResetNeighbors() {
		// Black-box Testing
	}

}
