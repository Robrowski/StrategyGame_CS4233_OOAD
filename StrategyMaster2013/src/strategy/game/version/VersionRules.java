/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version;

import java.util.Collection;

import strategy.common.PlayerColor;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.battle.IBattleEngine;
import strategy.game.common.board.IBoardManager;
import strategy.game.common.history.IMoveHistory;
import strategy.game.common.validation.configuration.IConfigurationValidator;
import strategy.game.common.validation.configuration.location.IPieceLocationValidator;
import strategy.game.common.validation.configuration.pieces.IPieceAllowanceValidator;
import strategy.game.common.validation.move.IMoveDistanceValidator;
import strategy.game.common.validation.move.IMoveSpecialCaseValidator;

/** Classes that implement this class are meant to be
 *  rule sets designed specifically for each version
 *  of Strategy. All variability points should be 
 *  covered inside the classes implementing this. The 
 *  methods below are to be used to provide the tools
 *  a StrategyGameController will need to service a 
 *  game. 
 *  
 *  Classes implementing this should be created in the
 *  StrategyGameFactory, but could also be formulated 
 *  with peticulars client-side (someday).
 * 
 * 
 *  This class is used to follow a new kind of pattern
 *  Recipe Pattern: pass a recipe into the factory (or
 *  make it there), and that recipe gets passed through 
 *  the entire system, providing validators and other
 *  pieces of data that make different versions of 
 *  Strategy unique. The objects generated here are 
 *  tools like validators following interfaces. The 
 *  collections are samples of valid data.
 * 
 * 
 * 
 * This class might actually be a mobile super factory...?
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface VersionRules {

	
	/** Gets the color of player 2. Typically this is the player 
	 * located on the top side of the board at the highest indexed 
	 * locations. 
	 * 
	 * @return the color of player 2
	 */
	PlayerColor getPlayer2();
	
	/** Gets the color of player 1. Typically this is the player 
	 * located on the bottom of the board at the lowest indexed 
	 * locations
	 * 
	 * @return the color of player 1
	 */
	PlayerColor getPlayer1();
	
	/** Creates a configuration validator that can be used
	 *  to make sure a player's configuration is correct.
	 * 
	 * @return an IConfigurationValidator to validate a configuration with
	 */
	IConfigurationValidator getConfigurationValidator();
	
	/** Creates a piece allowance validator that can be used
	 *  to make sure a player's configuration has the right 
	 *  number of pieces.
	 * 
	 * @return an IPieceAllowanceValidator to validate a configuration with
	 */
	IPieceAllowanceValidator getPieceAllowanceValidator();
	
	/** Creates a piece location validator that can be used
	 *  to make sure a player's configuration has its pieces
	 *  in valid locations.
	 * 
	 * @return an IPieceLocationValidator to validate a configuration with
	 */
	IPieceLocationValidator  getPieceLocationValidator();

	/** Puts together a Collection of additional field items
	 *  that should be present in the current version of Strategy
	 * 
	
	 * @return a collection of additional field items */
	Collection<PieceLocationDescriptor> additionalFieldItems();
	
	


	
	/** Creates a move distance validator that can be used to make
	 *  sure a move distance is valid. That validator is designed
	 *  for repeated use.
	 *  
	 * @return a IMoveDistanceValidator for validating move distances
	 */
	IMoveDistanceValidator getMoveDistanceValidator();

	/** Creates a move validator that can be used to make
	 *  sure a move is valid. That validator is designed
	 *  for repeated use. 
	 *  
	 *  Special cases include:
	 *  	- don't attack choke points
	 *  	- don't attack allies
	 *  	- don't move pieces that aren't yours :P
	 *  	- don't say one piece then try to move another
	 *  	- etc.
	 * @return an IMoveSpecialCaseValidator for repeated use
	 */
	IMoveSpecialCaseValidator getMoveSpecialCaseValidator();

	/** Return a move history object that is used to store moves. It
	 *  is also responsible for throwing exceptions when the move
	 *  repetition rule is violated (or other history rules)
	 * 
	 * @return a move history object that is used to store moves
	 */
	IMoveHistory getMoveHistory();

	/** Return a battle engine applicable to the current game
	 *  of strategy. Should be built for repeated use. It is 
	 *  responsible for taking two pieces, and forming a 
	 *  full move result - DetailedMoveResult
	 *  
	 * @return returns a battle engine for multiple use
	 */
	IBattleEngine getBattleEngine();

	/** Return a board manager that is used to keep track of the field
	 *  configuration in a game of Strategy.  
	 * 
	 * @return a board manager
	 */
	IBoardManager getBoard();
	
}
