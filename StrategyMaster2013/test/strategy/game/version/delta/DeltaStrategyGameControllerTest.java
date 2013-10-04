/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.common.StrategyRuntimeException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.GameVersion;
import strategy.game.common.Location;
import strategy.game.common.Location1D;
import strategy.game.common.Location2D;
import strategy.game.common.Location3D;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/** These are the tests for Delta Strategy
 * 
 * @author Dabrowski  
 * @version $Revision: 1.0 $
 */
public class DeltaStrategyGameControllerTest {

	private  StrategyGameController game;

	// Red Piece locations
	private final Location L01     = new Location2D(0, 1);
	private final Location L11  = new Location2D(1, 1);
	private final Location L21 = new Location2D(2,1);
	private final Location L40 = new Location2D(4,0);
	private final Location L20 = new Location2D(2, 0);
	private final Location L31 = new Location2D(3, 1);
	private final Location L00 = new Location2D(0, 0);
	private final Location L10 = new Location2D(1, 0);
	private final Location L41 = new Location2D(4, 1);
	private final Location L30 = new Location2D(3, 0);
	private final Location L50 = new Location2D(5, 0);
	private final Location L51 = new Location2D(5, 1);

	// Blue Piece locations
	private final Location L34 = new Location2D(3, 4);
	private final Location L55 = new Location2D(5, 5);
	private final Location L24 = new Location2D(2, 4);
	private final Location L05 = new Location2D(0, 5);
	private final Location L44 = new Location2D(4, 4);
	private final Location L45 = new Location2D(4, 5);
	private final Location L14 = new Location2D(1, 4);
	private final Location L15 = new Location2D(1, 5);
	private final Location L35 = new Location2D(3, 5);
	private final Location L25 = new Location2D(2, 5);
	private final Location L54 = new Location2D(5, 4);
	private final Location L04 = new Location2D(0, 4);

	/*
	 * The board with the initial configuration looks like this:
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 5 | COL2| LT2 | SGT1| LT3 | CPT2| MAR |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 4 | SGT3| LT1 | COL1|  F  | CPT1| SGT2|     BLUE
	 * - +-----+-----+-----+-----+-----+-----+
	 * 3 |     |     |  X  | X   |     |     |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 2 |     |     |  X  | X   |     |     |
	 * - +-----+-----+-----+-----+-----+-----+
	 * 1 |  F  | MAR | COL1| CPT2|LT 3 | SGT3|
	 * - +-----+-----+-----+-----+-----+-----+
	 * 0 | LT 1| LT 2| CPT1| SGT1| COL2| SGT2|     RED
	 * - +-----+-----+-----+-----+-----+-----+
	 *   |  0  |  1  |  2  |  3  |  4  |  5  |
	 */
	
	// Locations for testing (in the middle of the board)
	private final Location L02 = new Location2D(0, 2);
	private final Location L12 = new Location2D(1, 2);
	private final Location L22 = new Location2D(2, 2); // choke point
	private final Location L32 = new Location2D(3, 2); // choke point
	private final Location L42 = new Location2D(4, 2);
	private final Location L52 = new Location2D(5, 2);
	private final Location L03 = new Location2D(0, 3);
	private final Location L13 = new Location2D(1, 3);
	private final Location L23 = new Location2D(2, 3); // choke point
	private final Location L33 = new Location2D(3, 3); // choke point
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

	// Choke point
	private final Piece chokePoint = new Piece(PieceType.CHOKE_POINT, null);
	
	//Piecs + locations
	private final PieceLocationDescriptor redFlagAtLocation = new PieceLocationDescriptor( redFlag, L01);
	private final PieceLocationDescriptor blueFlagAtLocation = new PieceLocationDescriptor( blueFlag, L34);

	//Valid moves
	private final Location validMove_RedSerg3 = new Location2D(5,2);
	private final Location validMove_BlueSerg2 = new Location2D(5,3);

	/** the strategy game factory */
	private final StrategyGameFactory factory = StrategyGameFactory.getInstance();

