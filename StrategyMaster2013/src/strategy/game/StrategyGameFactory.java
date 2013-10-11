/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import strategy.common.StrategyException;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.StrategyGameObserver;
import strategy.game.version.alpha.AlphaStrategyGameController;
import strategy.game.version.beta.BetaStrategyGameController;
import strategy.game.version.delta.DeltaRules;
import strategy.game.version.delta.DeltaStrategyGameController;
import strategy.game.version.epsilon.EpsilonStrategyGameController;
import strategy.game.version.gamma.GammaRules;
import strategy.game.version.gamma.GammaStrategyGameController;

/**
 * <p>
 * Factory to produce various versions of the Strategy game. This is implemented
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
public class StrategyGameFactory
{
	/** An instance of the factory */
	private final static StrategyGameFactory instance = new StrategyGameFactory();

	/**
	 * Default private constructor to ensure this is a singleton.
	 */
	private StrategyGameFactory()
	{
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static StrategyGameFactory getInstance()
	{
		return instance;
	}

	/**
	 * Create an Alpha Strategy game.
	 * @return the created Alpha Strategy game
	 */
	public StrategyGameController makeAlphaStrategyGame()
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
	public StrategyGameController makeBetaStrategyGame(
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
	public StrategyGameController makeGammaStrategyGame(
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
	public StrategyGameController makeDeltaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException	{
		checkConfigurationForNull(redConfiguration);
		checkConfigurationForNull(blueConfiguration);

		return new DeltaStrategyGameController(redConfiguration,blueConfiguration, new DeltaRules());
	}

	/**
	 * @param validRedConfiguration
	 * @param validBlueConfiguration
	 * @return
	 * @throws StrategyException 
	 */
	public StrategyGameController makeEpsilonStrategyGame(
			List<PieceLocationDescriptor> redConfiguration,
			List<PieceLocationDescriptor> blueConfiguration,
			Collection<StrategyGameObserver> observers) throws StrategyException {

		checkConfigurationForNull(redConfiguration);
		checkConfigurationForNull(blueConfiguration);


		return new EpsilonStrategyGameController(redConfiguration,blueConfiguration, new DeltaRules(), observers);
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