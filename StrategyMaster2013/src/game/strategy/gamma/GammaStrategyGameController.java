/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.strategy.gamma;

import game.AbstractStrategyGameController;
import game.VersionRules;
import game.common.PieceLocationDescriptor;

import java.util.Collection;

import common.StrategyException;

/** The Gamma game engine for the Gamma version of Strategy. This class handles
 *  validating piece configurations, validating and recording moves and also
 *  locating pieces.
 * 
 * @author Dabrowski
 * @version $Revision: 6.9 $
 */
public class GammaStrategyGameController extends AbstractStrategyGameController  {

	/** Constructor for GammaStrategyGameController. Takes two configurations, checks their 
	 *  validity (throwing an exception if either is invalid) and stores them internally,
	 *  as well as setting up a couple other basic variables involved with a game.
	 * 
	 * @param redConfiguration Configuration for the red side
	 * @param blueConfiguration  Configuration for the blue side
	 * @param gammaRules a validator for checking the player configurations
	 * @throws StrategyException Thrown if an invalid configuration is detected */
	public GammaStrategyGameController(
			Collection<PieceLocationDescriptor>  redConfiguration,
			Collection<PieceLocationDescriptor>  blueConfiguration,
			VersionRules gammaRules)throws StrategyException {
		super(redConfiguration, blueConfiguration, gammaRules);		
	}
	
}
