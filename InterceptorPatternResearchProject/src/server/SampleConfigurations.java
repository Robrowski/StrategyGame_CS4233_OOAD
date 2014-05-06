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

import java.util.Collection;
import java.util.LinkedList;

import strategy.common.PlayerColor;
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/** This class provides sample piece configurations for 
 *  different versions of Strategy and lets the StrategyServer
 *  use them. All configurations are valid. It is implemented
 *  as a singleton object.
 * 
 *  This will almost definitely be changed out
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class SampleConfigurations {

	/** An instance of this singleton object */
	private final static SampleConfigurations configurator = new SampleConfigurations();

	// Red Piece locations
	private final Location redFlagLocation     = new Location2D(0, 1);
	private final Location redMarshalLocation  = new Location2D(1, 1);
	private final Location redColonelLocation1 = new Location2D(2,1);
	private final Location redColonelLocation2 = new Location2D(4,0);
	private final Location redCaptainLocation1 = new Location2D(2, 0);
	private final Location redCaptainLocation2 = new Location2D(3, 1);
	private final Location redLieutenantLocation1 = new Location2D(0, 0);
	private final Location redLieutenantLocation2 = new Location2D(1, 0);
	private final Location redLieutenantLocation3 = new Location2D(4, 1);
	private final Location redSergeantLocation1 = new Location2D(3, 0);
	private final Location redSergeantLocation2 = new Location2D(5, 0);
	private final Location redSergeantLocation3 = new Location2D(5, 1);

	// Blue Piece locations
	private final Location blueFlagLocation = new Location2D(3, 4);
	private final Location blueMarshalLocation = new Location2D(5, 5);
	private final Location blueColonelLocation1 = new Location2D(2, 4);
	private final Location blueColonelLocation2 = new Location2D(0, 5);
	private final Location blueCaptainLocation1 = new Location2D(4, 4);
	private final Location blueCaptainLocation2 = new Location2D(4, 5);
	private final Location blueLieutenantLocation1 = new Location2D(1, 4);
	private final Location blueLieutenantLocation2 = new Location2D(1, 5);
	private final Location blueLieutenantLocation3 = new Location2D(3, 5);
	private final Location blueSergeantLocation1 = new Location2D(2, 5);
	private final Location blueSergeantLocation2 = new Location2D(5, 4);
	private final Location blueSergeantLocation3 = new Location2D(0, 4);

	// Red Pieces
	private final Piece redFlag = new Piece(PieceType.FLAG, PlayerColor.RED);
	private final Piece redMarshal = new Piece(PieceType.MARSHAL, PlayerColor.RED);
	private final Piece redColonel = new Piece(PieceType.COLONEL, PlayerColor.RED);

	private final Piece redCaptain = new Piece(PieceType.CAPTAIN, PlayerColor.RED);
	private final Piece redLieutenant = new Piece(PieceType.LIEUTENANT, PlayerColor.RED);
	private final Piece redSergeant = new Piece(PieceType.SERGEANT, PlayerColor.RED);

	// Blue Pieces
	private final Piece blueFlag = new Piece(PieceType.FLAG, PlayerColor.BLUE);
	private final Piece blueMarshal = new Piece(PieceType.MARSHAL, PlayerColor.BLUE);
	private final Piece blueColonel = new Piece(PieceType.COLONEL, PlayerColor.BLUE);
	private final Piece blueCaptain = new Piece(PieceType.CAPTAIN, PlayerColor.BLUE);
	private final Piece blueLieutenant = new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE);
	private final Piece blueSergeant = new Piece(PieceType.SERGEANT, PlayerColor.BLUE);

	//Piecs + locations
	private final PieceLocationDescriptor redFlagAtLocation = new PieceLocationDescriptor( redFlag, redFlagLocation);
	private final PieceLocationDescriptor blueFlagAtLocation = new PieceLocationDescriptor( blueFlag, blueFlagLocation);

	/** Configuration for the red team */
	private  final Collection<PieceLocationDescriptor> validRedConfiguration ;	
	/** Configuration for the blue team */
	private  final Collection<PieceLocationDescriptor> validBlueConfiguration ;

	/** Constructor that sets up data */
	private SampleConfigurations(){
		validBlueConfiguration = new LinkedList<PieceLocationDescriptor>();
		validRedConfiguration = new LinkedList<PieceLocationDescriptor>();
		// Fresh configuration for each test
		validBlueConfiguration.add(blueFlagAtLocation);
		validBlueConfiguration.add(new PieceLocationDescriptor( blueMarshal, blueMarshalLocation));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueColonel, blueColonelLocation1));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueColonel, blueColonelLocation2));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueCaptain, blueCaptainLocation1));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueCaptain, blueCaptainLocation2));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, blueLieutenantLocation1));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, blueLieutenantLocation2));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueLieutenant, blueLieutenantLocation3));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, blueSergeantLocation1));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, blueSergeantLocation2));
		validBlueConfiguration.add(new PieceLocationDescriptor( blueSergeant, blueSergeantLocation3));
		validRedConfiguration.add(  redFlagAtLocation );
		validRedConfiguration.add(new PieceLocationDescriptor( redMarshal, redMarshalLocation));
		validRedConfiguration.add(new PieceLocationDescriptor( redColonel, redColonelLocation1));
		validRedConfiguration.add(new PieceLocationDescriptor( redColonel, redColonelLocation2));
		validRedConfiguration.add(new PieceLocationDescriptor( redCaptain, redCaptainLocation1));
		validRedConfiguration.add(new PieceLocationDescriptor( redCaptain, redCaptainLocation2));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, redLieutenantLocation1));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, redLieutenantLocation2));
		validRedConfiguration.add(new PieceLocationDescriptor( redLieutenant, redLieutenantLocation3));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, redSergeantLocation1));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, redSergeantLocation2));
		validRedConfiguration.add(new PieceLocationDescriptor( redSergeant, redSergeantLocation3));
	}

	/**
	 * @return the instance
	 */
	public static SampleConfigurations getInstance()
	{
		return configurator;
	}
	
	/** 
	 * @return a valid red configuration for beta/gamma
	 */
	public Collection<PieceLocationDescriptor> getValidBetaRedConfig(){
		return validRedConfiguration;
	}
	
	/** 
	 * @return a valid blue configuration for beta/gamma
	 */
	public Collection<PieceLocationDescriptor> getValidBetaBlueConfig(){
		return validBlueConfiguration;
	}
}
