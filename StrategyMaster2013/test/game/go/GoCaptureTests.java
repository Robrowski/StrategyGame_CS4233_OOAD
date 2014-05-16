/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.go;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import game.common.Location;
import game.common.Location2D;
import game.common.PieceLocationDescriptor;
import game.common.turnResult.ITurnResult;

import org.junit.Test;

import common.PlayerColor;
import common.StrategyException;

/** Tests for all cases that involve captures
 * 
 * 
 * @author Dabrowski
 *
 */
public class GoCaptureTests extends GoTestSuite {
	/** Checks that the board has all the expected pieces removed
	 * 
	 * @param expectedToBeRemoved
	 */
	void verifyPiecesRemovedFromBoard(Collection<PieceLocationDescriptor> expectedToBeRemoved){
		Iterator<PieceLocationDescriptor> expectedItr = expectedToBeRemoved.iterator();

		while (expectedItr.hasNext()){
			assertNull(gameDouble.getPieceAt(expectedItr.next().getLocation()));
		}
	}


	/** Verifies that the turn result is accurate
	 * 
	 * @param expectedToBeRemoved
	 * @param iTR
	 */
	void verifyAccurateTurnResult(Collection<PieceLocationDescriptor> expectedToBeRemoved, ITurnResult iTR){
		Collection<PieceLocationDescriptor> removed = iTR.getPiecesRemoved();
		assertTrue(  expectedToBeRemoved.containsAll(removed) & removed.containsAll(expectedToBeRemoved) );
	}
	
	/** Run the capture test expecting all white pieces to be removed
	 * 
	 * @param blkStart
	 * @param whtStart
	 * @param blkMoveToCapture
	 * @throws StrategyException
	 */
	void runCaptureTest(int[][] blkStart, int[][] whtStart, Location blkMoveToCapture) throws StrategyException{
		runCaptureTest(blkStart, whtStart, whtStart, blkMoveToCapture);
	}

	/** Run the capture test expecting white pieces to be removed and black to get points 
	 * 
	 * @param blkStart
	 * @param whtStart
	 * @param whtExpectedRemoved
	 * @param blkMoveToCapture
	 * @throws StrategyException
	 */
	void runCaptureTest(int[][] blkStart, int[][] whtStart, int[][] whtExpectedRemoved, Location blkMoveToCapture) throws StrategyException{
		// Setup configuration
		addBlackAndWhitePieces(blkStart, whtStart);

		// Pieces expected to be removed
		Collection<PieceLocationDescriptor> expectedRemoved = generateConfiguration(PlayerColor.WHITE, whtExpectedRemoved);

		// Make the move
		ITurnResult res = gameDouble.placePiece(blk, blkMoveToCapture);

		// Check the results
		verifyAccurateTurnResult( expectedRemoved, res);
		verifyPiecesRemovedFromBoard(expectedRemoved);

		assertEquals(whtExpectedRemoved.length, gameDoubleScore.getPlayerScore(PlayerColor.BLACK));
	}
	
	
	
	
	//************ Basic Corner Captures          **************** /
	@Test
	public void cornerCapture1() throws StrategyException{
		// Setup configuration
		int[][] blackConfig = {	 {1,0} };
		int[][] whiteConfig = {	 {0,0} };

		runCaptureTest(blackConfig, whiteConfig, new Location2D(0,1));
	}

	@Test
	public void cornerCapture2() throws StrategyException{
		// Setup configuration
		int[][] blackConfig = {	 {0,17} };
		int[][] whiteConfig = {	 {0,18} };

		runCaptureTest(blackConfig, whiteConfig, new Location2D(1,18));
	}

	@Test
	public void cornerCapture3() throws StrategyException{
		// Setup configuration
		int[][] blackConfig = {	 {17,0} };
		int[][] whiteConfig = {	 {18,0} };

		runCaptureTest(blackConfig, whiteConfig, new Location2D(18,1));
	}

	@Test
	public void cornerCapture4() throws StrategyException{
		// Setup configuration
		int[][] blackConfig = {	 {18,17} };
		int[][] whiteConfig = {	 {18,18} };

		runCaptureTest(blackConfig, whiteConfig, new Location2D(17,18));
	}

	//************  edge Captures          **************** /
	// The same starting configuration is used for these tests
	final int[][] edgeBlackConfig = {	 {0,16}, {0 ,14 },{1 ,12 },    {0 ,11 },{1 ,10 },{1,8}   ,{0,7},{1,6},{1,4},{1,3} ,{0 ,2 }  };
	final int[][] edgeWhiteConfig = {	{0,15},    {0,13},{0,12}        ,{0,10},{0,9},{0,8},   {0,6},{0,5},{0,4},{0,3}   };

	@Test
	public void edgeCapture1() throws StrategyException{
		int[][] expectedRemoval = { edgeWhiteConfig[0]};
		runCaptureTest(edgeBlackConfig, edgeWhiteConfig, expectedRemoval, new Location2D(1, 15  ));
	}

