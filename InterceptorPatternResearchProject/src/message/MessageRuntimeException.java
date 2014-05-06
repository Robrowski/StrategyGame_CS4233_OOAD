/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package message;

/** This exception is used when messages don't work out well
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class MessageRuntimeException extends RuntimeException{

	/** Basic constructor that holds a message about the excpetion
	 * 
	 * @param message the message
	 */
	public MessageRuntimeException (String message){
		super(message);
	}
	
}
