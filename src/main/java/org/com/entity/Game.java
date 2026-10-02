
package org.com.entity;

import lombok.Builder;
import lombok.Data;
import org.com.enums.GameStatus;
import org.com.exceptions.GameAlredyCompletedException;
import org.com.exceptions.InvalidMoveException;
import org.com.exceptions.NotSufficientPlayerException;
import org.com.model.Player;
import org.com.model.Position;
import org.com.strategy.WiningStrategyI;

import java.util.List;

@Data
@Builder
public class Game {

private Board board;                    // Owns the board
private List< Player > players;           // Manages players
private GameStatus gameStatus;           // Current game status
private WiningStrategyI winingStrategy;  // Checks winning condition
private int currentPlayerTurn;
private DiceI dice;
private Player winnigPlayer;

// State related to the current turn
private Position turnStartPosition;
private int consecutiveSixes;


private void switchPlayer () {
	int next = (currentPlayerTurn + 1) % players.size ();
	currentPlayerTurn = next;
}


//JUST CHECK SUFFICENT PLAYER AND THE STARTS THE GAME
public void start () {
	
	if ( players.size () < 2 ) {
		throw new NotSufficientPlayerException (
				"At least 2 players are required"
		);
	}
	
	while ( gameStatus != GameStatus.COMPLETED ) {
		System.out.println ();
		System.out.println ();
		System.out.println ("Next Player Turn @@@@@@@@");
		
		playTurn ();
	}
	
	
}


private void playTurn () {
	
	// get the current player
	
	Player current = players.get (currentPlayerTurn);
	
	
	//save the state of current player
	turnStartPosition = current.getPosition ();
	consecutiveSixes = 0;
	
	
	// while()
	
	while ( GameStatus.COMPLETED != gameStatus ) {
		
		//roll a dice
		int diceValue = dice.roll ();
		
		System.out.println ("Dice gave "+diceValue);
		
		// use the single move
		try {
			makeMove (diceValue);
		} catch ( Exception e ) {
			switchPlayer ();
			return;
		}
		
		if(gameStatus==GameStatus.COMPLETED){
			return;
		}
		
		if( !isExtraTurn (diceValue) ){
			this.switchPlayer ();
			return;
			
		}
		
		System.out.println ("Extra Chance to roll ");
		
		
	}
}


private void undoCurrentPlayerScore () {
	this.consecutiveSixes = 0;
	players.get (currentPlayerTurn).setPosition (turnStartPosition);
	turnStartPosition = null;
}


private boolean isExtraTurn (int diceValue) {
	
	if ( diceValue != 6 ) {
		return false;
	}
	
	consecutiveSixes++;
	
	if ( consecutiveSixes == 2 ||consecutiveSixes==1 ) {
		return true;
	}
	
	if ( consecutiveSixes == 3 ) {
		System.out.println ("XXX PENALITY");
		undoCurrentPlayerScore ();
	}
	
	return false;
	
	
}


public GameStatus makeMove (int diceValue) {
	
	if ( gameStatus == GameStatus.COMPLETED ) {
		throw new GameAlredyCompletedException (
				"Game has already finished"
		);
	}
	
	Player currentPlayer = players.get (currentPlayerTurn);
	
	int currentPosition =
			currentPlayer.getPosition ().getNo ();
	
	int targetPosition =
			currentPosition + diceValue;
	
	// Validate move
	if ( !board.isValid (targetPosition) ) {
		throw new InvalidMoveException (
				"Invalid move: position is outside the board"
		);
	}
	
	// Apply snake/ladder
	int newPosition = board.apply (targetPosition);
	
	currentPlayer.setPosition (
			new Position (newPosition)
	);
	
	System.out.println (
			"Player " +
					currentPlayer.getName () +
					" moved to " +
					newPosition
	);
	
	// Check winner
	if ( winingStrategy.hasWon (board, newPosition) ) {
		
		System.out.println (
				"Game Over! " +
						currentPlayer.getName () +
						" won."
		);
		
		winnigPlayer = currentPlayer;
		gameStatus = GameStatus.COMPLETED;
	}
	
	return gameStatus;
}
}

