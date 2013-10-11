/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.epsilon;

import java.util.Collection;

import strategy.common.StrategyException;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.StrategyGameObserver;
import strategy.game.version.AbstractStrategyGameController;
import strategy.game.version.VersionRules;

/** The Delta game engine for the Epsilon version of Strategy. This class handles
 *  validating piece configurations, validating and recording moves and also
 *  locating pieces.
 * 
 * @author Dabrowski
 * @version $Revision: 6.9 $
 */
public class EpsilonStrategyGameController extends
		AbstractStrategyGameController {

	/** Constructor for AbstractStrategyGameController. Takes two configurations, checks their 
	 *  validity (throwing an exception if either is invalid) and stores them internally,
	 *  as well as setting up a couple other basic variables involved with a game.
	 * @param redConfiguration Configuration for the red side
	 * @param blueConfiguration  Configuration for the blue side
	 * @param theRules a validator for checking the player configurations
	 * @param observers observers that will watch this controller
	 * @throws StrategyException Thrown if an invalid configuration is detected */
	public EpsilonStrategyGameController(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration,
			VersionRules theRules, 
			Collection<StrategyGameObserver> observers) throws StrategyException {
		super(redConfiguration, blueConfiguration, theRules);

	}

}
