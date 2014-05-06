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

import message.AbstractMessage;

/** This message is provided to support additional 
 *  commands to be sent to the server. This message should 
 *  be used when new interceptors are added AND the server
 *  has not been updated yet. 
 * 
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class GenericMessage extends AbstractMessage{


	/** Arguments stored in this message */
	private Object[] arguments;

	/** This constructor takes a message type and an array 
	 *  of arguments and stores them.
	 *  
	 * @param serviceRequested The type of message
	 * @param arguments arguments important to the message
	 */
	public GenericMessage(String serviceRequested, Object[] arguments){
		type = serviceRequested;
		this.setArguments(arguments);
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}
}
