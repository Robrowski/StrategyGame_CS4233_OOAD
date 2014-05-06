/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.validation.configuration.location;

import game.common.PieceLocationDescriptor;

import java.util.Collection;

import common.PlayerColor;

/** This interface should be implemented by any class that is used
 *  to validate the locations of pieces placed in a players piece
 *  configuration.
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public interface IPieceLocationValidator {

	/** Check the configurations given for valid placement on the 
	 *  playing field. 
	 *  
	 *  The standard usage for player color is to identify  what
	 *  section of the board the player is located on. Different
	 *  variations of Strategy will expect different player colors,
	 *  so unrecognized PlayerColor's will cause error messages to
	 *  be returned by this method.  
	 * 
	 * @param aConfiguration the red configuration 
	 * @param playerColor the index of the player configuration being verified
	
	 * @return a string containing any error messages that may apply */
	String checkPieceLocations(
			Collection<PieceLocationDescriptor> aConfiguration,
			PlayerColor playerColor);
}
