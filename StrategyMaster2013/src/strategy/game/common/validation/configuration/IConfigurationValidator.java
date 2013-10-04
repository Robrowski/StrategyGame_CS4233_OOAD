/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common.validation.configuration;

import java.util.Collection;

import strategy.common.StrategyException;
import strategy.game.common.PieceLocationDescriptor;

/** This interface should be implemented by any class that is used to
 *  validate player configurations. 
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IConfigurationValidator {
	
	/** Takes player configurations and ensures that they are valid, both in 
	 *  terms of piece allocations and piece locations. 
	 * 
	 * @param redConfiguration the red player's configuration
	 * @param blueConfiguration the blue player's configuration
	
	 * @throws StrategyException thrown when invalidities are found */
	void validatePlayerConfigurations(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException ;
	
}
