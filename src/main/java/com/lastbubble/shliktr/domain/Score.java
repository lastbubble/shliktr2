package com.lastbubble.shliktr.domain;

public class Score {

	private final Player player;
	private final int value;
	private final int gamesWon;
	private final int gamesLost;

	public Score(Player player, int value, int gamesWon, int gamesLost) {

		this.player = player;
		this.value = value;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
	}

	public Player player() { return player; }

	public int value() { return value; }

	public int gamesWon() { return gamesWon; }

	public int gamesLost() { return gamesLost; }
}
