package org.com.entity;


import lombok.Data;
import lombok.Setter;
import org.com.model.Ladder;
import org.com.model.Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Setter
public class Board {

private final int size;

public Board(int size) {
	this.size = size;
	this.snakes = new ArrayList<> ();
	this.ladders = new ArrayList<>();
}

List< Snake > snakes;

List< Ladder > ladders;

public boolean isValid (int cr) {
	if ( cr < 0 || cr > this.size )
		return false;
	return true;
}


public int apply(int currentPosition) {
	
	
	int position = snakes.stream()
			               .filter(snake -> snake.getStart() == currentPosition)
			               .map(Snake::getEnd)
			               .findFirst()
			               .orElse(currentPosition);
	

	return ladders.stream()
			       .filter(ladder -> ladder.getStart() == position)
			       .map(Ladder::getEnd)
			       .findFirst()
			       .orElse(position);
}

}
