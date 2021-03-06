/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.validation.configuration;

import game.VersionRules;
import game.common.PieceLocationDescriptor;
import game.common.validation.configuration.location.IPieceLocationValidator;
import game.common.validation.configuration.pieces.IPieceAllowanceValidator;

import java.util.Collection;

import common.StrategyException;

/** A configuration validator good for Enough
 *  variability points are covered in the parameter inputs that multiple versions of 
 *  Strategy should be covered by this single implementation.
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class ConfigurationValidator2D implements IConfigurationValidator {

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
	public ConfigurationValidator2D(VersionRules theRules){
		this.theRules = theRules;
	}

	/* (non-Javadoc)
	 * @see game.strategy.common.validation.configuration.IConfigurationValidator#validatePlayerConfigurations(java.util.Collection, java.util.Collection)
	 */
	@Override
	public void validatePlayerConfigurations(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException {

		// Tally of invalidities
		String invalidities = invalidityStatement; 		

		// Validate the number of pieces each team has
		final IPieceAllowanceValidator pieceCounter = theRules.getPieceAllowanceValidator();	
		invalidities += pieceCounter.validatePieceCounts( redConfiguration);
		invalidities += pieceCounter.validatePieceCounts( blueConfiguration);

		// Validate the locations of the pieces for each team
		final IPieceLocationValidator locationValidator = theRules.getPieceLocationValidator();
		invalidities += locationValidator.checkPieceLocations(redConfiguration,theRules.getPlayer1() );
		invalidities += locationValidator.checkPieceLocations(blueConfiguration, theRules.getPlayer2());

		// Throw invalidities
		if (!invalidities.equals(invalidityStatement) ){
			throw new StrategyException(invalidities);
		}
	}	






}
