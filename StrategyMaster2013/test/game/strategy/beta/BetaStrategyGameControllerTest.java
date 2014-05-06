/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.strategy.beta;

import static org.junit.Assert.*;
import game.GameController;
import game.GameFactory;
import game.common.GameVersion;
import game.common.Location;
import game.common.Location2D;
import game.common.MoveResult;
import game.common.MoveResultStatus;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.strategy.beta.BetaStrategyGameController;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.*;

import common.PlayerColor;
import common.StrategyException;

/**
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class BetaStrategyGameControllerTest {

	private  GameController game;

	// Red Piece locations
	private final Location redFlagLocation     = new Location2D(0, 1);
	private final Location redMarshalLocation  = new Location2D(1, 1);
	private final Location redColonelLocation1 = new Location2D(2,1);
	private final Location redColonelLocation2 = new Location2D(4,0);
	private final Location redCaptainLocation1 = new Location2D(2, 0);
	private final Location redCaptainLocation2 = new Location2D(3, 1);
	private final Location redLieutenantLocation1 = new Location2D(0, 0);
	private final Location redLieutenantLocation2 = new Location2D(1, 0);
	private final Location redLieutenantLocation3 = new Location2D(4, 1);
	private final Location redSergeantLocation1 = new Location2D(3, 0);
	private final Location redSergeantLocation2 = new Location2D(5, 0);
	private final Location redSergeantLocation3 = new Location2D(5, 1);

	// Blue Piece locations
	private final Location blueFlagLocation = new Location2D(3, 4);
	private final Location blueMarshalLocation = new Location2D(5, 5);
	private final Location blueColonelLocation1 = new Location2D(2, 4);
	private final Location blueColonelLocation2 = new Location2D(0, 5);
	private final Location blueCaptainLocation1 = new Location2D(4, 4);
	private final Location blueCaptainLocation2 = new Location2D(4, 5);
	private final Location blueLieutenantLocation1 = new Location2D(1, 4);
	private final Location blueLieutenantLocation2 = new Location2D(1, 5);
	private final Location blueLieutenantLocation3 = new Location2D(3, 5);
	private final Location blueSergeantLocation1 = new Location2D(2, 5);
	private final Location blueSergeantLocation2 = new Location2D(5, 4);
	private final Location blueSergeantLocation3 = new Location2D(0, 4);

	// Locations for testing (in the middle of the board)
	private final Location L02 = new Location2D(0, 2);
	private final Location L12 = new Location2D(1, 2);
	private final Location L22 = new Location2D(2, 2);
	private final Location L32 = new Location2D(3, 2);
	private final Location L42 = new Location2D(4, 2);
	private final Location L52 = new Location2D(5, 2);
	private final Location L03 = new Location2D(0, 3);
	private final Location L13 = new Location2D(1, 3);
	private final Location L23 = new Location2D(2, 3);
	private final Location L33 = new Location2D(3, 3);
	private final Location L43 = new Location2D(4, 3);
	private final Location L53 = new Location2D(5, 3);

	// Red Pieces
	private final Piece redFlag = new Piece(PieceType.FLAG, PlayerColor.RED);
	private final Piece redMarshal = new Piece(PieceType.MARSHAL, PlayerColor.RED);
	private final Piece redColonel = new Piece(PieceType.COLONEL, PlayerColor.RED);

	private final Piece redCaptain = new Piece(PieceType.CAPTAIN, PlayerColor.RED);
	private final Piece redLieutenant = new Piece(PieceType.LIEUTENANT, PlayerColor.RED);
	private final Piece redSergeant = new Piece(PieceType.SERGEANT, PlayerColor.RED);

	// Blue Pieces
	private final Piece blueFlag = new Piece(PieceType.FLAG, PlayerColor.BLUE);
	private final Piece blueMarshal = new Piece(PieceType.MARSHAL, PlayerColor.BLUE);
	private final Piece blueColonel = new Piece(PieceType.COLONEL, PlayerColor.BLUE);
	private final Piece blueCaptain = new Piece(PieceType.CAPTAIN, PlayerColor.BLUE);
	private final Piece blueLieutenant = new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE);
	private final Piece blueSergeant = new Piece(PieceType.SERGEANT, PlayerColor.BLUE);

	//Piecs + locations
	private final PieceLocationDescriptor redFlagAtLocation = new PieceLocationDescriptor( redFlag, redFlagLocation);
	private final PieceLocationDescriptor blueFlagAtLocation = new PieceLocationDescriptor( blueFlag, blueFlagLocation);

	//Valid moves
	private final Location validMove_RedSerg3 = new Location2D(5,2);
	private final Location validMove_BlueSerg2 = new Location2D(5,3);

	/** the strategy game factory */
	private final GameFactory factory = GameFactory.getInstance();

	/** Configuration for the red team */
	private  List<PieceLocationDescriptor> validRedConfiguration = new LinkedList<PieceLocationDescriptor>();
	/** Configuration for the blue team */
	private  List<PieceLocationDescriptor> validBlueConfiguration = new LinkedList<PieceLocationDescriptor>();
	/** a bad configuration for testing purposes */
	private  List<PieceLocationDescriptor> badConfig = new LinkedList<PieceLocationDescriptor>();

	@BeforeClass
	public static void setupBefore(){
		@SuppressWarnings("unused")
		GameVersion gameVersion = (GameVersion.BETA);
	}
	
	/**
	 * Method setup.
	 * @throws StrategyException
	 */
	@Before
	public void setup() throws StrategyException{
		validBlueConfiguration = new LinkedList<PieceLocationDescriptor>();
		validRedConfiguration = new LinkedList<PieceLocationDescriptor>();
		// Fresh configuration for each test
		validBlueConfiguration.add(blueFlagAtLocation);
		validBlueConfiguration.add(new PieceLocationDescriptor( blueMarshal, blueMarshalLocation));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueColonel, blueColonelLocation1));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueColonel, blueColonelLocation2));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueCaptain, blueCaptainLocation1));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueCaptain, blueCaptainLocation2));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, blueLieutenantLocation1));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, blueLieutenantLocation2));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, blueLieutenantLocation3));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, blueSergeantLocation1));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, blueSergeantLocation2));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, blueSergeantLocation3));
		validRedConfiguration.add(  redFlagAtLocation );
		validRedConfiguration.add(new PieceLocationDescriptor( redMarshal, redMarshalLocation));
		validRedConfiguration.add(new PieceLocationDescriptor( redColonel, redColonelLocation1));
		validRedConfiguration.add(new PieceLocationDescriptor( redColonel, redColonelLocation2));
		validRedConfiguration.add(new PieceLocationDescriptor( redCaptain, redCaptainLocation1));
		validRedConfiguration.add(new PieceLocationDescriptor( redCaptain, redCaptainLocation2));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, redLieutenantLocation1));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, redLieutenantLocation2));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, redLieutenantLocation3));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, redSergeantLocation1));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, redSergeantLocation2));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, redSergeantLocation3));

		// Fresh valid game
		game = factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);
		game.startGame();

		// For general use
		badConfig = new LinkedList<PieceLocationDescriptor>();
	}

	/////////// TESTING makeBetaStrategyGame -- number of pieces
	/**
	 * Method controllerTakesValidConfigurations.
	 * @throws StrategyException
	 */
	@Test
	public void controllerTakesValidConfigurations() throws StrategyException
	{
		new BetaStrategyGameController(validRedConfiguration, validBlueConfiguration);
		assertTrue(true); // No exceptions thrown!
	} 

	/**
	 * Method startGameAfterStarting.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void startGameAfterStarting() throws StrategyException{
		game.startGame();
	}

	/**
	 * Method nullRedConfiguration.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullRedConfiguration() throws StrategyException{
		factory.makeBetaStrategyGame(null, validBlueConfiguration);
	}

	/**
	 * Method noRedFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void noRedFlag() throws StrategyException{
		validRedConfiguration.remove(redFlagAtLocation);
		validRedConfiguration.add(new PieceLocationDescriptor(blueMarshal ,redFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);
	}

	/**
	 * Method testNoBlueFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void testNoBlueFlag() throws StrategyException{
		validBlueConfiguration.remove(blueFlagAtLocation);
		validBlueConfiguration.add(new PieceLocationDescriptor(blueMarshal ,blueFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);
	}

	/**
	 * Method toofewPiecesForRedConfigurationButHasFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void toofewPiecesForRedConfigurationButHasFlag() throws StrategyException{
		badConfig.add(new PieceLocationDescriptor( redFlag, redFlagLocation));
		factory.makeBetaStrategyGame(badConfig, validBlueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{ // a direct copy that works here but not in BetaStrategyMasterTest
		validRedConfiguration.remove(0);
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);
	}
	

	/**
	 * Method tooManyPiecesForRedConfigurationWithOnlyFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void tooManyPiecesForRedConfigurationWithOnlyFlag() throws StrategyException{
		for (int i =0;i<15;i++) badConfig.add(new PieceLocationDescriptor( redFlag, redFlagLocation));
		factory.makeBetaStrategyGame(badConfig, validBlueConfiguration);
	}

	/**
	 * Method nullForBlueConfiguration.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullForBlueConfiguration() throws StrategyException{
		factory.makeBetaStrategyGame(validRedConfiguration, null);
	}

	/**
	 * Method tooFewPiecesForBlueConfigurationButHasFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void tooFewPiecesForBlueConfigurationButHasFlag() throws StrategyException{
		badConfig.add(new PieceLocationDescriptor( blueFlag, blueFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method tooManyPiecesForBlueConfigurationWithOnlyFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void tooManyPiecesForBlueConfigurationWithOnlyFlag() throws StrategyException{
		for (int i =0;i<15;i++) badConfig.add(new PieceLocationDescriptor( blueFlag, blueFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method nullLocation.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullLocation() throws StrategyException{
		badConfig.add(new PieceLocationDescriptor( blueFlag, null));
		factory.makeBetaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method nullPiece.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullPiece() throws StrategyException{
		badConfig.add(new PieceLocationDescriptor( null, blueFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method nullPieceLocationDescription.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullPieceLocationDescription() throws StrategyException{
		badConfig.add(null);
		factory.makeBetaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method invalidPieceType.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void invalidPieceType() throws StrategyException{
		final Piece badPice = new Piece(PieceType.BOMB, PlayerColor.BLUE);		
		badConfig.add(new PieceLocationDescriptor( badPice, blueFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration,badConfig );
	}

	/**
	 * Method nullPieceOwner.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullPieceOwner() throws StrategyException{
		final Piece badPice = new Piece(PieceType.BOMB, null);		
		badConfig.add(new PieceLocationDescriptor( badPice, blueFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration,badConfig );
	}

	/**
	 * Method nullPieceType.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullPieceType() throws StrategyException{
		final Piece badPice = new Piece(null, PlayerColor.BLUE);		
		badConfig.add(new PieceLocationDescriptor( badPice, blueFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration,badConfig );
	}

	/////////// TESTING makeBetaStrategyGame -- locations
	/**
	 * Method locationUsedTwice.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void locationUsedTwice() throws StrategyException{
		validRedConfiguration.add(new PieceLocationDescriptor( blueFlag, blueFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);
	}

	/**
	 * Method bluePieceInNeutralZone.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void bluePieceInNeutralZone() throws StrategyException {
		validBlueConfiguration.remove(blueFlagAtLocation); // remove flag
		validBlueConfiguration.add(new PieceLocationDescriptor( blueFlag,new Location2D(3, 3)));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method redPieceInNeutralZone.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void redPieceInNeutralZone() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(3, 2)));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method playerFlagsInEnemyZones.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void playerFlagsInEnemyZones() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validBlueConfiguration.remove(blueFlagAtLocation); // remove flag
		validBlueConfiguration.add(new PieceLocationDescriptor( redFlag,blueFlagLocation));
		validRedConfiguration.add(new PieceLocationDescriptor( blueFlag, redFlagLocation));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);		
	}

	/**
	 * Method pieceOutOfBoundsY.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceOutOfBoundsY() throws StrategyException {
		validBlueConfiguration.remove(blueFlagAtLocation); // remove flag
		validBlueConfiguration.add(new PieceLocationDescriptor( blueFlag, new Location2D(0, 12)));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method pieceOutOfBoundsX.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceOutOfBoundsX() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(11, 1)));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method pieceInNegativeX.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceInNegativeX() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(-1, 0)));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method pieceInNegativeY.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceInNegativeY() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(0, -1)));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method pieceInNegativeCoordinates.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceInNegativeCoordinates() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(-1, -1)));
		factory.makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	////// getPieceAt
	/**
	 * Method initialPositionHasCorrectREDPiecesPlaced.
	 */
	@Test
	public void initialPositionHasCorrectREDPiecesPlaced() {
		final Iterator<PieceLocationDescriptor> redIter = validRedConfiguration.iterator();
		// Check the entire red configuration
		while (redIter.hasNext()){
			PieceLocationDescriptor next = redIter.next();
			Piece aPiece = next.getPiece();
			Location aLocation = next.getLocation();
			assertEquals(aPiece, game.getPieceAt(aLocation));	
		}				
	}

	/**
	 * Method initialPositionHasCorrectBLUEPiecesPlaced.
	 */
	@Test
	public void initialPositionHasCorrectBLUEPiecesPlaced() {
		final Iterator<PieceLocationDescriptor> blueIter = validBlueConfiguration.iterator();
		// Check the entire blue configuration
		while (blueIter.hasNext()){
			PieceLocationDescriptor next = blueIter.next();
			Piece aPiece = next.getPiece();
			Location aLocation = next.getLocation();
			assertEquals(aPiece, game.getPieceAt(aLocation));	
		}				
	}

	/**
	 * Method getPieceAtIllegalLocations.
	 */
	@Test
	public void getPieceAtIllegalLocations()  {
		assertEquals(null, game.getPieceAt(new Location2D(-1 ,  0)));	
		assertEquals(null, game.getPieceAt(new Location2D(-1 , -1)));	
		assertEquals(null, game.getPieceAt(new Location2D(0  , -1)));	
		assertEquals(null, game.getPieceAt(new Location2D(6  ,  0)));	
		assertEquals(null, game.getPieceAt(new Location2D(0  ,  6)));	
		assertEquals(null, game.getPieceAt(new Location2D(6  ,  6)));
	}

	/**
	 * Method getPieceAt_worksAfterTwoMoves.
	 * @throws StrategyException
	 */
	@Test
	public void getPieceAt_worksAfterTwoMoves() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  validMove_RedSerg3);
		game.move(PieceType.SERGEANT, blueSergeantLocation2 ,  validMove_BlueSerg2);
		assertTrue(true); // have to get this far with moves
		assertEquals(PieceType.SERGEANT, game.getPieceAt(validMove_RedSerg3).getType());
		assertEquals(PieceType.SERGEANT, game.getPieceAt(validMove_BlueSerg2).getType());				
	}


	//////////// MOVEMENT
	/**
	 * Method makeMoveBeforeInitialization.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeInitialization() throws StrategyException	{
		final GameController agame = GameFactory.getInstance().makeBetaStrategyGame(validRedConfiguration, validBlueConfiguration);
		agame.move(PieceType.SERGEANT, redSergeantLocation3 ,  new Location2D(5, 2));
	}

	/**
	 * Method moveOffBoardXNegative.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveOffBoardXNegative() throws StrategyException {
		game.move(PieceType.LIEUTENANT, redLieutenantLocation1 ,  new Location2D(-1, 0));	
	}

	/**
	 * Method moveOffBoardYNegative.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveOffBoardYNegative() throws StrategyException {
		game.move(PieceType.LIEUTENANT, redLieutenantLocation1 ,  new Location2D(0, -1));		
	}

	/**
	 * Method moveOffBoardXPositive.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveOffBoardXPositive() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  new Location2D(6, 1));			
	}

	/**
	 * Method moveOffBoardYPositive.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveOffBoardYPositive() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  validMove_RedSerg3);
		game.move(PieceType.MARSHAL, blueMarshalLocation ,  new Location2D(5, 6));
	}

	/**
	 * Method badFromNegativeX.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void badFromNegativeX() throws StrategyException {
		game.move(PieceType.SERGEANT, new Location2D(-1, 0) ,  validMove_RedSerg3);
	}

	/**
	 * Method badFromNegativeY.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void badFromNegativeY() throws StrategyException {
		game.move(PieceType.SERGEANT, new Location2D(0, -1) ,  validMove_RedSerg3);
	}

	/**
	 * Method badFromOutOfBoundsX.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void badFromOutOfBoundsX() throws StrategyException {
		game.move(PieceType.SERGEANT, new Location2D(6, 0) ,  validMove_RedSerg3);
	}

	/**
	 * Method badFromOutOfBoundsY.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void badFromOutOfBoundsY() throws StrategyException {
		game.move(PieceType.SERGEANT, new Location2D(0, 6) ,  validMove_RedSerg3);
	}

	/**
	 * Method moveFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveFlag() throws StrategyException {
		game.move(PieceType.FLAG, redFlagLocation ,  new Location2D(0,2));
	}

	/**
	 * Method moveWrongPiece.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveWrongPiece() throws StrategyException {
		game.move(PieceType.MARSHAL, redFlagLocation ,  new Location2D(0,2));
	}

	/**
	 * Method blueTriesToGoFirst.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void blueTriesToGoFirst() throws StrategyException {
		game.move(PieceType.SERGEANT, blueSergeantLocation2 ,  validMove_BlueSerg2);
	}

	/**
	 * Method redTriesToGoTwice.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void redTriesToGoTwice() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  validMove_BlueSerg2);
		game.move(PieceType.SERGEANT, validMove_BlueSerg2 ,  new Location2D(5,3));

	}

	/**
	 * Method moveNonAdjacentX.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveNonAdjacentX() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  validMove_RedSerg3);
		game.move(PieceType.SERGEANT, blueSergeantLocation2 ,  validMove_BlueSerg2);
		assertTrue(true); // have to get this far
		game.move(PieceType.SERGEANT, validMove_RedSerg3 ,  new Location2D(3,2));
	}

	/**
	 * Method moveNonAdjacentY.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveNonAdjacentY() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  new Location2D(5,4));
	}

	/**
	 * Method moveDiagonal.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveDiagonal() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  new Location2D(4,2));
	}

	/**
	 * Method moveFartherThanDiagonalAdjacent.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveFartherThanDiagonalAdjacent() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  new Location2D(2,3));
	}

	/**
	 * Method redMovesAPieceOntoHisOtherPiece.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void redMovesAPieceOntoHisOtherPiece() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,  redSergeantLocation2);
	}

	/**
	 * Method gameEndsAfterSIXMoves.
	 * @throws StrategyException
	 */
	@Test
	public void gameEndsAfterSIXMoves() throws StrategyException {
		game.move(PieceType.SERGEANT, redSergeantLocation3 ,   validMove_RedSerg3);
		game.move(PieceType.SERGEANT, blueSergeantLocation2 ,  validMove_BlueSerg2);

		game.move(PieceType.SERGEANT, validMove_RedSerg3 ,  redSergeantLocation3);
		game.move(PieceType.SERGEANT, validMove_BlueSerg2 ,  blueSergeantLocation2);

		game.move(PieceType.SERGEANT, redSergeantLocation3 ,   validMove_RedSerg3);
		game.move(PieceType.SERGEANT, blueSergeantLocation2 ,  validMove_BlueSerg2);
	}

	/**
	 * Method moveAfterGameOver.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveAfterGameOver() throws StrategyException {
		for (int i = 0; i < 3; i++){
			game.move(PieceType.SERGEANT, redSergeantLocation3 ,   validMove_RedSerg3);
			game.move(PieceType.SERGEANT, blueSergeantLocation2 ,  validMove_BlueSerg2);	
			game.move(PieceType.SERGEANT, validMove_RedSerg3 ,  redSergeantLocation3);
			game.move(PieceType.SERGEANT, validMove_BlueSerg2 ,  blueSergeantLocation2);		
		}
		game.move(PieceType.SERGEANT, validMove_RedSerg3 ,  redSergeantLocation3);
	}

	////// BATTLE
	/**
	 * Method battle1_MarshalWinsALot.
	 * @throws StrategyException
	 */
	@Test
	public void battle1_MarshalWinsALot() throws StrategyException {
		assertSame(game.move(PieceType.MARSHAL,    redMarshalLocation,      L12).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.LIEUTENANT, blueLieutenantLocation1, L13).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.MARSHAL, L12,                        L13).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(L13).getType(),PieceType.MARSHAL );
		assertSame(game.move(PieceType.SERGEANT, blueSergeantLocation3,    blueLieutenantLocation1).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.MARSHAL, L13,                       blueLieutenantLocation1).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(blueLieutenantLocation1).getType(),PieceType.MARSHAL );
		assertSame(game.move(PieceType.COLONEL, blueColonelLocation1,      blueLieutenantLocation1).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(blueColonelLocation1).getType(),PieceType.MARSHAL );
	}

	/**
	 * Method battle2_ColonelDraw_FlagCapture.
	 * @throws StrategyException
	 */
	@Test
	public void battle2_ColonelDraw_FlagCapture() throws StrategyException {
		assertSame(game.move(PieceType.COLONEL,    redColonelLocation1,      L22).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, blueSergeantLocation3, L03).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.COLONEL, L22,         L23).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, L03,    L02).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.COLONEL, L23,       blueColonelLocation1).getStatus(), MoveResultStatus.OK );
		assertNull(game.getPieceAt(L23));
		assertNull(game.getPieceAt(blueColonelLocation1));
		assertSame(game.move(PieceType.SERGEANT, L02,      redFlagLocation).getStatus(), MoveResultStatus.BLUE_WINS );
		assertSame(game.getPieceAt(redFlagLocation).getType(), PieceType.SERGEANT);
	}

	/**
	 * Method battle3_Captain_Lieutenant_Colonel.
	 * @throws StrategyException
	 */
	@Test
	public void battle3_Captain_Lieutenant_Colonel() throws StrategyException {
		assertSame(game.move(PieceType.LIEUTENANT,    redLieutenantLocation3,      L42).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.CAPTAIN, blueCaptainLocation1, L43).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.LIEUTENANT, L42,         L43).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(L42).getType(), PieceType.CAPTAIN);
		assertSame(game.move(PieceType.CAPTAIN, L42,    redLieutenantLocation3).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.COLONEL, redColonelLocation2,       redLieutenantLocation3).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(redLieutenantLocation3).getType(), PieceType.COLONEL);
	}

	/**
	 * Method battle3_SergeantDraw.
	 * @throws StrategyException
	 */
	@Test
	public void battle3_SergeantDraw() throws StrategyException {
		assertSame(game.move(PieceType.CAPTAIN,    redCaptainLocation2,      L32).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, blueSergeantLocation2, L53).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.CAPTAIN, L32,         L33).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, L53,    L52).getStatus(), MoveResultStatus.OK );
		final MoveResult drawResult= game.move(PieceType.SERGEANT, redSergeantLocation3,       L52);
		assertNull(drawResult.getBattleWinner());
		assertSame(drawResult.getStatus(), MoveResultStatus.OK );
		assertNull(game.getPieceAt(redSergeantLocation3));
		assertNull(game.getPieceAt(L52));		
		assertSame(game.move(PieceType.MARSHAL, blueMarshalLocation,      blueSergeantLocation2).getStatus(), MoveResultStatus.OK );
	}

	/**
	 * Method battle4_redGetsBlueFlag.
	 * @throws StrategyException
	 */
	@Test
	public void battle4_redGetsBlueFlag()throws StrategyException {
		assertSame(game.move(PieceType.CAPTAIN,    redCaptainLocation2,      L32).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, blueSergeantLocation2, L53).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.CAPTAIN, L32,         L33).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, L53,    L52).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.CAPTAIN, L33,         blueFlagLocation).getStatus(), MoveResultStatus.RED_WINS );
	}	
}