	@Test
	public void edgeCapture2() throws StrategyException{
		int[][] expectedRemoval = { edgeWhiteConfig[1],edgeWhiteConfig[2]};
		runCaptureTest(edgeBlackConfig, edgeWhiteConfig, expectedRemoval, new Location2D(1, 13  ));
	}
	@Test
	public void edgeCapture3() throws StrategyException{
		int[][] expectedRemoval = { edgeWhiteConfig[3],edgeWhiteConfig[4],edgeWhiteConfig[5]};
		runCaptureTest(edgeBlackConfig, edgeWhiteConfig, expectedRemoval, new Location2D(1,  9 ));
	}
	@Test
	public void edgeCapture4() throws StrategyException{
		int[][] expectedRemoval = { edgeWhiteConfig[6],edgeWhiteConfig[7],edgeWhiteConfig[8],edgeWhiteConfig[9]};
		runCaptureTest(edgeBlackConfig, edgeWhiteConfig, expectedRemoval, new Location2D(1,  5 ));
	}


	//************  center Captures          **************** /
	// The same starting configuration is used for these tests
	final int[][] centerBlackConfig = {	{7,7 },{7,8 },{7,9 },  {7,11 },{7,12 },{7,13 },{7,14 },{7,15},{8,10},{8,11} , {9,7 },{9,8 },{9,9 },  {9,11 },{9,12 },{9,13 },{9,14 },{9,15 },  {10,9 },{10,11 }  };
	final int[][] centerWhiteConfig = {	{ 7,10 }, 	{9 ,10 },{ 10,10 },            	{8 ,7 },{8 ,8 },{8 ,9 },  {8 ,15 },{8 ,12 },{8 ,13 },{8 ,14 } };

	@Test
	public void centerCapture1() throws StrategyException{
		int[][] expectedRemoval = { centerWhiteConfig[0]};
		runCaptureTest(centerBlackConfig, centerWhiteConfig, expectedRemoval, new Location2D(6, 10  ));
	}

	@Test
	public void centerCapture2() throws StrategyException{
		int[][] expectedRemoval = { centerWhiteConfig[1],centerWhiteConfig[2]};
		runCaptureTest(centerBlackConfig, centerWhiteConfig, expectedRemoval, new Location2D(11, 10  ));
	}
	@Test
	public void centerCapture3() throws StrategyException{
		int[][] expectedRemoval = { centerWhiteConfig[3],centerWhiteConfig[4],centerWhiteConfig[5]};
		runCaptureTest(centerBlackConfig, centerWhiteConfig, expectedRemoval, new Location2D(8,  6 ));
	}
	@Test
	public void centerCapture4() throws StrategyException{
		int[][] expectedRemoval = { centerWhiteConfig[6],centerWhiteConfig[7],centerWhiteConfig[8],centerWhiteConfig[9]};
		runCaptureTest(centerBlackConfig, centerWhiteConfig, expectedRemoval, new Location2D(8,  16 ));
	}

	//************  complex Captures          **************** /
	// The same starting configuration is used for these tests
	final int[][] complexBlackConfig = {	{ 1,0},{ 7,0},{8 ,0},{10 ,0},    {1 ,1},{ 2,1},{3 ,1},{4 ,1},{5,1},{7,1},{11 ,1},    { 7,2},{8 ,2},{9 ,2},  {0 ,3},{1 ,3},{2 ,3},{5 ,3},{6 ,3},    { 0,5},{ 2,5},  {1 ,6} };
	final int[][] complexWhiteConfig  = {  {0 ,0 },{0 ,1 },{0 , 2},{1 ,2 },{2 , 2},   { 0,4 },{1 ,4 },{2 ,4 },{1 ,5 },   {2 ,0 },{3 ,0 },{4 ,0 },{5 ,0 },{ 6,0 },{ 6, 1},{ 6,2 },{ 5, 2},     { 8,1 }, {9 ,1 },{ 9,0 },{10 ,1 },      { 3,5 },{3 ,6 },{4 ,4 },{4 ,3 },{5 ,4 },{11 , 2}    };

	@Test
	public void complexCaptureA() throws StrategyException{
		int[][] expectedRemoval = { {0 ,0 },{0 ,1 },{0 , 2},{1 ,2 },{2 , 2}   };
		runCaptureTest(complexBlackConfig, complexWhiteConfig, expectedRemoval, new Location2D( 3  ,   2));

	}

	@Test
	public void complexCaptureB() throws StrategyException{
		int[][] expectedRemoval = { { 0,4 },{1 ,4 },{2 ,4 },{1 ,5 }  };
		runCaptureTest(complexBlackConfig, complexWhiteConfig, expectedRemoval, new Location2D( 3  , 4  ));

	}

	@Test
	public void complexCaptureC() throws StrategyException{
		int[][] expectedRemoval = {{2 ,0 },{3 ,0 },{4 ,0 },{5 ,0 },{ 6,0 },{ 6, 1},{ 6,2 },{ 5, 2}   };
		runCaptureTest(complexBlackConfig, complexWhiteConfig, expectedRemoval, new Location2D(  4 ,   2));

	}

