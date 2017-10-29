package com.lastbubble.shliktr.domain;

public class Score {

	private final Player player;
	private final int value;
	private final int gamesWon;
	private final int gamesLost;
	private final int tiebreakerDiff;

	public Score(Player player, int value, int gamesWon, int gamesLost, int tiebreakerDiff) {

		this.player = player;
		this.value = value;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
		this.tiebreakerDiff = tiebreakerDiff;
	}

	public Player player() { return player; }

	public int value() { return value; }

	public int gamesWon() { return gamesWon; }

	public int gamesLost() { return gamesLost; }

	public int tiebreakerDiff() { return tiebreakerDiff; }
}
