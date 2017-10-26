package com.lastbubble.shliktr.domain;

public class Entry {

	private final Player player;
	private final int score;
	private final int gamesWon;
	private final int gamesLost;

	public Entry(Player player, int score, int gamesWon, int gamesLost) {

		this.player = player;
		this.score = score;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
	}

	public Player player() { return player; }

	public int score() { return score; }

	public int gamesWon() { return gamesWon; }

	public int gamesLost() { return gamesLost; }
}
