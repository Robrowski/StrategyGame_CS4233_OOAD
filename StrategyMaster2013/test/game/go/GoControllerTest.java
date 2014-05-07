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

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import game.GameController;
import game.GameFactory;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;

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
	private final int boardSize = 7;
	private  GameController game;
	private  GoControllerTestDouble gameDouble;
	private  List<PieceLocationDescriptor> config;
	
	
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
	
	
	
}
