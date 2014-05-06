/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package message;

/** These are the types of messages that may be
 *  sent over the network.
 * 
 * @version $Revision: 1.0 $
 * @author Dabrowski
 */
public enum StrategyMessageType  { 
	UNKNOWN, // for those times that you just don't know
	MOVE,  				// For sending move details
	QUERY,  			// For requesting information, like "getPieceAt"
	RESULT, 			// For the result of a move
	DEFAULT_INITIALIZE,	// To initialize a game - the game version should be included
	START; 				// To start a game 

}
