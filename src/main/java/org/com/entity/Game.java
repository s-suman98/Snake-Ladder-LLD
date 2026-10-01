package org.com.entity;


import lombok.Builder;
import lombok.Data;
import org.com.enums.GameStatus;
import org.com.exceptions.GameAlredyCompletedException;
import org.com.exceptions.InvalidMoveException;
import org.com.model.Player;
import org.com.model.Position;
import org.com.strategy.WiningStrategyI;

import java.util.List;

@Data
@Builder
public class Game {

private Board board; //owns board

private List< Player > players; //manages player

private GameStatus gameStatus;  //Knows about the gae status

private WiningStrategyI winingStrategy;   //Uses this to temp cehck the winner

private int currentPlayerTurn;

private Player winnigPlayer;


public void switchPlayer () {
	int currindex = (currentPlayerTurn + 1) % players.size ();
	this.currentPlayerTurn = currindex;
	
	
}


public GameStatus makeMove (int dicevalue) {
	
	if ( gameStatus == GameStatus.COMPLETED ) {
		throw new GameAlredyCompletedException ("Game has already finished");
	}
	
	
	Player currPlayer = players.get (currentPlayerTurn);
	int currPos = currPlayer.getPosition ().getNo ();
	
	if ( board.isValid (currPos + dicevalue) == false ) {
		throw new InvalidMoveException ("out of cell move for this palyer");
	}
	
	
	// make a move
	
	//check if 6 then give one more chance
	
	
	// apply snake of ladder
	
	int newPos = board.apply (currPos+dicevalue);
	
	System.out.println ("Player" + currPlayer.getName () + " Made a move to" + newPos);
	currPlayer.setPosition (new Position (newPos));
	
	
	
	// check if won
	
	if ( winingStrategy.hasWon (this.board, newPos) ) {
		System.out.println ("Game OVER " + currPlayer.getName () + " WON");
		
		this.winnigPlayer = currPlayer;
		this.gameStatus = GameStatus.COMPLETED;
		return GameStatus.COMPLETED;
	}
	
	switchPlayer ();
	
	
	return GameStatus.IN_PROGRESS;
	
	
}


}
