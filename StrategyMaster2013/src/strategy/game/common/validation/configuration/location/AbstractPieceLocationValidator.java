/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common.validation.configuration.location;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import strategy.common.PlayerColor;
import strategy.game.common.Location;
import strategy.game.common.PieceLocationDescriptor;

/** Classes wishing to perform with common behavior and
 *  validate the locations of pieces in a player 
 *  configuration should extend this class. They should 
 *  also implement the IPieceLocationValidator interface.
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public abstract class AbstractPieceLocationValidator implements IPieceLocationValidator {
	
	/** The number of players in the game */
	protected int numberOfPlayers;
	/** The number of units the field goes on the Y axis */
	protected int fieldYDim;
	/** The number of units the field goes on the X axis */
	protected int fieldXDim;
	
	/** General constructor to take the number of players, as well as the X and Y 
	 *  dimensions of the field. This constructor is here because the existence
	 *  of these parameters is not a variable point, and thus should be extended
	 *  for common functionality. 
	 * 
	 * @param numberOfPlayers The number of players in the game
	 * @param fieldXDim The number of units the field goes on the X axis
	 * @param fieldYDim The number of units the field goes on the Y axis 
	 */
	protected AbstractPieceLocationValidator(int numberOfPlayers, int fieldXDim, int fieldYDim){
		this.numberOfPlayers = numberOfPlayers;
		this.fieldYDim = fieldYDim;
		this.fieldXDim = fieldXDim;
	}
	

	/* (non-Javadoc)
	 * @see strategy.game.version.common.validation.configuration.IPieceLocationValidator#checkPieceLocations(java.util.Collection,java.util.Collection)
	 */
	public String checkPieceLocations(
			Collection<PieceLocationDescriptor> aConfiguration,
			PlayerColor givenPlayer) {
		// Standard method of message passing in the Dabrowski implementation of Stategy
		String invalidities = "";
		
		// Check that the given PlayerColor is one that was expected
		invalidities += this.isPlayerColorExpected(givenPlayer); // Does not need to be implemented yet
		
		// Get the valid locations that pieces may be placed at
		final Collection<Location> validLocations = generateValidStartingLocations( givenPlayer );		

		// Check that all the locations are used
		if (validLocations.size() != aConfiguration.size()){
			invalidities += " there aren't enough pieces placed";
		}
		
		// THIS is not a variability point (I hope)
		final Iterator<PieceLocationDescriptor> pieces = aConfiguration.iterator();
		final Collection<Location> tempLocationChecker = new LinkedList<Location>();	
		while (pieces.hasNext()){
			// The next piece + location to check
			PieceLocationDescriptor next = pieces.next();
			
			// Make sure that no piece is located out side the validLocations collection
			Location nextLocation = next.getLocation();
			if (!validLocations.contains(nextLocation)) invalidities += " a piece is in an invalid location, ";
		
			// Make sure that the givenPlayer is the owner of every piece in the configuration
			PlayerColor owner = next.getPiece().getOwner();
			if (!owner.equals(givenPlayer)) invalidities += " the current player does not own this piece, ";
		
			// Validating that locations do not contain two pieces
			if (tempLocationChecker.contains(nextLocation)) invalidities+= "a location was used twice, "; 
			 // Else add this location to the list of locations used
			else tempLocationChecker.add(nextLocation);			
		}	
		
		return invalidities;
	}
	
	/** Checks if the givenPlayer is valid for the current version of Strategy
	 * 
	 * @param givenPlayer the color of the player to verify
	
	 * @return an error message if the color was not recognized, or an empty string */
	protected abstract String isPlayerColorExpected(PlayerColor givenPlayer);


	/** Generate a collection of locations for all valid starting locations for
	 *  the given player number. 
	 * 
	 * @param givenPlayer the given player to generate starting locations for
	
	 * @return a collection of viable starting locations */
	protected abstract Collection<Location> generateValidStartingLocations(PlayerColor givenPlayer);
	
	
	
}
