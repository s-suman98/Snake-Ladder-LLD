package org.com.strategy;

import org.com.entity.Board;

public interface WiningStrategyI {
	boolean hasWon(Board board, int no); //temportray uses the board
}
