/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package interceptor;

/** This is the generic interceptor used in a StrategyServer
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public interface StrategyInterceptor {
	
	/** Gets the service name, ALL CAPS. This is 
	 *  equivalent to the following expression.
	 *  MessageType.MOVE.toString(); etc.
	 * @return the name of the service
	 */
	String getServiceName();
}
