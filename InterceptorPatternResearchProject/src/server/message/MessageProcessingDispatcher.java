/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package server.message;

import interceptor.StrategyMessageInterceptor;
import interceptor.StrategyPreprocessorInterceptor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import message.Sendable;
import strategy.common.StrategyRuntimeException;
import strategy.game.StrategyGameController;


/** This dispatcher is responsible for finding an
 *  interceptor to process messages as they come
 *  into the server.
 * 
 *  This is implemented as a singleton
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class MessageProcessingDispatcher {

	/** The currently available interceptors */
	private final static Map<String, StrategyMessageInterceptor> theInterceptors = new HashMap<String,StrategyMessageInterceptor>();
	/** The preprocessing interceptors */
	private final static Collection<StrategyPreprocessorInterceptor> preProcessingInterceptors = new LinkedList<StrategyPreprocessorInterceptor>();
	/** An instance of this object */
	protected static MessageProcessingDispatcher instance = new MessageProcessingDispatcher();
	/** A counter that says which interceptor list to add to */
	private int counter = 0;

	/** Basic constructor that takes a reference to a StrategyGameController.
	 *  This is from the server and is here to be used by the Interceptors
	 * 
	 * Tries to read in classes from text files.. gives up at the first sign of trouble
	 * 
	 * @param game reference to the current game from the server
	 */
	private MessageProcessingDispatcher() {

		final BufferedReader messageProcessingInterceptorsFile;
		final BufferedReader preprocessingInterceptorsFile;

		// Try to instantiate the file readers, give up if either fails
		try {
			messageProcessingInterceptorsFile = new BufferedReader( new FileReader("../InterceptorPatternResearchProject/interceptors/MessageProcessingInterceptors.txt"));
			preprocessingInterceptorsFile = new BufferedReader( new FileReader("../InterceptorPatternResearchProject/interceptors/PreprocessingInterceptors.txt"));
		} catch (FileNotFoundException e) {
			throw new StrategyRuntimeException("One of the interceptor files is missing");
		}

		// Add the interceptors
		addInterceptors(messageProcessingInterceptorsFile); 
		addInterceptors(preprocessingInterceptorsFile);

	}

	/** Takes a file, reads it and adds the given classes to the given list or map.
	 * 
	 * 
	 * @param messageProcessingInterceptorsFile
	 * @param next
	 * @param classLoader
	 * @return
	 */
	private void addInterceptors(final BufferedReader messageProcessingInterceptorsFile) {

		// Get a class loader for loading classes
		final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		// Go through the file
		String next;
		try {
			next = messageProcessingInterceptorsFile.readLine();
		} catch (IOException e1) {
			next = null;
		}
		
		final Collection<String> classNames = new LinkedList<String>();
		while (next != null) {
			classNames.add(next);		
			try {
				next = messageProcessingInterceptorsFile.readLine();
			} catch (IOException e1) {
				// Have to catch the IO exception...
				next = null;
			}
		
		}

		// Go through the file
		final Iterator<String> classIter = classNames.iterator();
		while (classIter.hasNext()) {

			// Try to load the classes
			try {
				// Instantiate the interceptor
				Object aSMI =  classLoader.loadClass(classIter.next()).getConstructors()[0].newInstance((Object[]) null);
				// Add the interceptor internally
				if (counter == 0){			
					addInterceptor((StrategyMessageInterceptor) aSMI);
				} else if (counter == 1){
					addInterceptor((StrategyPreprocessorInterceptor) aSMI);
				}
			} catch (Exception e1) {
				throw new StrategyRuntimeException("An interceptor binary was not added correctly or the .java file was not added correctly");
			} 
		}
		counter++;
	}

	/**
	 * @return the instance
	 */
	public static MessageProcessingDispatcher getInstance() {
		return instance;
	}


	/** Take a messag from the server and use the right
	 *  interceptor to handle it
	 * 
	 * @param message the message to process
	 * @param game a reference to the current game because interceptors need that
	 * @return the result
	 */
	public Sendable handleMessageProcessing(Sendable message, StrategyGameController game) {
		// Time to process the message
		Sendable theMessage = message;

		// Preprocessing
		final Iterator<StrategyPreprocessorInterceptor> preInters = preProcessingInterceptors.iterator();
		while (preInters.hasNext()){
			// Preprocess
			theMessage = preInters.next().handleMessage(theMessage, game);

			// Make sure the preprocessor succeeded
			if (!(theMessage.getMessageStatus())){
				return theMessage; 
			}

			// Reset message status
			theMessage.setMessageStatus(false);
		}

		// What is the service?
		final String serviceNeeded = theMessage.getTypeString();

		// Get the interceptor
		final StrategyMessageInterceptor anInterceptor = theInterceptors.get(serviceNeeded);

		// Check to see if the interceptor retrieved was usable
		if (anInterceptor == null){
			return theMessage; // return it as is because the status was already false
		}

		// Do the work
		return anInterceptor.handleMessage(theMessage,game);
	}


	/** Adds an interceptor to the map of available interceptors
	 * 
	 * @param toAdd the interceptor to add
	 * @return false if it was added already
	 */
	public static boolean addInterceptor(StrategyMessageInterceptor toAdd){
		if (theInterceptors.put(toAdd.getServiceName(), toAdd) == null){
			return true;
		}
		// False indicates the interceptor already existed
		return false;
	}

	/** Adds an interceptor to the collection of available preprocessing interceptors
	 * 
	 * NOTE: PREPROCESSORS SHOULD BE DESIGNED TO BE RUN IN ANY ORDER
	 * 
	 * @param toAdd the interceptor to add
	 * @return true if successful
	 */
	public static boolean addInterceptor(StrategyPreprocessorInterceptor toAdd){
		preProcessingInterceptors.add( toAdd);
		return true;

	}
}
