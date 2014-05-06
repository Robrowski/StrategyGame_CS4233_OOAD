/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.history;

import game.common.DetailedMoveResult;
import game.common.PieceType;

import java.util.LinkedList;

import common.StrategyException;
import common.StrategyRuntimeException;

/** This class is responsible for recording moves for the Gamma 
 *  version of strategy. It must also be on the look out for
 *  violations of the move repetition rule
 * 
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class StandardMoveHistory implements IMoveHistory {

	/** The move log */
	private final LinkedList<DetailedMoveResult> log;

	/** standard constructor that initializes the move log
	 * 
	 */
	public StandardMoveHistory(){
		log = new LinkedList<DetailedMoveResult>();
	}


	/* (non-Javadoc)
	 * @see game.version.common.history.IMoveHistory#recordMove(game.common.MoveResult)
	 */
	@Override
	public void recordMove(DetailedMoveResult theMove) throws StrategyException {
		try {		
	
			// Once the log fills enough, start checking
			if (log.size() > 3){ 
				final DetailedMoveResult second = log.get(1);
				final DetailedMoveResult first  = log.get(3);

				final PieceType firstPiece = first.getBattleWinner().getPiece().getType();
				final PieceType secondPiece = second.getBattleWinner().getPiece().getType();
				final PieceType thirdPiece = theMove.getBattleWinner().getPiece().getType();
				if (firstPiece == secondPiece && secondPiece == thirdPiece ){ // if same pieces then proceed
					// Now check locations
					if (first.isOpposite(second) && first.isEqual(theMove)){
						throw new StrategyException("That violates the move repetition rule");			
					}
				}
			}
			// IF that went well, add the item to the history log
			log.addFirst(theMove);
		} catch(NullPointerException npe){
			// DetailedMoveResults are allowed to contain null datas
			log.addFirst(theMove);
		} catch(StrategyRuntimeException sre){
			log.addFirst(theMove);
		}
	}

}
