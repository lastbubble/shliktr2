package com.lastbubble.shliktr.domain;

public class Game {

	private final Team awayTeam;
	private final Team homeTeam;

	public Game(Team awayTeam, Team homeTeam) {

		this.awayTeam = awayTeam;
		this.homeTeam = homeTeam;
	}

	public Team awayTeam() { return awayTeam; }

	public Team homeTeam() { return homeTeam; }

	public Score score(int away, int home) { return new Score(this, away, home); }

	public static class Score {

		private final Game game;
		private final int away;
		private final int home;

		private Score(Game game, int away, int home) {

			this.game = game;
			this.away = away;
			this.home = home;
		}

		public Game game() { return game; }

		public int away() { return away; }

		public int home() { return home; }
	}
}
