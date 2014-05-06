/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package game;

import game.common.PieceLocationDescriptor;
import game.common.StrategyGameObserver;
import game.version.alpha.AlphaStrategyGameController;
import game.version.beta.BetaStrategyGameController;
import game.version.delta.DeltaRules;
import game.version.delta.DeltaStrategyGameController;
import game.version.epsilon.EpsilonRules;
import game.version.epsilon.EpsilonStrategyGameController;
import game.version.gamma.GammaRules;
import game.version.gamma.GammaStrategyGameController;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import common.StrategyException;

/**
 * <p>
 * Factory to produce various versions of the Strategy game and others soon. This is implemented
 * as a singleton.
 * </p><p>
 * NOTE: If an error occurs creating any game, that is not specified in the particular
 * factory method's documentation, the factory method should throw a 
 * StrategyRuntimeException.
 * </p>
 * 
 * @author gpollice
 * @version Sep 10, 2013
 */
public class GameFactory
{
	/** An instance of the factory */
	private final static GameFactory instance = new GameFactory();

	/**
	 * Default private constructor to ensure this is a singleton.
	 */
	private GameFactory()
	{
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static GameFactory getInstance()
	{
		return instance;
	}

	/**
	 * Create an Alpha Strategy game.
	 * @return the created Alpha Strategy game
	 */
	public GameController makeAlphaStrategyGame()
	{
		return new AlphaStrategyGameController();
	}

	/**
	 * Create a new Beta Strategy game given the 
	 * @param redConfiguration the initial starting configuration for the RED pieces
	 * @param blueConfiguration the initial starting configuration for the BLUE pieces
	 * @return the Beta Strategy game instance with the initial configuration of pieces
	 * @throws StrategyException if either configuration is incorrect
	 */
	public GameController makeBetaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
					throws StrategyException
					{
		checkConfigurationForNull(redConfiguration);
		checkConfigurationForNull(blueConfiguration);

		return new BetaStrategyGameController(redConfiguration,blueConfiguration);
					}

	/**
	 * Create a new Gamma Strategy game given the 
	 * @param redConfiguration the initial starting configuration for the RED pieces
	 * @param blueConfiguration the initial starting configuration for the BLUE pieces
	 * @return the Gamma Strategy game instance with the initial configuration of pieces
	 * @throws StrategyException if either configuration is incorrect
	 */
	public GameController makeGammaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
					throws StrategyException
					{
		checkConfigurationForNull(redConfiguration);
		checkConfigurationForNull(blueConfiguration);

		return new GammaStrategyGameController(redConfiguration,blueConfiguration, new GammaRules());
					}

	/**
	 * Create a new Delta Strategy game given the 
	 * @param redConfiguration the initial starting configuration for the RED pieces
	 * @param blueConfiguration the initial starting configuration for the BLUE pieces
	 * @return the Delta Strategy game instance with the initial configuration of pieces
	 * @throws StrategyException if either configuration is incorrect
	 */
	public GameController makeDeltaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException	{
		checkConfigurationForNull(redConfiguration);
		checkConfigurationForNull(blueConfiguration);

		return new DeltaStrategyGameController(redConfiguration,blueConfiguration, new DeltaRules());
	}

	/** Create a new Epsilon strategy game given the following
	 * @param redConfiguration  a configuration for the red side
	 * @param blueConfiguration a configuration for the blue side
	 * @param observers Observers to watch the game
	 * @return A valid game of Epsilon Strategy
	 * @throws StrategyException a configuration was invalid
	 */
	public GameController makeEpsilonStrategyGame(
			List<PieceLocationDescriptor> redConfiguration,
			List<PieceLocationDescriptor> blueConfiguration,
			Collection<StrategyGameObserver> observers) throws StrategyException {

		checkConfigurationForNull(redConfiguration);
		checkConfigurationForNull(blueConfiguration);


		return new EpsilonStrategyGameController(redConfiguration,blueConfiguration, new EpsilonRules(), observers);
	}


	/** Checks the a configuration for nulls and throws an exception if any are thrown.
	 *  @param aConfiguration a configuration of pieces to check
	 *  @throws StrategyException if any nulls are encountered.
	 */
	private void checkConfigurationForNull(Collection<PieceLocationDescriptor> aConfig) throws StrategyException{
		if (aConfig == null){
			throw new StrategyException("This whole configuration is null!");
		}

		final Iterator<PieceLocationDescriptor> items = aConfig.iterator();
		while(items.hasNext()){
			PieceLocationDescriptor next = items.next();
			if (next == null 
					|| next.getPiece() == null
					|| next.getPiece().getType() == null
					|| next.getPiece().getOwner() == null
					|| next.getLocation() == null) throw new StrategyException("Null data is not appreciated.");
		}
	}




}