/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package message.implementations;

import game.common.Location;
import game.common.PieceType;
import game.common.turnResult.ITurnResult;
import message.AbstractMessage;
import message.StrategyMessageType;


/** A message containing the information about a move
 *  that a player is trying to make. 
 *  
 *  This message also gets re-purposed to hold the 
 *  results of a move when sent back to a client.
 * 
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class MoveMessage extends AbstractMessage {

	/** The piece being moved */
	private final PieceType piece;
	/** The location the piece is at */
	private final Location from;
	/** The location to move the piece to */
	private final Location to;
	/** The result of a message */
	private ITurnResult result = null;
	
	/** Basic constructor to take the pieces of a move
	 * 
	 * @param piece the piece to move
	 * @param from  the location the piece is at
	 * @param to    the location to move the piece to
	 */
	public MoveMessage(PieceType piece, Location from, Location to){
		type = StrategyMessageType.MOVE.toString();
		this.piece = piece;
		this.from = from;
		this.to = to;
	}

	/** Get the piece type of the piece being moved
	 * 
	 * @return the type of the piece being moved
	 */
	public PieceType  getPieceType(){
		return piece;
	}

	/** Get the location the piece is moving from 
	 * 
	 * @return the location the piece is moving from
	 */
	public Location getFrom(){
		return from;
	}
	
	/** Get the location the piece is moving to 
	 * 
	 * @return the location the piece is moving to
	 */
	public Location getTo(){
		return to;
	}
	
	
	/** Set the result of the move
	 *  
	 * @param theResult the result of the move
	 */
	public void setMoveResult(ITurnResult theResult){
		result = theResult;
		type = StrategyMessageType.RESULT.toString();
	}

	/** Get the result of the move
	 *  
	 * @param theResult the result of the move
	 */
	public ITurnResult getMoveResult(){
		return result;
	}
	
	
}
