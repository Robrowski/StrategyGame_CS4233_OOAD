/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.strategy.epsilon;

import game.common.Location;
import game.common.PieceType;
import game.common.battle.BattleResult;
import game.common.battle.ISpecialBattles;
import game.common.pieceStats.IPiecePowers;

/** This class is capable of taking two pieces and evaluating all the special battles
 *  for the Epsilon version of Strategy.
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class EpsilonSpecialBattles implements ISpecialBattles {

	/* (non-Javadoc)
	 * @see game.strategy.common.battle.SpecialBattles#specialBattle(game.common.PieceType, game.common.PieceType)
	 */
	@Override
	public BattleResult specialBattle(PieceType attacker, Location from, PieceType defender, Location to) {
		// Locations aren't critical in this version

		// Main way of figuring out which special case to deal with
		final int moveDistance = from.distanceTo(to);
		
		// Very specific attack case
		if (attacker == PieceType.FIRST_LIEUTENANT && moveDistance == 2){
			final IPiecePowers epsilonPowers = new EpsilonPiecePowers(); // absolutely necessary here
			final int atkStr = epsilonPowers.getAtk(attacker);
			final int defStr = epsilonPowers.getDef(defender);
			
			// Now evaluate battle for the very special case that first lieutenant lost on a 2 move attack
			if (defStr > atkStr){
				 return BattleResult.DEFENDERWINS_NOTMOVED; 
			}
		}
		
		if (PieceType.MINER == attacker && PieceType.BOMB == defender) return BattleResult.ATTACKERWINS;
		if (PieceType.BOMB == defender) return BattleResult.DEFENDERWINS_NOTMOVED;
		if (PieceType.SPY == attacker && PieceType.MARSHAL == defender) return BattleResult.ATTACKERWINS;

		return BattleResult.NOTSPECIAL;
	}

}
