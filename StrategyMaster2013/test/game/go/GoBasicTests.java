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
import static org.junit.Assert.fail;
import game.GameFactory;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceType;

import org.junit.Test;

import common.PlayerColor;
import common.StrategyException;

/**  Basic tests that verify that a game of Go will be played correctly. Captures
 *  and final scores are calculated separately.
 * 
 * @author Dabrowski
 */
public class GoBasicTests extends GoTestSuite {

	//// THE BEFORE & AFTER ARE IN "GoTestSuite" along with helper functions

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
		gameDouble.setBoardConfiguration(basicConfig);

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
			game.placePiece(blk, new Location2D(0,0));
			game.placePiece(wht, new Location2D(0,0));
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
}
