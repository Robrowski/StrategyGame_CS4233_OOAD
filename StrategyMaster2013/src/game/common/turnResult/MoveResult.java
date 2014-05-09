/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package game.common.turnResult;

import java.util.Collection;
import java.util.LinkedList;

import game.common.PieceLocationDescriptor;

/**
 * This class contains the complete results of a move.
 * 
 * @author gpollice
 * @version Sep 7, 2013
 */
public class MoveResult implements ITurnResult
{
	private final MoveResultStatus status;
	private final PieceLocationDescriptor battleWinner;
	/** The pieces removed */
	Collection<PieceLocationDescriptor> piecesRemoved;
	
	
	/**
	 * Constructor that sets the properties.
	 * @param status the move result status
	 * @param battleWinner if there were a strike, this contains the information
	 * 		about the winner.
	 */
	public MoveResult(MoveResultStatus status, PieceLocationDescriptor battleWinner)
	{
		this(status, battleWinner, new LinkedList<PieceLocationDescriptor>());
	}

	/**
	 * Constructor that sets the properties.
	 * @param status the move result status
	 * @param battleWinner if there were a strike, this contains the information
	 * 		about the winner.
	 * @param piecesRemoved Collection<PieceLocationDescriptor>
	 */
	public MoveResult(MoveResultStatus status, PieceLocationDescriptor battleWinner, Collection<PieceLocationDescriptor> piecesRemoved)
	{
		this.status = status;
		this.battleWinner = battleWinner;
		this.piecesRemoved = piecesRemoved;
	}
	
	
	
	/**
	 * @return the status
	 */
	public MoveResultStatus getStatus()
	{
		return status;
	}

	/**
	 * @return the battleWinner
	 */
	public PieceLocationDescriptor getBattleWinner()
	{
		return battleWinner;
	}

	/* (non-Javadoc)
	 * @see game.common.turnResult.ITurnResult#getPiecesRemoved()
	 */
	@Override
	public Collection<PieceLocationDescriptor> getPiecesRemoved() {
		return piecesRemoved;
	}
}
