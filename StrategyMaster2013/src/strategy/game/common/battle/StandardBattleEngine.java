/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common.battle;

import strategy.common.PlayerColor;
import strategy.game.common.DetailedMoveResult;
import strategy.game.common.Location;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.pieceStats.IPiecePowers;

/** The standard battle engine responsible for handling battles
 *  and simple moves, including when there is no piece at the 
 *  destination. The main responsibility here is to create
 *  a DetailedMoveResult
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class StandardBattleEngine implements IBattleEngine {

	/** An object to ask when piece stats are needed   */
	private final IPiecePowers pieceStats;
	/** An object to use to handle special case battles */
	private final ISpecialBattles specialBattles;

	/** The basic constructor that takes in the informational objects
	 *  it will need when evaluating a battle
	 * 
	 * @param pieceStats The statistics of the pieces applicable
	 * @param specialBattles An object for providing information about special batles
	 */
	public StandardBattleEngine(IPiecePowers pieceStats, ISpecialBattles specialBattles) {
		this.pieceStats = pieceStats;
		this.specialBattles = specialBattles;
	}

	/* (non-Javadoc)
	 * @see strategy.game.version.common.battle.IBattleEngine#doBattle(strategy.game.common.Piece, strategy.game.common.Location, strategy.game.common.Piece, strategy.game.common.Location)
	 */
	@Override
	public DetailedMoveResult doBattle(Piece atFrom, Location moveFrom,	Piece atTo, Location moveTo) {
		// If atTo != null, then battle
		if (atTo != null){
			// Get the types of the pieces
			final PieceType attacker = atFrom.getType();
			final PieceType defender = atTo.getType();

			// Have a status ready
			MoveResultStatus gameWinner = MoveResultStatus.OK;

			// Start by checking for the flag, because thats the END-GAME
			if (defender == PieceType.FLAG){
				// Figure out who won
				if (atFrom.getOwner() == PlayerColor.BLUE){	
					gameWinner = MoveResultStatus.BLUE_WINS;
				} else 	{	
					gameWinner = MoveResultStatus.RED_WINS;
				}
			}
			
			// For storing the move result as it is calculated
			DetailedMoveResult finalMoveResult; 
			
			// Check for special battle conditions: *NOTE* special conditions override
			// regular battles between different powers
			final BattleResult result = specialBattles.specialBattle(attacker, moveFrom, defender, moveTo);
			
			// Evaluate winner by power
			final int atkPower = pieceStats.getAtk(attacker);
			final int defPower = pieceStats.getDef(defender);	
			
			// Attacker wins
			if (result == BattleResult.ATTACKERWINS || (atkPower > defPower && result == BattleResult.NOTSPECIAL)){  
				finalMoveResult = new DetailedMoveResult(gameWinner, 
						new PieceLocationDescriptor(atFrom, moveTo), 
						moveFrom, moveTo);
			// Defender wins
			} else if (result == BattleResult.DEFENDERWINS || ( atkPower < defPower && result == BattleResult.NOTSPECIAL)){ 
				finalMoveResult = new DetailedMoveResult(gameWinner, 
						new PieceLocationDescriptor(atTo, moveFrom), 
						moveTo, moveFrom);
			// Because bombs don't move
			} else if (result == BattleResult.DEFENDERWINS_NOTMOVED){
				finalMoveResult = new DetailedMoveResult(gameWinner,
						new PieceLocationDescriptor(atTo, moveTo),
						moveTo, moveFrom);	
			// Its a draw!
			} else {
				finalMoveResult = new DetailedMoveResult(gameWinner, 
						null, moveFrom, moveTo);		
			}			
			// Return the final result
			return finalMoveResult;
		} 
		// Else just a regular move
		return new DetailedMoveResult(MoveResultStatus.OK,  
				new PieceLocationDescriptor(atFrom, moveTo), 
				moveFrom, null);
	}
}
