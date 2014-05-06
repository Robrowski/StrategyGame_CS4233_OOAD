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

import static org.junit.Assert.assertSame;

import org.junit.Test;

import common.StrategyException;
import common.StrategyRuntimeException;

/** Simple tests make sure the custom exceptions work
 * 
 * 
 * @author Dabrowski
 *
 */
public class StrategyExceptionTest {


	@Test
	public void strategyRuntimeException_holdsException(){
		StrategyRuntimeException sre = new StrategyRuntimeException("A test", new StrategyException("test internal exception"));

		try {
			throw sre;
		} catch(StrategyRuntimeException thrownSRE){
			assertSame(thrownSRE.getCause().getMessage(), "test internal exception");
		}
	}

	@Test
	public void strategyException_holdsException() throws StrategyException{
		StrategyException se = new StrategyException("A test", new StrategyException("test internal exception"));

		try {
			throw se;
		} catch(StrategyException thrownSE){
			assertSame(thrownSE.getCause().getMessage(), "test internal exception");
		}
	}
}
