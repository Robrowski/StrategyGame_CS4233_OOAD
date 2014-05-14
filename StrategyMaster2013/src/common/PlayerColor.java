/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package common;

/**
 * This enumeration identifies the possible player colors in the various versions of
 * Strategy.
 * @author gpollice
 * @version Aug 31, 2013
 */
public enum PlayerColor
{
	RED, BLUE, BLACK, WHITE;
	
	public PlayerColor opposite(){
		switch(this){
		case BLACK:
			return WHITE;
		case WHITE:
			return BLACK;
		
		
		case BLUE:
			return RED;
		case RED:
			return BLUE;

		default:
			return null;
		}
	}
}
