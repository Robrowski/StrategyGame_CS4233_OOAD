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


/** This interface should be implemented by any class
 *  that wishes to act as a message to be sent over 
 *  the basic network.
 *  
 *  Once I learn more network protocols, methods do need
 *  to be added to this to allow message compression or
 *  conversion to JSON.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface Sendable {
	
	/** Get the type of the message as a string
	 * 
	 * @return the type of the message
	 */
	String getTypeString();
	
	
	/** Get the status of the message
	 *  True indicates good, false indicates bad
	 *  null = default
	 *  
	 * @return the status
	 */
	Boolean getMessageStatus();
	
	
	/** Set the status of the message
	 *  True indicates good, false indicates bad
	 *  null = default
	 *  
	 *  @param status to set to
	 */
	void setMessageStatus(Boolean status);
	
	/** Get the reason for the fault, if applicable
	 * @return the reason for the fault, if applicable
	 */
	String getFaultReason();
	
	/** Get the reason for the fault, if applicable
	 * @param faultReason the reason for the fault, if applicable
	 */
	void  setFaultReason(String faultReason);
	
}
 