	@Test
	public void complexCaptureD() throws StrategyException{
		int[][] expectedRemoval = {  { 8,1 }, {9 ,1 },{ 9,0 },{10 ,1 }  };
		runCaptureTest(complexBlackConfig, complexWhiteConfig, expectedRemoval, new Location2D(  10 ,2   ));
	}




	//************  MultiCaptures          **************** /
	// The same starting configuration is used for these tests
	int[][] multiBlackConfig = { {7,9},{8 ,6 },{ 8, 8},{8 ,10 },{ 9,5 },{9 , 7},{ 9, 11},{10 ,8 },{ 10,10 },{ 11, 5},{11 , 7},{11 , 9},{ 12, 6},{ 12,10 },{12 ,12 },{ 13, 11}  };
	int[][] multiWhiteConfig = { { 12,11 },{11 ,10 },   {10 , 7},{9 , 6},{11 ,6 },  {8 ,9 },{9 ,10 },{9 ,8 },{ 10, 9} };

	@Test
	public void multiCapture2() throws StrategyException{
		int[][] expectedRemoval = {  { 12,11 },{11 ,10 }  };
		runCaptureTest(multiBlackConfig, multiWhiteConfig, expectedRemoval, new Location2D( 11 ,  11 ));
	}

	@Test
	public void multiCapture3() throws StrategyException{
		int[][] expectedRemoval = { {10 , 7},{9 , 6},{11 ,6 }  };
		runCaptureTest(multiBlackConfig, multiWhiteConfig, expectedRemoval, new Location2D(10  , 6  ));
	}	

	@Test
	public void multiCapture4() throws StrategyException{
		int[][] expectedRemoval = {   {8 ,9 },{9 ,10 },{9 ,8 },{ 10, 9}  };
		runCaptureTest(multiBlackConfig, multiWhiteConfig, expectedRemoval, new Location2D( 9 ,  9 ));
	}	


	//*******************  More Complex ***********************8/
	// The same starting configuration is used for these tests
	int[][] moreComplexBlackConfig = { { 6,10 },{ 6,11 },{ 7,9 },{7 ,12 },{ 8,9 },{ 9, 10} };
	int[][] moreComplexWhiteConfig = { { 7,11 },{7 ,10 },{8 ,10 } };

	@Test
	public void moreComplexCapture1() throws StrategyException{
		int[][] expectedRemoval = {  { 7,11 },{7 ,10 },{8 ,10 }  };
		runCaptureTest(moreComplexBlackConfig, moreComplexWhiteConfig, expectedRemoval, new Location2D( 8 ,  11 ));
	}


	@Test
	public void moreComplexCapture2() throws StrategyException {
		int [][] blackConfig = new int[boardSize*2 -1][2];
		int [][] whiteConfig = new int[boardSize][2];

		int x = 0, y = 1;

		for (int i = 0; i < boardSize -1; i++){
			blackConfig[i][x] = 14;
			blackConfig[i][y] = i;

			whiteConfig[i][x] = 15;
			whiteConfig[i][y] = i;

			blackConfig[i + boardSize][x] = 16;
			blackConfig[i + boardSize][y] = i;
		}
		int lastRow = boardSize-1;
		blackConfig[lastRow][x] = 14;
		blackConfig[lastRow][y] = lastRow;

		whiteConfig[lastRow][x] = 15;
		whiteConfig[lastRow][y] = lastRow;

		runCaptureTest(blackConfig, whiteConfig, whiteConfig, new Location2D( 16, 18));
	}

	//********************    Suicides *********************/
	int [][] blackSuicideConfig = { { 0, 3},{ 2, 3},{ 3, 3}   };
	int [][] whiteSuicideConfig = { {0 ,1 },{1 ,0 },{0 ,4 },{1 ,2 },{1 ,3 },{1 ,5 },{2 ,2 },{2 ,4 },{3 ,2 },{3 ,4 },{4 ,2 },{4 ,4 },{ 5, 3}};

	@Test(expected=StrategyException.class)
	public void NOsuicide1() throws StrategyException{
		runCaptureTest(blackSuicideConfig, whiteSuicideConfig, new Location2D(0,0));		
	}

	@Test(expected=StrategyException.class)
	public void NOsuicide2() throws StrategyException{
		runCaptureTest(blackSuicideConfig, whiteSuicideConfig, new Location2D(0,2));		
	}

	@Test(expected=StrategyException.class)
	public void NOsuicide3() throws StrategyException{
		runCaptureTest(blackSuicideConfig, whiteSuicideConfig, new Location2D(4,3));		

	}

	@Test(expected=StrategyException.class)
	public void NOsuicide4() throws StrategyException{
		runCaptureTest(blackSuicideConfig, whiteSuicideConfig, new Location2D(1,4));		
	}

}
