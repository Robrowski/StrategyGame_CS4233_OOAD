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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import game.common.Location2D;
import game.common.board.IBoardManager;

import org.junit.Before;
import org.junit.Test;

import common.StrategyException;
import common.StrategyRuntimeException;

/**
 * @author Dabrowski
 *
 */
public class GoBoardTest {

	IBoardManager goBoard;
	
	@Before
	public void setup() throws StrategyException{
		goBoard = new GoBoard(7);
	}
	
	@Test
	public void cannotMakeEvenLengthBoard() throws StrategyException{
		for (int i = 6; i < 101; i += 2){
			try{
				new GoBoard(i);		
				fail();
			} catch (StrategyException se){
				// Good, an exception
				assertTrue(true);
			}
		}
	}
	

	@Test
	public void canMakeOddLengthBoard() throws StrategyException{
		for (int i = 5; i < 101; i += 2){
			try{
				new GoBoard(i);			
			} catch (StrategyException se){
				fail();
			}
		}
	}
	
	@Test
	public void cannotMakeBoardSmallerThan5x5() throws StrategyException{
		for (int i = 0; i < 5; i++){
			try {
				new GoBoard(i);
				fail();
			} catch (StrategyException se){
				// good it threw the exception
				assertTrue(true);
			}
		}
	}
	
	
	@Test(expected=StrategyRuntimeException.class)
	public void cannotCall_updateField() throws StrategyException
	{
		goBoard.updateField(null);
	}
	
	@Test(expected=StrategyRuntimeException.class)
	public void cannotCall_getPiecesInPath() throws StrategyException
	{
		goBoard.getPiecesInPath(new Location2D(0,1), new Location2D(1,0));
	}
	
	
	
	
}
