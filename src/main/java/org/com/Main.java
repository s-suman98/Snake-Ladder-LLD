package org.com;

import org.com.entity.Board;
import org.com.entity.DiceI;
import org.com.entity.Game;
import org.com.entity.SingleDice;
import org.com.enums.GameStatus;
import org.com.model.Ladder;
import org.com.model.Player;
import org.com.model.Position;
import org.com.model.Snake;
import org.com.strategy.SimpleWinStrategy;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
public static void main (String[] args) {
	
	// MALE PLAYER
	Player p1 = new Player (1, "Anish", new Position (1));
	Player p2 = new Player (1, "Anuhya", new Position (1));
	
	
	// Make baord
	Board board = new Board (100);
	board.setLadders (List.of (new Ladder (5, 35), new Ladder (14, 56)));
	board.setSnakes (List.of (new Snake (97, 27), new Snake (60, 35), new Snake (55, 22)));
	
	
	//
	
	
	Game game = Game.builder ().
			            board (board).
			            players (List.of (p1, p2))
			            .gameStatus (GameStatus.NOT_STARTED).currentPlayerTurn (0).winingStrategy (new SimpleWinStrategy ()).build ();
	
	
	//
	
	DiceI dice = new SingleDice ();
	
	
	while ( game.getGameStatus () != GameStatus.COMPLETED ) {
		int pos = dice.roll ();
		
		System.out.println ("||||Dice value|||"+pos);
		
		
		try {
			
			game.makeMove (pos);
			
			
		} catch ( Exception e ) {
			
			System.out.println ("This palyer cant make move to let another player take and turn");
			game.switchPlayer ();
			
		}
		
		
	}
	
	
}
}