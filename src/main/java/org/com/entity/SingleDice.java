package org.com.entity;

import java.util.Random;

public class SingleDice implements DiceI {
private final Random random = new Random();

public int roll() {
	return random.nextInt(6) + 1;
}
}
