/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common.validation.configuration.pieces;

import java.util.Collection;

import strategy.game.common.PieceLocationDescriptor;

/** This interface should be implemented by any class entrusted with
 *  the duty of counting the pieces in a players piece configuration.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IPieceAllowanceValidator {

	/** Validate a player's configuration by counting the number of pieces and 
	 *  making sure each piece occurs an acceptable number of times.S 
	 * 
	 *  This method should not be overridden.
	 *  
	 * @param aConfiguration The configuration to count the pieces in.
	
	 * @return a string containing the invalidities found in the configurationS */
	String validatePieceCounts(Collection<PieceLocationDescriptor> aConfiguration);
	
	
	
}