	/** Configuration for the red team */
	private  List<PieceLocationDescriptor> validRedConfiguration = new LinkedList<PieceLocationDescriptor>();
	/** Configuration for the blue team */
	private  List<PieceLocationDescriptor> validBlueConfiguration = new LinkedList<PieceLocationDescriptor>();
	/** a bad configuration for testing purposes */
	private  List<PieceLocationDescriptor> badConfig = new LinkedList<PieceLocationDescriptor>();

	/** a test double for a game */
	private DeltaStrategyGameControllerTestDouble deltaTestDouble;
	
	@BeforeClass
	public static void setupBefore(){
		@SuppressWarnings("unused")
		GameVersion gameVersion = (GameVersion.DELTA);
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
		validBlueConfiguration.add(new PieceLocationDescriptor( blueMarshal, L55));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueColonel, L24));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueColonel, L05));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueCaptain, L44));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueCaptain, L45));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, L14));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, L15));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, L35));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, L25));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, L54));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, L04));
		validRedConfiguration.add(  redFlagAtLocation );
		validRedConfiguration.add(new PieceLocationDescriptor( redMarshal, L11));
		validRedConfiguration.add(new PieceLocationDescriptor( redColonel, L21));
		validRedConfiguration.add(new PieceLocationDescriptor( redColonel, L40));
		validRedConfiguration.add(new PieceLocationDescriptor( redCaptain, L20));
		validRedConfiguration.add(new PieceLocationDescriptor( redCaptain, L31));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, L00));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, L10));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, L41));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, L30));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, L50));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, L51));

		// Fresh valid game
		game = factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);
		game.startGame();

		// For general use
		badConfig = new LinkedList<PieceLocationDescriptor>();
		deltaTestDouble = new DeltaStrategyGameControllerTestDouble(validRedConfiguration, validBlueConfiguration, new DeltaRules());
	}

	/////////// TESTING makeDeltaStrategyGame -- number of pieces
	/**
	 * Method controllerTakesValidConfigurations.
	 * @throws StrategyException
	 */
	@Test
	public void controllerTakesValidConfigurations() throws StrategyException
	{
		game = factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);
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
		factory.makeDeltaStrategyGame(null, validBlueConfiguration);
	}

	/**
	 * Method noRedFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void noRedFlag() throws StrategyException{
		validRedConfiguration.remove(redFlagAtLocation);
		validRedConfiguration.add(new PieceLocationDescriptor(blueMarshal ,L01));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);
	}

	/**
	 * Method testNoBlueFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void testNoBlueFlag() throws StrategyException{
		validBlueConfiguration.remove(blueFlagAtLocation);
		validBlueConfiguration.add(new PieceLocationDescriptor(blueMarshal ,L34));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);
	}

	/**
	 * Method toofewPiecesForRedConfigurationButHasFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void toofewPiecesForRedConfigurationButHasFlag() throws StrategyException{
		badConfig.add(new PieceLocationDescriptor( redFlag, L01));
		factory.makeDeltaStrategyGame(badConfig, validBlueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{ 
		validRedConfiguration.remove(0);
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);
	}
	

	/**
	 * Method tooManyPiecesForRedConfigurationWithOnlyFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void tooManyPiecesForRedConfigurationWithOnlyFlag() throws StrategyException{
		for (int i =0;i<15;i++) badConfig.add(new PieceLocationDescriptor( redFlag, L01));
		factory.makeDeltaStrategyGame(badConfig, validBlueConfiguration);
	}

	/**
	 * Method nullForBlueConfiguration.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullForBlueConfiguration() throws StrategyException{
		factory.makeDeltaStrategyGame(validRedConfiguration, null);
	}

	/**
	 * Method tooFewPiecesForBlueConfigurationButHasFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void tooFewPiecesForBlueConfigurationButHasFlag() throws StrategyException{
		badConfig.add(new PieceLocationDescriptor( blueFlag, L34));
		factory.makeDeltaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method tooManyPiecesForBlueConfigurationWithOnlyFlag.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void tooManyPiecesForBlueConfigurationWithOnlyFlag() throws StrategyException{
		for (int i =0;i<15;i++) badConfig.add(new PieceLocationDescriptor( blueFlag, L34));
		factory.makeDeltaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method nullLocation.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullLocation() throws StrategyException{
		badConfig.add(new PieceLocationDescriptor( blueFlag, null));
		factory.makeDeltaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method nullPiece.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullPiece() throws StrategyException{
		badConfig.add(new PieceLocationDescriptor( null, L34));
		factory.makeDeltaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method nullPieceLocationDescription.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullPieceLocationDescription() throws StrategyException{
		badConfig.add(null);
		factory.makeDeltaStrategyGame(validRedConfiguration, badConfig);
	}

	/**
	 * Method invalidPieceType.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void invalidPieceType() throws StrategyException{
		final Piece badPice = new Piece(PieceType.BOMB, PlayerColor.BLUE);		
		badConfig.add(new PieceLocationDescriptor( badPice, L34));
		factory.makeDeltaStrategyGame(validRedConfiguration,badConfig );
	}

	/**
	 * Method nullPieceOwner.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullPieceOwner() throws StrategyException{
		final Piece badPice = new Piece(PieceType.BOMB, null);		
		badConfig.add(new PieceLocationDescriptor( badPice, L34));
		factory.makeDeltaStrategyGame(validRedConfiguration,badConfig );
	}

	/**
	 * Method nullPieceType.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void nullPieceType() throws StrategyException{
		final Piece badPice = new Piece(null, PlayerColor.BLUE);		
		badConfig.add(new PieceLocationDescriptor( badPice, L34));
		factory.makeDeltaStrategyGame(validRedConfiguration,badConfig );
	}

	/////////// TESTING makeDeltaStrategyGame -- locations
	/**
	 * Method locationUsedTwice.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void locationUsedTwice() throws StrategyException{
		validRedConfiguration.add(new PieceLocationDescriptor( blueFlag, L34));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);
	}

	/**
	 * Method bluePieceInNeutralZone.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void bluePieceInNeutralZone() throws StrategyException {
		validBlueConfiguration.remove(blueFlagAtLocation); // remove flag
		validBlueConfiguration.add(new PieceLocationDescriptor( blueFlag,new Location2D(3, 3)));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method redPieceInNeutralZone.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void redPieceInNeutralZone() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, L32));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method playerFlagsInEnemyZones.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void playerFlagsInEnemyZones() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validBlueConfiguration.remove(blueFlagAtLocation); // remove flag
		validBlueConfiguration.add(new PieceLocationDescriptor(redFlag, L34 ));
		validRedConfiguration.add(new PieceLocationDescriptor( blueFlag,L01));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);		
	}

	/**
	 * Method pieceOutOfBoundsY.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceOutOfBoundsY() throws StrategyException {
		validBlueConfiguration.remove(blueFlagAtLocation); // remove flag
		validBlueConfiguration.add(new PieceLocationDescriptor( blueFlag, new Location2D(0, 12)));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method pieceOutOfBoundsX.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceOutOfBoundsX() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(11, 1)));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method pieceInNegativeX.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceInNegativeX() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(-1, 0)));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method pieceInNegativeY.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceInNegativeY() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(0, -1)));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	/**
	 * Method pieceInNegativeCoordinates.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void pieceInNegativeCoordinates() throws StrategyException {
		validRedConfiguration.remove(redFlagAtLocation); // remove flag
		validRedConfiguration.add(new PieceLocationDescriptor( redFlag, new Location2D(-1, -1)));
		factory.makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);	
	}

	////// getPieceAt
	/**
	 * Method initialPositionHasCorrectREDPiecesPlaced.
	 */
	@Test
	public void getPieceAtCanUseNonLocation2DSometimes(){
		assertSame(redLieutenant, game.getPieceAt(new Location3D(0,0,3)));
		assertTrue(true);// no exception thrown
	}
	
	@Test(expected=StrategyRuntimeException.class)
	public void getPieceAtThrowsExceptionWhenItCantUseALocation(){
		game.getPieceAt(new Location1D(1));		
	}
		
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
		game.move(PieceType.SERGEANT, L51 ,  validMove_RedSerg3);
		game.move(PieceType.SERGEANT, L54 ,  validMove_BlueSerg2);
		assertTrue(true); // have to get this far with moves
		assertEquals(PieceType.SERGEANT, game.getPieceAt(validMove_RedSerg3).getType());
		assertEquals(PieceType.SERGEANT, game.getPieceAt(validMove_BlueSerg2).getType());				
	}

	@Test
	public void getPieceAtChokePoints() throws StrategyException{
		assertSame(chokePoint.getType(), game.getPieceAt(L33).getType() );
		assertSame(chokePoint.getType(), game.getPieceAt(L32).getType() );
		assertSame(chokePoint.getType(), game.getPieceAt(L22).getType() );
		assertSame(chokePoint.getType(), game.getPieceAt(L23).getType() );
	}
	

	//////////// MOVEMENT
	/**
	 * Method makeMoveBeforeInitialization.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeInitialization() throws StrategyException	{
		final StrategyGameController agame = StrategyGameFactory.getInstance().makeDeltaStrategyGame(validRedConfiguration, validBlueConfiguration);
		agame.move(PieceType.SERGEANT, L51 ,  new Location2D(5, 2));
	}

	/**
	 * Method moveOffBoardXNegative.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveOffBoardXNegative() throws StrategyException {
		game.move(PieceType.LIEUTENANT, L00 ,  new Location2D(-1, 0));	
	}

	/**
	 * Method moveOffBoardYNegative.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveOffBoardYNegative() throws StrategyException {
		game.move(PieceType.LIEUTENANT, L00 ,  new Location2D(0, -1));		
	}

	/**
	 * Method moveOffBoardXPositive.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveOffBoardXPositive() throws StrategyException {
		game.move(PieceType.SERGEANT, new Location2D(11,0) ,  new Location2D(6, 1));			
	}

	/**
	 * Method moveOffBoardYPositive.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveOffBoardYPositive() throws StrategyException {
		game.move(PieceType.SERGEANT, L51 ,  validMove_RedSerg3);
		game.move(PieceType.MARSHAL, L55 ,  new Location2D(5, 11));
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
		game.move(PieceType.FLAG, L01 ,  new Location2D(0,2));
	}

	/**
	 * Method moveWrongPiece.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveWrongPiece() throws StrategyException {
		game.move(PieceType.MARSHAL, L01 ,  new Location2D(0,2));
	}

	/**
	 * Method blueTriesToGoFirst.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void blueTriesToGoFirst() throws StrategyException {
		game.move(PieceType.SERGEANT, L54 ,  validMove_BlueSerg2);
	}

	/**
	 * Method redTriesToGoTwice.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void redTriesToGoTwice() throws StrategyException {
		game.move(PieceType.SERGEANT, L51 ,  validMove_BlueSerg2);
		game.move(PieceType.SERGEANT, validMove_BlueSerg2 ,  new Location2D(5,3));

	}

	/**
	 * Method moveNonAdjacentX.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveNonAdjacentX() throws StrategyException {
		game.move(PieceType.SERGEANT, L51 ,  validMove_RedSerg3);
		game.move(PieceType.SERGEANT, L54 ,  validMove_BlueSerg2);
		assertTrue(true); // have to get this far
		game.move(PieceType.SERGEANT, validMove_RedSerg3 ,  new Location2D(0,2));
	}

	/**
	 * Method moveNonAdjacentY.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveNonAdjacentY() throws StrategyException {
		game.move(PieceType.SERGEANT, L51 ,  new Location2D(5,4));
	}

	/**
	 * Method moveDiagonal.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveDiagonal() throws StrategyException {
		game.move(PieceType.SERGEANT, L51 ,  new Location2D(4,2));
	}

	/**
	 * Method moveFartherThanDiagonalAdjacent.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void moveFartherThanDiagonalAdjacent() throws StrategyException {
		game.move(PieceType.SERGEANT, L51 ,  new Location2D(0,3));
	}

	/**
	 * Method redMovesAPieceOntoHisOtherPiece.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void redMovesAPieceOntoHisOtherPiece() throws StrategyException {
		game.move(PieceType.SERGEANT, L51 ,  L50);
	}

	@Test(expected=StrategyException.class)
	public void redMovesOntoChokePoint() throws StrategyException{
		assertSame(game.move(PieceType.COLONEL,    L21,      L22).getStatus(), MoveResultStatus.OK );
	}
	
	/**
	 * Method gameEndsAfterSIXMoves.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void violateMoveRepitionRule() throws StrategyException {
		game.move(PieceType.SERGEANT,   L51 ,   L52);
		game.move(PieceType.LIEUTENANT, L14 ,   L13);
		game.move(PieceType.SERGEANT,   L52 ,   L51);
		game.move(PieceType.CAPTAIN,    L44 ,   L43);
		game.move(PieceType.SERGEANT,   L51 ,   L52); 
	}

	@Test(expected=StrategyException.class)
	public void violateMoveRepetitionRule2() throws StrategyException {
		game.move(PieceType.MARSHAL, L11, L12);      //RED
		game.move(PieceType.LIEUTENANT, L14, L13);
		game.move(PieceType.LIEUTENANT, L41, L42);			//RED
		game.move(PieceType.COLONEL, L24, L14); 
		game.move(PieceType.SERGEANT, L51, L52);			//RED
		game.move(PieceType.LIEUTENANT, L13, L12);
		game.move(PieceType.MARSHAL, L13, L03); //RED
		game.move(PieceType.COLONEL, L14, L13);
		game.move(PieceType.COLONEL, L21, L11); //RED
		game.move(PieceType.COLONEL, L13, L12); 
		game.move(PieceType.COLONEL, L11, L12);//RED - its a tie battle
		game.move(PieceType.LIEUTENANT, L15, L14);
		game.move(PieceType.MARSHAL, L03, L04);//RED
		game.move(PieceType.SERGEANT, L25, L24);
		game.move(PieceType.MARSHAL, L04, L14);//RED
		game.move(PieceType.SERGEANT, L24, L14);//////////////
		
		game.move(PieceType.SERGEANT, L52, L53);//RED
		game.move(PieceType.SERGEANT, L54, L53);
		game.move(PieceType.MARSHAL, L24, L25);//RED
		game.move(PieceType.LIEUTENANT, L35, L25);
		game.move(PieceType.MARSHAL, L35, L45);//RED
		game.move(PieceType.MARSHAL, L55, L54);
		game.move(PieceType.MARSHAL, L45, L44);//RED
		game.move(PieceType.MARSHAL, L54, L53);
		game.move(PieceType.MARSHAL, L44, L45);//RED
		game.move(PieceType.MARSHAL, L53, L52);
		game.move(PieceType.MARSHAL, L45, L35);//RED <----
		game.move(PieceType.MARSHAL, L52, L51);//Blue
		game.move(PieceType.MARSHAL, L35, L45);//RED <----
		game.move(PieceType.MARSHAL, L51, L50);//Blue
		game.move(PieceType.MARSHAL, L45, L35);//RED <----
	}


	////// BATTLE
	/**
	 * Method battle1_MarshalWinsALot.
	 * @throws StrategyException
	 */
	@Test
	public void battle1_MarshalWinsALot() throws StrategyException {
		assertSame(game.move(PieceType.MARSHAL,    L11,      L12).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.LIEUTENANT, L14, L13).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.MARSHAL, L12,                        L13).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(L13).getType(),PieceType.MARSHAL );
		assertSame(game.move(PieceType.SERGEANT, L04,    L14).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.MARSHAL, L13,                       L14).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(L14).getType(),PieceType.MARSHAL );
		assertSame(game.move(PieceType.COLONEL, L24,      L14).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(L24).getType(),PieceType.MARSHAL );
	}

	/**
	 * Method battle2_ColonelDraw_FlagCapture.
	 * @throws StrategyException
	 */
	@Test
	public void battle2_ColonelDraw_FlagCapture() throws StrategyException {
		game.move(PieceType.MARSHAL, L11, L12);      //RED
		game.move(PieceType.LIEUTENANT, L14, L13);
		game.move(PieceType.LIEUTENANT, L41, L42);											//RED
		game.move(PieceType.COLONEL, L24, L14); 
		game.move(PieceType.LIEUTENANT, L42, L43);											//RED
		game.move(PieceType.LIEUTENANT, L13, L12);
		game.move(PieceType.MARSHAL, L13, L03); //RED
		game.move(PieceType.COLONEL, L14, L13);
		game.move(PieceType.COLONEL, L21, L11); //RED
		game.move(PieceType.COLONEL, L13, L12); 
		game.move(PieceType.COLONEL, L11, L12);//RED - its a tie
		game.move(PieceType.LIEUTENANT, L15, L14);
		game.move(PieceType.MARSHAL, L03, L04);//RED
		game.move(PieceType.SERGEANT, L25, L24);
		game.move(PieceType.MARSHAL, L04, L14);//RED
		game.move(PieceType.SERGEANT, L24, L14);
		assertSame(game.move(PieceType.MARSHAL, L24, L34).getStatus(), MoveResultStatus.RED_WINS);//RED
		assertSame(game.getPieceAt(L34).getType(), PieceType.MARSHAL);
	}


	/**
	 * Method battle3_Captain_Lieutenant_Colonel.
	 * @throws StrategyException
	 */
	@Test
	public void battle3_Captain_Lieutenant_Colonel() throws StrategyException {
		assertSame(game.move(PieceType.LIEUTENANT,    L41,      L42).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.CAPTAIN, L44, L43).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.LIEUTENANT, L42,         L43).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(L42).getType(), PieceType.CAPTAIN);
		assertSame(game.move(PieceType.CAPTAIN, L42,    L41).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.COLONEL, L40,       L41).getStatus(), MoveResultStatus.OK );
		assertSame(game.getPieceAt(L41).getType(), PieceType.COLONEL);
	}

	/**
	 * Method battle5_blueGetsRedFlag
	 * @throws StrategyException
	 */
	@Test
	public void battle5_blueGetsRedFlag()throws StrategyException {
		assertSame(game.move(PieceType.LIEUTENANT,    L41,      L42).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, L04, L03).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.LIEUTENANT, L42,         L43).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, L03,    L02).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.LIEUTENANT, L43,         L44).getStatus(), MoveResultStatus.OK );
		assertSame(game.move(PieceType.SERGEANT, L02,    L01).getStatus(), MoveResultStatus.BLUE_WINS );

	}	
	
	/**
	 * Method battle3_SergeantDraw.
	 * @throws StrategyException
	 */
	@Test(expected=StrategyException.class)
	public void fullGame_blueCantMove_andMoveAfterGame() throws StrategyException {
		game.move(PieceType.MARSHAL, L11, L12);      //RED
		game.move(PieceType.LIEUTENANT, L14, L13);
		game.move(PieceType.LIEUTENANT, L41, L42);			//RED
		game.move(PieceType.COLONEL, L24, L14); 
		game.move(PieceType.SERGEANT, L51, L52);			//RED
		game.move(PieceType.LIEUTENANT, L13, L12);
		game.move(PieceType.MARSHAL, L13, L03); //RED
		game.move(PieceType.COLONEL, L14, L13);
		game.move(PieceType.COLONEL, L21, L11); //RED
		game.move(PieceType.COLONEL, L13, L12); 
		game.move(PieceType.COLONEL, L11, L12);//RED - its a tie battle
		game.move(PieceType.LIEUTENANT, L15, L14);
		game.move(PieceType.MARSHAL, L03, L04);//RED
		game.move(PieceType.SERGEANT, L25, L24);
		game.move(PieceType.MARSHAL, L04, L14);//RED
		game.move(PieceType.SERGEANT, L24, L14);//////////////
		
		game.move(PieceType.SERGEANT, L52, L53);//RED
		game.move(PieceType.SERGEANT, L54, L53);
		game.move(PieceType.MARSHAL, L24, L25);//RED
		game.move(PieceType.LIEUTENANT, L35, L25);
		game.move(PieceType.MARSHAL, L35, L45);//RED
		game.move(PieceType.COLONEL, L05, L15);
		game.move(PieceType.MARSHAL, L45, L44);//RED
		game.move(PieceType.COLONEL, L15, L25);
		game.move(PieceType.MARSHAL, L44, L45);//RED
		game.move(PieceType.COLONEL, L25, L35);
		game.move(PieceType.MARSHAL, L45, L35);//RED
		game.move(PieceType.MARSHAL, L55, L45);//Blue
		assertSame(game.move(PieceType.MARSHAL, L35, L45).getStatus(), MoveResultStatus.RED_WINS);//RED
		game.move(PieceType.CAPTAIN, L20, L21);
	}

	@Test
	public void fullGame_DRAW() throws StrategyException {
		game.move(PieceType.MARSHAL, L11, L12);      //RED
		game.move(PieceType.LIEUTENANT, L14, L13);
		game.move(PieceType.LIEUTENANT, L41, L42);			//RED
		game.move(PieceType.COLONEL, L24, L14); 
		game.move(PieceType.SERGEANT, L51, L52);			//RED
		game.move(PieceType.LIEUTENANT, L13, L12);
		game.move(PieceType.MARSHAL, L13, L03); //RED
		game.move(PieceType.COLONEL, L14, L13);
		game.move(PieceType.COLONEL, L21, L11); //RED
		game.move(PieceType.COLONEL, L13, L12); 
		game.move(PieceType.COLONEL, L11, L12);//RED - its a tie battle
		game.move(PieceType.LIEUTENANT, L15, L14);
		game.move(PieceType.MARSHAL, L03, L04);//RED
		game.move(PieceType.SERGEANT, L25, L24);
		game.move(PieceType.MARSHAL, L04, L14);//RED
		game.move(PieceType.SERGEANT, L24, L14);//////////////
		game.move(PieceType.SERGEANT, L52, L53);//RED
		game.move(PieceType.SERGEANT, L54, L53);
		game.move(PieceType.MARSHAL, L24, L25);//RED
		game.move(PieceType.LIEUTENANT, L35, L25);
		game.move(PieceType.MARSHAL, L35, L45);//RED
		game.move(PieceType.MARSHAL, L55, L54);
		game.move(PieceType.MARSHAL, L45, L44);//RED
		game.move(PieceType.MARSHAL, L54, L53);
		game.move(PieceType.MARSHAL, L44, L45);//RED
		game.move(PieceType.MARSHAL, L53, L52);
		game.move(PieceType.MARSHAL, L45, L35);//RED
		game.move(PieceType.MARSHAL, L52, L51);//Blue
		game.move(PieceType.MARSHAL, L35, L25);//RED
		game.move(PieceType.MARSHAL, L51, L50);//Blue
		game.move(PieceType.MARSHAL, L25, L15);//RED
		game.move(PieceType.MARSHAL, L50, L40);//Blue
		game.move(PieceType.MARSHAL, L15, L05);//RED
		game.move(PieceType.MARSHAL, L40, L30);//Blue
		game.move(PieceType.LIEUTENANT, L42, L41);			//RED
		game.move(PieceType.MARSHAL, L30, L20); // BLUE
		game.move(PieceType.MARSHAL, L05, L15);//RED
		game.move(PieceType.MARSHAL, L20, L10);//Blue
		game.move(PieceType.MARSHAL, L15, L14);//RED
		game.move(PieceType.MARSHAL, L10, L00);//Blue
		game.move(PieceType.CAPTAIN, L31, L21);//red
		game.move(PieceType.MARSHAL, L00, L10);//Blue
		game.move(PieceType.MARSHAL, L14, L13);//RED
		game.move(PieceType.MARSHAL, L10, L20);//Blue
		game.move(PieceType.MARSHAL, L13, L12);//RED
		game.move(PieceType.MARSHAL, L20, L21);//Blue
		game.move(PieceType.MARSHAL, L12, L11);//RED
		game.move(PieceType.MARSHAL, L21, L31);//Blue
		game.move(PieceType.MARSHAL, L11, L21); //red
		game.move(PieceType.MARSHAL, L31, L41);//Blue
		game.move(PieceType.MARSHAL, L21, L31); //red
		assertSame(game.move(PieceType.MARSHAL, L41, L31).getStatus(), MoveResultStatus.DRAW);//BLUE
	}
	
	@Test
	public void fullGame_blueWins_redCantMove() throws StrategyException {
		game.move(PieceType.MARSHAL, L11, L12);      //RED
		game.move(PieceType.LIEUTENANT, L14, L13);
		game.move(PieceType.LIEUTENANT, L41, L42);			//RED
		game.move(PieceType.COLONEL, L24, L14); 
		game.move(PieceType.SERGEANT, L51, L52);			//RED
		game.move(PieceType.LIEUTENANT, L13, L12);
		game.move(PieceType.MARSHAL, L13, L03); //RED
		game.move(PieceType.COLONEL, L14, L13);
		game.move(PieceType.COLONEL, L21, L11); //RED
		game.move(PieceType.COLONEL, L13, L12); 
		game.move(PieceType.COLONEL, L11, L12);//RED - its a tie battle
		game.move(PieceType.LIEUTENANT, L15, L14);
		game.move(PieceType.MARSHAL, L03, L04);//RED
		game.move(PieceType.SERGEANT, L25, L24);
		game.move(PieceType.MARSHAL, L04, L14);//RED
		game.move(PieceType.SERGEANT, L24, L14);//////////////
		game.move(PieceType.SERGEANT, L52, L53);//RED
		game.move(PieceType.SERGEANT, L54, L53);
		game.move(PieceType.MARSHAL, L24, L25);//RED
		game.move(PieceType.LIEUTENANT, L35, L25);
		game.move(PieceType.MARSHAL, L35, L45);//RED
		game.move(PieceType.MARSHAL, L55, L54);
		game.move(PieceType.MARSHAL, L45, L44);//RED
		game.move(PieceType.MARSHAL, L54, L53);
		game.move(PieceType.MARSHAL, L44, L45);//RED
		game.move(PieceType.MARSHAL, L53, L52);
		game.move(PieceType.MARSHAL, L45, L35);//RED
		game.move(PieceType.MARSHAL, L52, L51);//Blue
		game.move(PieceType.MARSHAL, L35, L25);//RED
		game.move(PieceType.MARSHAL, L51, L50);//Blue
		game.move(PieceType.MARSHAL, L25, L15);//RED
		game.move(PieceType.MARSHAL, L50, L40);//Blue
		game.move(PieceType.CAPTAIN, L20 , L21); //Red
		game.move(PieceType.MARSHAL, L40, L30);//Blue
		game.move(PieceType.LIEUTENANT, L42, L41);			//RED
		game.move(PieceType.MARSHAL, L30, L20); // BLUE
		game.move(PieceType.LIEUTENANT, L41, L42);			//RED
		game.move(PieceType.MARSHAL, L20, L10);//Blue
		game.move(PieceType.MARSHAL, L15, L14);//RED
		game.move(PieceType.MARSHAL, L10, L00);//Blue
		game.move(PieceType.LIEUTENANT, L42, L41);//red
		game.move(PieceType.MARSHAL, L00, L10);//Blue
		game.move(PieceType.MARSHAL, L14, L13);//RED
		game.move(PieceType.MARSHAL, L10, L20);//Blue
		game.move(PieceType.MARSHAL, L13, L12);//RED
		game.move(PieceType.MARSHAL, L20, L21);//Blue
		game.move(PieceType.MARSHAL, L12, L11);//RED
		game.move(PieceType.MARSHAL, L21, L31);//Blue
		game.move(PieceType.MARSHAL, L11, L21); //red
		game.move(PieceType.MARSHAL, L31, L41);//Blue
		game.move(PieceType.MARSHAL, L21, L31); //red
		assertSame(game.move(PieceType.MARSHAL, L41, L31).getStatus(), MoveResultStatus.BLUE_WINS);//BLUE
	}
}
