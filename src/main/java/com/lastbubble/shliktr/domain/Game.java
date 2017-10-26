package com.lastbubble.shliktr.domain;

public class Game {

	private final Team awayTeam;
	private final Team homeTeam;
	private final int awayScore;
	private final int homeScore;

	public Game(Team awayTeam, int awayScore, Team homeTeam, int homeScore) {

		this.awayTeam = awayTeam;
		this.awayScore = awayScore;
		this.homeTeam = homeTeam;
		this.homeScore = homeScore;
	}

	public Team awayTeam() { return awayTeam; }

	public Team homeTeam() { return homeTeam; }

	public int awayScore() { return awayScore; }

	public int homeScore() { return homeScore; }
}
