/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package server;

import java.util.LinkedList;

import message.Sendable;


/** This double is used to reset the singleton object between tests ONLY
 * 
 * 
 * @author Dabrowski
 *
 */
public class StrategyServerTestDouble extends StrategyServer{

	/** Reset the singleton object for testing purposes - technically
	 *  only resets the game right now, because that is all that matters
	 * 
	 */
	public static void resetSingleton(){
		game = null;
		log = new LinkedList<Sendable>();
	}

}
