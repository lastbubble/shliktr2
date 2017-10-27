package com.lastbubble.shliktr.domain;

public class Pick {

	private final Game game;
	private final Winner winner;
	private final int ranking;

	public Pick(Game game, Winner winner, int ranking) {

		this.game = game;
		this.winner = winner;
		this.ranking = ranking;
	}

	public Game game() { return game; }

	public Winner winner() { return winner; }

	public int ranking() { return ranking; }
}
