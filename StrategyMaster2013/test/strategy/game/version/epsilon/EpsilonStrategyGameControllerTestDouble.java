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
import java.util.HashMap;

import strategy.common.StrategyException;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.StrategyGameObserver;
import strategy.game.common.board.IBoardManager;
import strategy.game.version.VersionRules;
import strategy.game.version.delta.DeltaBoardManager;
import strategy.game.version.delta.DeltaPieceMoves;

/** Same exact functionality as a EpsilonStrategyGameController + the
 *  internal configuration can be set after initialization
 * 
 * 
 * @author Dabrowski
 * @version $Revision: 6.9 $
 */
public class EpsilonStrategyGameControllerTestDouble extends
		EpsilonStrategyGameController {

	/** Constructor for EpsilonStrategyGameControllerTestDouble. Takes two configurations, checks their 
	 *  validity (throwing an exception if either is invalid) and stores them internally,
	 *  as well as setting up a couple other basic variables involved with a game.
	 * 
	 * @param redConfiguration Configuration for the red side
	 * @param blueConfiguration  Configuration for the blue side
	 * @param EpsilonRules a validator for checking the player configurations
	 * @param observers the observers
	 * @throws StrategyException Thrown if an invalid configuration is detected */
	public EpsilonStrategyGameControllerTestDouble(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration,
			VersionRules EpsilonRules,
			Collection<StrategyGameObserver> observers) throws StrategyException {
		super(redConfiguration, blueConfiguration, EpsilonRules, observers);
	}
	
	/** Sets the current field configuration to the given config in
	 *  the form of a board manager
	 * 
	 * @param toSetTo the board manager to set to 
	 */
	public void setFieldConfiguration(IBoardManager toSetTo){
		fieldConfiguration = toSetTo;
	}
	
	/** Sets the current field configuration to the given config in
	 *  the form of a board manager
	 * 
	 * @param toSetTo collection of pieces to put in the board manager
	 */
	public void setFieldConfiguration(Collection<PieceLocationDescriptor> toSetTo){
		// Make a new manager
		IBoardManager newManager = new DeltaBoardManager(new HashMap<String,PieceLocationDescriptor>(), new DeltaPieceMoves());
		// Add the collection to the manager
		newManager.addToConfiguration(toSetTo);
		// Sets the field configuration		
		setFieldConfiguration(newManager);
	}
	
	

}
