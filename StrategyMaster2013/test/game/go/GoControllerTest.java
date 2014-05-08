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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import game.GameController;
import game.GameFactory;
import game.common.Location;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.turnResult.ITurnResult;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import common.PlayerColor;
import common.StrategyException;

/** Tests for a controller for Go, the CHinese game of strategy and skill.
 * 
 * 
 * @author Dabrowski
 * @author Catt Mosti
 */
public class GoControllerTest {

	// The game!
	private final int boardSize = 19;
	private  GameController game;
	private  GoControllerTestDouble gameDouble;
	private  Collection<PieceLocationDescriptor> config;


	final Piece blk = new Piece(PieceType.STONE, PlayerColor.BLACK);
	final Piece wht = new Piece(PieceType.STONE, PlayerColor.WHITE);


	@Before
	public void setup() throws StrategyException{
		game = GameFactory.getInstance().makeGoGame(boardSize, null);
		game.startGame();

		gameDouble = new GoControllerTestDouble(boardSize);
		gameDouble.startGame();


		// Basic configuration setup for testing
		config = new LinkedList<PieceLocationDescriptor>();
		for (int x = 0; x < boardSize; x++){
			for (int y = 0; y < boardSize; y++){
				if (x < 3){		
					config.add(new PieceLocationDescriptor(new Piece(PieceType.STONE,PlayerColor.BLACK )  , new Location2D(x,y)  ));
				} else if (x > 5){
					config.add(new PieceLocationDescriptor(new Piece(PieceType.STONE,PlayerColor.WHITE )  , new Location2D(x,y)  ));
				}
			}
		}
		gameDouble.setBoardConfiguration(config);
	}



	////// Initialization stoof
	@Test(expected=StrategyException.class)
	public void startGameAfterStarting() throws StrategyException{
		game.startGame();
	}

	@Test(expected=StrategyException.class)
	public void cannotCallMove() throws StrategyException
	{
		game.move( PieceType.STONE, new Location2D(0, 0),new Location2D(0, 1));
	}


	//// getPieceAt - tests the entire board
	@Test
	public void getBlackorWhiteorNullPieces() throws StrategyException{
		// Expecting Nulls
		for (int x = 3; x <= 5; x++){
			for (int y = 0; y < boardSize; y++){
				assertNull(gameDouble.getPieceAt(new Location2D(x,y)));
			}
		}

		// Expecting BLACK pieces
		for (int x = 0; x < 3; x++){
			for (int y = 0; y < boardSize; y++){
				assertSame(gameDouble.getPieceAt(new Location2D(x,y)).getOwner(), PlayerColor.BLACK);
			}
		}

		// Expecting BLACK pieces
		for (int x = 6; x < boardSize; x++){
			for (int y = 0; y < boardSize; y++){
				assertSame(gameDouble.getPieceAt(new Location2D(x,y)).getOwner(), PlayerColor.WHITE);
			}
		}
	}



	// placePiece
	@Test
	public void cannotPlacePiecesOnPieces() throws StrategyException{
		try {
			gameDouble.placePiece(blk, new Location2D(0,0));
			fail();
		} catch (StrategyException se){
			assertSame(se.getMessage(), "Cannot place pieces on top of other pieces.");
		}
	}

	@Test
	public void cannotPlacePiecesBeforeGame()throws StrategyException{
		try{
			GameFactory.getInstance().makeGoGame(boardSize, null).placePiece(blk, new Location2D(0,0));
			fail();
		} catch (StrategyException se){
			assertSame(se.getMessage(), "Cannot place pieces before the game has started.");
		}
	}

	@Test
	public void cannotPlacePiecesAfterGame()throws StrategyException{
		gameDouble.setGameOver();
		try{
			gameDouble.placePiece(blk, new Location2D(0,0));
			fail();
		} catch (StrategyException se){
			assertSame(se.getMessage(), "Cannot place pieces after the game has ended.");
		}
	}

	@Test
	public void placePiece_InOrder() throws StrategyException{
		playStone("b",0,0);
		playStone("w",1,0);
		playStone("b",1,1);
		playStone("w",0,1);
	}


	@Test
	public void placePiece_OutOfOrder() throws StrategyException{
		try {
			playStone("w",0,0);
			fail();
		} catch (StrategyException se){
			assertSame(se.getMessage(), "Cannot place pieces during the other player's turn.");
		}
	}



	@Test
	public void canPassTurn() throws StrategyException {
		game.placePiece(new Piece(PieceType.PASS, PlayerColor.BLACK), null);
		playStone("w",0,0);
	}


	@Test 
	public void twoPassesEndsGame() throws StrategyException {
		playStone("b",0,0);
		playStone("w",1,0);

		game.placePiece(new Piece(PieceType.PASS, PlayerColor.BLACK), null);
		game.placePiece(new Piece(PieceType.PASS, PlayerColor.WHITE), null);

		try{
			playStone("b",0,1);
			fail();
		} catch (StrategyException se){
			assertSame(se.getMessage(), "Cannot place pieces after the game has ended.");
		}
	}


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
	


	///////////////////////////////
	// Helper functions

	/** Make a move
	 * 
	 * @param c Player color who is playing
	 * @param x position
	 * @param y position
	 * @throws StrategyException thrown on error
	 */
	void playStone(PlayerColor c, int x, int y) throws StrategyException{
		game.placePiece(new Piece(PieceType.STONE,c), new Location2D(x,y));
	}

	/** Make a move
	 * 
	 * @param c Player color who is playing
	 * @param x position
	 * @param y position
	 * @throws StrategyException thrown on error
	 */
	void playStone(String c, int x, int y) throws StrategyException{
		if (c == "w" || c == "W" || c == "WHITE" || c == "white"){
			playStone(PlayerColor.WHITE, x,y);
		} else {
			playStone(PlayerColor.BLACK, x,y);
		}
	}

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

	/** Takes a set of integers representing locations and puts them in a configuration
	 * 
	 * @param color of the pieces to make
	 * @param configToBe array of arrays that hold x,y coordinates
	 * @return configuration
	 */
	Collection<PieceLocationDescriptor> generateConfiguration(PlayerColor color, int[][] configToBe){
		LinkedList<PieceLocationDescriptor> config = new LinkedList<PieceLocationDescriptor>();

		int x = 0, y = 1;
		// Create the new configuration 
		for (int i = 0; i < configToBe.length; i++){
			config.add(new PieceLocationDescriptor(new Piece(PieceType.STONE, color), new Location2D(configToBe[i][x],configToBe[i][y])));
		}

		return config; 
	}

	/** Takes configurations for black and white and sets them in gameDouble
	 * 
	 * @param blk black pieces
	 * @param wht white pieces
	 * @throws StrategyException thrown on error
	 */
	void addBlackAndWhitePieces(int[][] blk, int[][] wht) throws StrategyException{
		Collection<PieceLocationDescriptor> whtConfig = generateConfiguration(PlayerColor.WHITE, wht);
		Collection<PieceLocationDescriptor> blkConfig = generateConfiguration(PlayerColor.BLACK, blk);

		Collection<PieceLocationDescriptor> newConfig = new LinkedList<PieceLocationDescriptor>();
		newConfig.addAll(whtConfig);
		newConfig.addAll(blkConfig);

		gameDouble.setBoardConfiguration(newConfig);
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

	/** Run the capture test expecting all white pieces to be removed
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
	}
}
