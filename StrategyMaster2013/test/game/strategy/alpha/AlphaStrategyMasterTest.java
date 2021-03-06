/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package game.strategy.alpha;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import game.GameController;
import game.GameFactory;
import game.common.GameVersion;
import game.common.Location;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.turnResult.ITurnResult;
import game.common.turnResult.MoveResultStatus;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.PlayerColor;
import common.StrategyException;

/**
 * Description
 * @author gpollice
 * @version Sep 6, 2013
 */
public class AlphaStrategyMasterTest
{
	private  GameController game;
	private final Location redMarshalLocation = new Location2D(0, 0);
	private final Location redFlagLocation = new Location2D(1, 0);
	private final Location blueFlagLocation = new Location2D(0, 1);
	private final Location blueMarshalLocation = new Location2D(1, 1);
	private final Piece redMarshal = new Piece(PieceType.MARSHAL, PlayerColor.RED);
	private final Piece redFlag = new Piece(PieceType.FLAG, PlayerColor.RED);
	private final Piece blueFlag = new Piece(PieceType.FLAG, PlayerColor.BLUE);
	private final Piece blueMarshal = new Piece(PieceType.MARSHAL, PlayerColor.BLUE);
	
	@BeforeClass
	public static void setupBefore(){
		@SuppressWarnings("unused")
		GameVersion gameVersion = (GameVersion.ALPHA);
	}
	
	@Before
	public  void setup()
	{
		game = GameFactory.getInstance().makeAlphaStrategyGame();
	}
	
	
	@Test(expected=StrategyException.class)
	public void cannotPlacePiece() throws StrategyException
	{
		game.placePiece(new Piece(PieceType.FLAG, PlayerColor.RED), new Location2D(0, 0));
	}
	
	
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeInitialization() throws StrategyException
	{
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
	}
	
	@Test
	public void makeValidFirstMoveRedWins() throws StrategyException
	{
		game.startGame();
		final ITurnResult result = 
				game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
		assertEquals(new PieceLocationDescriptor(redMarshal, blueFlagLocation), 
				result.getBattleWinner());
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveWithWrongPiece() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.CAPTAIN, redMarshalLocation, blueFlagLocation);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveWithIncorrectFromXCoordinate() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(1, 0), blueFlagLocation);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveWithIncorrectFromYCoordinate() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0, 1), blueFlagLocation);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveWithIncorrectToXCoordinate() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, new Location2D(1, 0));
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveWithIncorrectToYCoordinate() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, new Location2D(0, 0));
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveAfterGameIsComplete() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
	}
	
	@Test
	public void playTwoGames() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
	}
	
	@Test
	public void initialPositionHasCorrectPiecesPlaced() throws StrategyException
	{
		game.startGame();
		assertEquals(redMarshal, game.getPieceAt(redMarshalLocation));
		assertEquals(redFlag, game.getPieceAt(redFlagLocation));
		assertEquals(blueFlag, game.getPieceAt(blueFlagLocation));
		assertEquals(blueMarshal, game.getPieceAt(blueMarshalLocation));
	}
	
	@Test
	public void boardIsCorrectAfterFirstMove() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);
		assertNull(game.getPieceAt(redMarshalLocation));
		assertEquals(redFlag, game.getPieceAt(redFlagLocation));
		assertEquals(redMarshal, game.getPieceAt(blueFlagLocation));
		assertEquals(blueMarshal, game.getPieceAt(blueMarshalLocation));
	}
}

