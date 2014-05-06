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

/** The basic implementation of a message being
 *  sent over the network.
 * 
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public abstract class AbstractMessage implements Sendable {

	/** The type of the message */
	protected String type; 
	/** The status of the message */
	protected Boolean status = null;
	/** If there was a fault, the reasoning */
	protected String faultReason = "";
	
	
	/** Default constructor */
	protected AbstractMessage(){
		type = null;
	}
	

	/** Get the type of the message as a string
	 * 
	 * @return the type of the message
	 */
	public String getTypeString(){
		return type;
	}
	
	/** Get the status of the message
	 *  True indicates good, false indicates bad
	 *  null = default
	 *  
	 * @return the status
	 */
	public Boolean getMessageStatus(){
		return status;
	}
	
	/** Set the status of the message
	 *  True indicates good, false indicates bad
	 *  null = default
	 *  
	 *  @param status to set to
	 * @return 
	 */
	public void setMessageStatus(Boolean status){
		this.status = status;
	}
	
	/** Get the reason for the fault, if applicable
	 * @return the reason for the fault, if applicable
	 */
	public String getFaultReason(){
		return faultReason;
	}
	
	/** Get the reason for the fault, if applicable
	 * @param faultReason the reason for the fault, if applicable
	 */
	public void  setFaultReason(String faultReason){
		this.faultReason = faultReason;
	}
}
