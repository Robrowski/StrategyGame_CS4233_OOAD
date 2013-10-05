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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import strategy.common.PlayerColor;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.battle.IBattleEngine;
import strategy.game.common.battle.StandardBattleEngine;
import strategy.game.common.board.IBoardManager;
import strategy.game.common.board.MapBoardManager;
import strategy.game.common.history.StandardMoveHistory;
import strategy.game.common.history.IMoveHistory;
import strategy.game.common.validation.configuration.ConfigurationValidator2D;
import strategy.game.common.validation.configuration.IConfigurationValidator;
import strategy.game.common.validation.configuration.location.GeneralPieceLocationValidator;
import strategy.game.common.validation.configuration.location.IPieceLocationValidator;
import strategy.game.common.validation.configuration.pieces.IPieceAllowanceValidator;
import strategy.game.common.validation.configuration.pieces.StandardPieceAllowanceValidator;
import strategy.game.common.validation.move.IMoveDistanceValidator;
import strategy.game.common.validation.move.IMoveSpecialCaseValidator;
import strategy.game.common.validation.move.StandardMoveDistanceValidator;
import strategy.game.version.VersionRules;
import strategy.game.version.gamma.GammaMoveSpecialCaseValidator;

/** DeltaRules holds all non-standard game rules that make
 *  Delta Strategy different from the rest. The methods
 *  below only provide tools to the DeltaStrategyGameController
 *  to use in validation steps.  
 *  
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class DeltaRules implements VersionRules {
	/** The number of players */
	private final int numberOfPlayers = 2;
	/** The size of the field */
	private final int xBoardDim = 6;
	/** The size of the field */
	private final int yBoardDim = 6;

	/** The color of Player 1 */
	private final PlayerColor player1 = PlayerColor.RED;
	/** The color of Player 2 */
	private final PlayerColor player2 = PlayerColor.BLUE;

	
	/** The number of flags per team at initialization */
	private static final int NUM_FLAGS_PER_TEAM = 1;
	/** The number of marshals per team at initialization */
	private static final int NUM_MARSHALS_PER_TEAM = 1;
	/** The number of colonels per team at initialization */
	private static final int NUM_COLONELS_PER_TEAM = 2;
	/** The number of captains per team at initialization */
	private static final int NUM_CAPTAINS_PER_TEAM = 4;
	/** The number of lieutenants per team at initialization */
	private static final int NUM_LIEUTENANTS_PER_TEAM = 4;
	/** The number of sergeants per team at initialization */
	private static final int NUM_SERGEANTS_PER_TEAM = 4;
	/** The number of bombs per team at initialization */
	private static final int NUM_BOMBS_PER_TEAM = 6;
	/** The number of spies per team at initialization */
	private static final int NUM_SPIES_PER_TEAM = 1;
	/** The number of scouts per team at initialization */
	private static final int NUM_SCOUTS_PER_TEAM = 8;
	/** The number of miners per team at initialization */
	private static final int NUM_MINERS_PER_TEAM = 5;
	/** The number of generals per team at initialization */
	private static final int NUM_GENERALS_PER_TEAM = 1;
	/** The number of majors per team at initialization */
	private static final int NUM_MAJORS_PER_TEAM = 3;

	/** A choke point object */
	private static final Piece chokePoint = new Piece(PieceType.CHOKE_POINT, null);
	
	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getPieceAllowanceValidator()
	 */
	@Override
	public IPieceAllowanceValidator getPieceAllowanceValidator() {
		final Map<PieceType,Integer> expectedPieceCounts = new HashMap<PieceType,Integer>();
		
		// Add all the pieces and their counts
		expectedPieceCounts.put(PieceType.FLAG 			, NUM_FLAGS_PER_TEAM);
		expectedPieceCounts.put(PieceType.MARSHAL		, NUM_MARSHALS_PER_TEAM);
		expectedPieceCounts.put(PieceType.COLONEL 		, 2);
		expectedPieceCounts.put(PieceType.CAPTAIN 		, 2);
		expectedPieceCounts.put(PieceType.LIEUTENANT 	, 3);
		expectedPieceCounts.put(PieceType.SERGEANT 		, 3);
//		expectedPieceCounts.put(PieceType.BOMB 			, NUM_BOMBS_PER_TEAM);
//		expectedPieceCounts.put(PieceType.MAJOR			, NUM_MAJORS_PER_TEAM);
//		expectedPieceCounts.put(PieceType.GENERAL 		, NUM_GENERALS_PER_TEAM);
//		expectedPieceCounts.put(PieceType.MINER			, NUM_MINERS_PER_TEAM);
//		expectedPieceCounts.put(PieceType.SPY 			, NUM_SPIES_PER_TEAM);
//		expectedPieceCounts.put(PieceType.SCOUT 		, NUM_SCOUTS_PER_TEAM);

		return new StandardPieceAllowanceValidator(expectedPieceCounts);
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getPieceLocationValidator()
	 */
	@Override
	public IPieceLocationValidator getPieceLocationValidator() {
		return new GeneralPieceLocationValidator(numberOfPlayers, xBoardDim, yBoardDim, 0, 4);
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getConfigurationValidator()
	 */
	@Override
	public IConfigurationValidator getConfigurationValidator() {
		return new ConfigurationValidator2D(this);	
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getPlayer2()
	 */
	@Override
	public PlayerColor getPlayer2() {
		return player2;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getPlayer1()
	 */
	@Override
	public PlayerColor getPlayer1() {
		return player1;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#additionalFieldItems()
	 */
	@Override
	public Collection<PieceLocationDescriptor> additionalFieldItems() {
		final Collection<PieceLocationDescriptor> chokePoints = new LinkedList<PieceLocationDescriptor>();
		
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(2,2)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(2,3)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(3,2)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(3,3)));
		
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(6,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(6,5)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(7,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(7,5)));
		
		return chokePoints;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getMoveValidator()
	 */
	@Override
	public IMoveSpecialCaseValidator getMoveSpecialCaseValidator() {
		return new DeltaMoveSpecialCaseValidator();
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getMoveDistanceValidator()
	 */
	@Override
	public IMoveDistanceValidator getMoveDistanceValidator() {
		return new StandardMoveDistanceValidator(xBoardDim, yBoardDim,    new DeltaPieceMoves());
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getMoveHistory()
	 */
	@Override
	public IMoveHistory getMoveHistory() {
		return new StandardMoveHistory();
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getBattleEngine()
	 */
	@Override
	public IBattleEngine getBattleEngine() {
		return new StandardBattleEngine(new DeltaPiecePowers(), new DeltaSpecialBattles());
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.VersionRules#getBoard()
	 */
	@Override
	public IBoardManager getBoard() {
		return new DeltaBoardManager(new HashMap<String, PieceLocationDescriptor>(), new DeltaPieceMoves());
	}
}
