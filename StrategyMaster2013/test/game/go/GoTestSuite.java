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

import static org.junit.Assert.fail;
import game.GameController;
import game.GameFactory;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.score.IScoreKeeper;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import common.PlayerColor;
import common.StrategyException;
import common.StrategyRuntimeException;

/**  Variables, helpers and setup/tear down for Go tests
 * 
 * 
 * @author Dabrowski
 *
 */
public abstract class GoTestSuite {

	// The game!
	final static int boardSize = 19;
	static  GameController game;
	static  GoControllerTestDouble gameDouble;
	static  Collection<PieceLocationDescriptor> basicConfig;

	// Score Keepers
	static IScoreKeeper gameScore;
	static IScoreKeeper gameDoubleScore;


	final Piece blk = new Piece(PieceType.STONE, PlayerColor.BLACK);
	final Piece wht = new Piece(PieceType.STONE, PlayerColor.WHITE);


	@BeforeClass 
	public static void preSetupSetup() throws StrategyException {
		// Score Keepers
		gameScore = new GoScoreKeeper(game);
		gameDoubleScore = new GoScoreKeeper(gameDouble);


		// Basic configuration setup for testing
		basicConfig = new LinkedList<PieceLocationDescriptor>();
		for (int x = 0; x < boardSize; x++){
			for (int y = 0; y < boardSize; y++){
				if (x < 3){		
					basicConfig.add(new PieceLocationDescriptor(new Piece(PieceType.STONE,PlayerColor.BLACK )  , new Location2D(x,y)  ));
				} else if (x > 5){
					basicConfig.add(new PieceLocationDescriptor(new Piece(PieceType.STONE,PlayerColor.WHITE )  , new Location2D(x,y)  ));
				}
			}
		}
	}


	@Before
	public void setup() throws StrategyException{
		game = GameFactory.getInstance().makeGoGame(boardSize, null);
		( (GoController) game).register(gameScore); // use cast because not all controllers may be observed
		game.startGame();

		gameDouble = new GoControllerTestDouble(boardSize);
		( (GoController) gameDouble).register(gameDoubleScore);
		gameDouble.startGame();
	}

	@After
	public void tearDown() throws StrategyException{
		// Nothin to do
	}

	@AfterClass
	public static void afterAllThatTesting(){
		( (GoController) game).unregister(gameScore);
		( (GoController) gameDouble).unregister(gameDoubleScore);


		// Just try to unregister again
		try {
			( (GoController) game).unregister(gameScore);
			fail();
		} catch (StrategyRuntimeException sre){

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
		newConfig.addAll(blkConfig);

		newConfig.addAll(whtConfig);


		gameDouble.setBoardConfiguration(newConfig);
	}
	
}
