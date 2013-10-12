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

import strategy.game.common.Location;
import strategy.game.common.PieceType;

/** Classes implementing this interface are responsible for knowing
 *  the special circumstances that may override piece powers during
 *  special battle cases.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface ISpecialBattles {

	/** Take in two pieces and return a battle result, which will be
	 * NOTSPECIAL in most cases because most pieces do not have powers.
	 * If they do have powers that apply, the BattleResult returned
	 * will reveal the winner.
	 * 
	 * @param attacker The attacking piece
	 * @param from start location
	 * @param defender The defending piece
	 * @param to  to location	
	 * @return The result of the battle if special circumstances apply */
	BattleResult specialBattle(PieceType attacker, Location from, PieceType defender, Location to);

}
