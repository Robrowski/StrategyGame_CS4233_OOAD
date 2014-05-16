/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.go;

import java.util.Collection;

import game.GameController;
import game.common.PieceLocationDescriptor;
import game.go.score.GoScoreKeeper_Chinese;

/** Test double of the score keeper for testing purposes
 * 
 * @author Dabrowski
 *
 */
public class GoScoreKeeperTestDouble extends GoScoreKeeper_Chinese {

	/** Basic constructor
	 * @param goGame
	 */
	GoScoreKeeperTestDouble(GameController goGame) {
		super(goGame);
		// TODO Auto-generated constructor stub
	}

	/** Overridden to allow for public calling
	 * 
	 */
	public 	Collection<PieceLocationDescriptor> findDeadPieces(){
		return super.findDeadPieces();
	}
	
}
