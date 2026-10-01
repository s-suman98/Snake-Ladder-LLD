package org.com.strategy;

import org.com.entity.Board;

public class SimpleWinStrategy implements WiningStrategyI {
@Override
public boolean hasWon (Board board, int position) {
	 if(board.getSize ()==position) return true;
	 
	 return false;
}
}
