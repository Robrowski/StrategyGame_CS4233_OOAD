/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.delta;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import strategy.game.common.PieceType;
import strategy.game.common.battle.BattleResult;
import strategy.game.common.battle.ISpecialBattles;

/** This is to test only the special battle
 *  conditions of Delta strategy
 * 
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class DeltaSpecialBattlesTest {

	/** DeltaSpecialBattles */
	ISpecialBattles specialBattles = new DeltaSpecialBattles();
		
	@Test
	public void testMinerKillsBomb(){
		assertSame(BattleResult.ATTACKERWINS, specialBattles.specialBattle(PieceType.MINER,PieceType.BOMB ));
	}
	
	@Test
	public void testBombCausesDraw_minerExcluded(){
		assertSame(BattleResult.DRAW,	specialBattles.specialBattle(PieceType.SPY,PieceType.BOMB ));
		assertSame(BattleResult.DRAW,	specialBattles.specialBattle(PieceType.MARSHAL,PieceType.BOMB ));
		assertSame(BattleResult.DRAW, 	specialBattles.specialBattle(PieceType.GENERAL,PieceType.BOMB ));
	}
	
	@Test
	public void testSpyKillsMarshal(){
		assertSame(BattleResult.ATTACKERWINS, specialBattles.specialBattle(PieceType.SPY,PieceType.MARSHAL ));
	}
	
	@Test
	public void testNotSpecialBattlesAreNotSpecial(){
		assertSame(BattleResult.NOTSPECIAL,	specialBattles.specialBattle(PieceType.SPY,PieceType.MINER ));
		assertSame(BattleResult.NOTSPECIAL,	specialBattles.specialBattle(PieceType.MARSHAL,PieceType.SPY ));
	}	
}
