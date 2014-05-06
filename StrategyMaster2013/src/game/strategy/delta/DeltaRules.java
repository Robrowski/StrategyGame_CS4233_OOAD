/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.strategy.delta;

import game.VersionRules;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.battle.IBattleEngine;
import game.common.battle.StandardBattleEngine;
import game.common.board.IBoardManager;
import game.common.board.MapBoardManager;
import game.common.history.IMoveHistory;
import game.common.history.StandardMoveHistory;
import game.common.validation.configuration.ConfigurationValidator2D;
import game.common.validation.configuration.IConfigurationValidator;
import game.common.validation.configuration.location.GeneralPieceLocationValidator;
import game.common.validation.configuration.location.IPieceLocationValidator;
import game.common.validation.configuration.pieces.IPieceAllowanceValidator;
import game.common.validation.configuration.pieces.StandardPieceAllowanceValidator;
import game.common.validation.move.IMoveDistanceValidator;
import game.common.validation.move.IMoveSpecialCaseValidator;
import game.common.validation.move.StandardMoveDistanceValidator;
import game.common.validation.move.StandardMoveSpecialCaseValidator;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import common.PlayerColor;

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
	private final int xBoardDim = 10;
	/** The size of the field */
	private final int yBoardDim = 10;

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
	 * @see game.strategy.VersionRules#getPieceAllowanceValidator()
	 */
	@Override
	public IPieceAllowanceValidator getPieceAllowanceValidator() {
		final Map<PieceType,Integer> expectedPieceCounts = new HashMap<PieceType,Integer>();
		
		// Add all the pieces and their counts
		expectedPieceCounts.put(PieceType.FLAG 			, NUM_FLAGS_PER_TEAM);
		expectedPieceCounts.put(PieceType.MARSHAL		, NUM_MARSHALS_PER_TEAM);
		expectedPieceCounts.put(PieceType.COLONEL 		, NUM_COLONELS_PER_TEAM);
		expectedPieceCounts.put(PieceType.CAPTAIN 		, NUM_CAPTAINS_PER_TEAM);
		expectedPieceCounts.put(PieceType.LIEUTENANT 	, NUM_LIEUTENANTS_PER_TEAM);
		expectedPieceCounts.put(PieceType.SERGEANT 		, NUM_SERGEANTS_PER_TEAM);
		expectedPieceCounts.put(PieceType.BOMB 			, NUM_BOMBS_PER_TEAM);
		expectedPieceCounts.put(PieceType.MAJOR			, NUM_MAJORS_PER_TEAM);
		expectedPieceCounts.put(PieceType.GENERAL 		, NUM_GENERALS_PER_TEAM);
		expectedPieceCounts.put(PieceType.MINER			, NUM_MINERS_PER_TEAM);
		expectedPieceCounts.put(PieceType.SPY 			, NUM_SPIES_PER_TEAM);
		expectedPieceCounts.put(PieceType.SCOUT 		, NUM_SCOUTS_PER_TEAM);

		return new StandardPieceAllowanceValidator(expectedPieceCounts);
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getPieceLocationValidator()
	 */
	@Override
	public IPieceLocationValidator getPieceLocationValidator() {
		return new GeneralPieceLocationValidator(numberOfPlayers, xBoardDim, yBoardDim, 0, 6);
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getConfigurationValidator()
	 */
	@Override
	public IConfigurationValidator getConfigurationValidator() {
		return new ConfigurationValidator2D(this);	
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getPlayer2()
	 */
	@Override
	public PlayerColor getPlayer2() {
		return player2;
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getPlayer1()
	 */
	@Override
	public PlayerColor getPlayer1() {
		return player1;
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#additionalFieldItems()
	 */
	@Override
	public Collection<PieceLocationDescriptor> additionalFieldItems() {
		final Collection<PieceLocationDescriptor> chokePoints = new LinkedList<PieceLocationDescriptor>();
		
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(2,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(2,5)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(3,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(3,5)));
		
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(6,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(6,5)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(7,4)));
		chokePoints.add(new PieceLocationDescriptor(chokePoint, new Location2D(7,5)));
		
		return chokePoints;
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getMoveValidator()
	 */
	@Override
	public IMoveSpecialCaseValidator getMoveSpecialCaseValidator() {
		return new StandardMoveSpecialCaseValidator();
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getMoveDistanceValidator()
	 */
	@Override
	public IMoveDistanceValidator getMoveDistanceValidator() {
		return new StandardMoveDistanceValidator(xBoardDim, yBoardDim,    new DeltaPieceMoves());
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getMoveHistory()
	 */
	@Override
	public IMoveHistory getMoveHistory() {
		return new StandardMoveHistory();
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getBattleEngine()
	 */
	@Override
	public IBattleEngine getBattleEngine() {
		return new StandardBattleEngine(new DeltaPiecePowers(), new DeltaSpecialBattles());
	}

	/* (non-Javadoc)
	 * @see game.strategy.VersionRules#getBoard()
	 */
	@Override
	public IBoardManager getBoard() {
		return new MapBoardManager(new HashMap<String, PieceLocationDescriptor>(), new DeltaPieceMoves());
	}
}
