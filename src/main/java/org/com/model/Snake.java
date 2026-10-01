package org.com.model;

import lombok.Data;
import org.com.exceptions.InavalidSnakeException;


@Data
public class Snake {

private int start;
private int end;

public Snake (int start, int end) {
	if ( start < end ) {
		throw new InavalidSnakeException ("Snake start must be greater than the head");
	}
	this.start = start;
	this.end = end;
	
}
}
