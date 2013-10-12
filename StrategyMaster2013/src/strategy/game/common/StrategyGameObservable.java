/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.common;

/** Components that may be observed should implement this interface
 * 
 * @author Dabrowski, gpollice
 * @version $Revision: 1.0 $
 */
public interface StrategyGameObservable
{
	/** Registers an observer with the observable
	 * 
	 * @param observer to do the observing
	 */
	void register(StrategyGameObserver observer);
	
	/** Unregister the given observer 
	 * 
	 * NOTED: This will FAIL if the instance of the object
	 * given is not the SAME as the one registered in first place
	 * 
	 * @param observer the observer to retire
	 */
	void unregister(StrategyGameObserver observer);
}