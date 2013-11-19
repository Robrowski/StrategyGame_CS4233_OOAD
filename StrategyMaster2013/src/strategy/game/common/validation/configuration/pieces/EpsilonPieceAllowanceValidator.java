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
import java.util.Map;

import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/** Modification of the 2D piece validator to allow for the
 * off by one errors allowed in Epsilon Strategy
 * 
 * 
 * @author Dabrowski
 * @version $Revision: 6.9 $
 */
public class EpsilonPieceAllowanceValidator extends
		StandardPieceAllowanceValidator {

	/** Basic constructor that takes a Map of the expected piece types as keys with 
	 * the values being the expected number of times that each should appear
	 * 
	 * @param expectedPieceCounts A map containing the expected pieces and the number of times each should appear
	 */
	public EpsilonPieceAllowanceValidator(
			Map<PieceType, Integer> expectedPieceCounts) {
		super(expectedPieceCounts);
	}

	
	/* For Epsilon specific, verifies that exactly one error is present and that it
	 * is the expected one. Derpy way of adding an extra flag and removing an extra piece
	 * 
	 * 
	 * (non-Javadoc)
	 * @see strategy.game.version.common.validation.configuration.pieces.IPieceAllowanceValidator#validatePieceCounts(java.util.Collection)
	 */
	@Override
	public String validatePieceCounts(Collection<PieceLocationDescriptor> config) {
		String redsProblems = super.validatePieceCounts( config);
		if (!redsProblems.contains(PieceType.FLAG.toString()) && redsProblems.contains("#")){
			redsProblems = "";
		}
		
		return redsProblems;	
	}	
}
