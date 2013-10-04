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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/** Handles counting the number of pieces a player has in 
 *  his configuration and ensures that the totals are valid
 *  specifically for any version of Strategy.
 * 
 * This class is labeled as "Static" and should work for any version of 
 * Strategy that only allows a fixed number of pieces of each type. The
 * first version this is used in is GammaStrategy...
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class StandardPieceAllowanceValidator implements IPieceAllowanceValidator {

	/** A map containing the expected pieces and the number of times each should appear */
	private final Map<PieceType, Integer> expectedPieceCounts;

	/** Basic constructor that takes a Map of the expected piece types as keys with 
	 * the values being the expected number of times that each should appear
	 * 
	 * @param expectedPieceCounts A map containing the expected pieces and the number of times each should appear
	 */
	public StandardPieceAllowanceValidator(Map<PieceType,Integer> expectedPieceCounts){
		this.expectedPieceCounts = expectedPieceCounts;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.common.validation.configuration.pieces.IPieceAllowanceValidator#validatePieceCounts(java.util.Collection)
	 */
	@Override
	public String validatePieceCounts(
			Collection<PieceLocationDescriptor> config) {
		// Standard invalidities variable to hold error messages
		String invalidities = "";

		// This map will be used for keeping track of how many of each piece is present
		final Map<PieceType,Integer> pieceCounts = countPieces(config);

		// Verify that the counts for each piece are valid
		invalidities += verifyCounts(pieceCounts);

		// Return the accumulated error messages
		return invalidities;
	}

	/** Verify that the counts for each method are valid and return a string
	 *  containing any error messages that may arise
	 * 
	 * @param pieceCounts a map containing the piece types as keys and the number of times they appear as values
	
	 * @return a string containing any error messagse that may apply */
	protected String verifyCounts(Map<PieceType, Integer> pieceCounts) {
		// Standard invalidities variable to hold error messages
		String invalidities = "";
		
		// Iterate through the counts and verify them against the expectedPieceCounts
		final Iterator<Entry<PieceType, Integer>> pieceCountIterator = pieceCounts.entrySet().iterator();
		while (pieceCountIterator.hasNext()){
			// The next entry
			Entry<PieceType, Integer> next = pieceCountIterator.next();
			// The piece type and number of times it occured
			PieceType typeToCheck = next.getKey();
			Integer    theCount = next.getValue();

			// Check to see if the type is even expected
			if (expectedPieceCounts.get(typeToCheck) == null){
				invalidities += typeToCheck.getSymbol() +" should not be included in this version, ";
			}
			// Now check the actual count
			else if (expectedPieceCounts.get(typeToCheck) != theCount ) {
				invalidities+= "a player has too many "+ typeToCheck.getPrintableName() +"s, ";	
			}
		}
		
		// A catch all/last resort to catch the fact that pieces might not be represented
		if (expectedPieceCounts.size() != pieceCounts.size()) invalidities += "invalid total number of pieces";
		
		return invalidities;
	}

	
	/** Takes a configuration and counts the number of times each piece type is present and
	 *  returns a map containing the piece types as keys and the number of times they appear
	 *  as values.
	 * 
	 * 
	 * @param config the configuration to count
	
	 * @return a map containing the piece types as keys and the number of times they appear as values */
	protected Map<PieceType,Integer> countPieces(Collection<PieceLocationDescriptor> config){
		final Map<PieceType,Integer> pieceCounts = new HashMap<PieceType,Integer>();
	
		// Iterate through the configuration and count the number of pieces
		final Iterator<PieceLocationDescriptor> pieces = config.iterator();
		while (pieces.hasNext()){
			// Get the current piece's type
			PieceType typeOfPiece = pieces.next().getPiece().getType();		
			
			// Get the current count of that pieces type
			Integer previousCount = pieceCounts.get(typeOfPiece);		
			
			// If the count was null, make it 0
			if (previousCount == null) previousCount = 0;
			
			// Increment the count
			previousCount++;
			
			// Store the count
			pieceCounts.put(typeOfPiece, previousCount);
		}	
		
		return pieceCounts;
	}
}
