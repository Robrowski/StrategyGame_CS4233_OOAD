/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.battle;

/** BattleResult is an enum used internally to note the
 *  winner of a particular battle
 * 
 * @author Dabrowski
 * @version Oct 4, 2013
 */
public enum BattleResult {
	NOTSPECIAL, DRAW, ATTACKERWINS, DEFENDERWINS, DEFENDERWINS_NOTMOVED;
}
