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

import strategy.game.common.DetailedMoveResult;
import strategy.game.common.Location;
import strategy.game.common.Piece;

/** Classes implementing this interface are responsible for handling battle
 *  and creating detailed move results. Those results contain all the 
 *  information necessary to update the field configuration after a battle
 *  has been finished. The doBattle method is also called when there is not
 *  battle EX. no piece is located atTo : in this case, a DetailedMoveResult
 *  is still necessary.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IBattleEngine {

	/** DO BATTLE! Take the pieces and see who the winner is. It is possible
	 *  for there not to be a piece at the TO location, in which case the 
	 *  battle winner is very easy to decide. In every case, a 
	 *  DetailedMoveResult  must be created. 
	 * 
	 *  If this method is called, it is assumed that the locations are valid
	 *  and that the piece atFrom is capable of going the distance.
	 * 
	 * @param atFrom The piece attacking
	 * @param moveFrom The starting location
	 * @param atTo   The piece being attacked (or null)
	 * @param moveTo   The attempted move
	
	 * @return a DetailedMoveResult, containing lots of details */
	DetailedMoveResult doBattle(Piece atFrom, Location moveFrom, Piece atTo,
			Location moveTo);

}
