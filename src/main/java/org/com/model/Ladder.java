package org.com.model;

import lombok.Data;
import org.com.exceptions.InavalidSnakeException;

@Data
public class Ladder {

private int start;
private int end;

public Ladder (int start, int end) {
	if ( start > end ) {
		throw new InavalidSnakeException ("Ladder end must be greater than the start");
	}
	this.start = start;
	this.end = end;
	
	
}
}
