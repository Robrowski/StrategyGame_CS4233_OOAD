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

import game.GameController;
import game.GameFactory;
import game.common.Location2D;
import game.common.PieceType;

import org.junit.Before;
import org.junit.Test;

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
	private  GameController gameDouble;
	
	
	
	@Before
	public void setup() throws StrategyException{
		game = GameFactory.getInstance().makeGoGame(boardSize, null);
		game.startGame();
		
		gameDouble = new GoControllerTestDouble(boardSize);
		gameDouble.startGame();
	}
	
	
	
	////// Initialization

	@Test(expected=StrategyException.class)
	public void startGameAfterStarting() throws StrategyException{
		game.startGame();
	}
	
	@Test(expected=StrategyException.class)
	public void cannotCallMove() throws StrategyException
	{
		game.move( PieceType.STONE, new Location2D(0, 0),new Location2D(0, 1));
	}
	
	

	
	
	
	
	
	
	
	
	
	
}
