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
import strategy.game.common.PieceType;
import strategy.game.common.validation.configuration.IConfigurationValidator;
import strategy.game.common.validation.configuration.location.IPieceLocationValidator;
import strategy.game.common.validation.configuration.pieces.IPieceAllowanceValidator;
import strategy.game.version.VersionRules;

/** A configuration validator designed to handle the special case that is Epsilon
 *  where any piece may be replaced by a flag
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class EpsilonConfigurationValidator implements IConfigurationValidator {

	/** The base statement for messages describing invalidity */
	private static final String invalidityStatement = "Problems with the configurations: ";
	/** The rules for the version of Strategy that is being validated */
	private final VersionRules theRules;

	/** Basic constructor that takes the rules for the current version of Strategy. These
	 *  rules provide the tools necessary for validating both the piece allowances and 
	 *  piece locations.
	 * 
	 * @param theRules The rules for the version of Strategy that is being validated
	 */
	public EpsilonConfigurationValidator(VersionRules theRules){
		this.theRules = theRules;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.common.validation.configuration.IConfigurationValidator#validatePlayerConfigurations(java.util.Collection, java.util.Collection)
	 */
	@Override
	public void validatePlayerConfigurations(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException {

		// Tally of invalidities
		String invalidities = invalidityStatement; 		

		// Validate the number of pieces each team has
		final IPieceAllowanceValidator pieceCounter = theRules.getPieceAllowanceValidator();	
		
		invalidities += epsilonVerifyPieceCounts(redConfiguration, pieceCounter);
		invalidities += epsilonVerifyPieceCounts(blueConfiguration, pieceCounter);

		// Validate the locations of the pieces for each team
		final IPieceLocationValidator locationValidator = theRules.getPieceLocationValidator();
		invalidities += locationValidator.checkPieceLocations(redConfiguration,theRules.getPlayer1() );
		invalidities += locationValidator.checkPieceLocations(blueConfiguration, theRules.getPlayer2());

		// Throw invalidities
		if (!invalidities.equals(invalidityStatement) ){
			throw new StrategyException(invalidities);
		}
	}

	/** For Epsilon specific, verifies that exactly one error is present and that it
	 *  is the expected one. Derpy way of adding an extra flag and removing an extra piece
	 * 
	 * @param config the configuration to check
	 * @param pieceCounter the piece counter tool to use
	 * @return the invalidities string 
	 */
	private String epsilonVerifyPieceCounts(
			Collection<PieceLocationDescriptor> config,
			final IPieceAllowanceValidator pieceCounter) {

		String redsProblems = pieceCounter.validatePieceCounts( config);
		if (!redsProblems.contains(PieceType.FLAG.toString()) && redsProblems.contains("#")){
			redsProblems = "";
		}
		
		return redsProblems;
	}	






}
