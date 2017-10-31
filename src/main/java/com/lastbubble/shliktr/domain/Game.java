package com.lastbubble.shliktr.domain;

import java.util.Optional;

public class Game {

	private final int id;
	private final Team awayTeam;
	private final Team homeTeam;

	public Game(int id, Team awayTeam, Team homeTeam) {

		this.id = id;
		this.awayTeam = awayTeam;
		this.homeTeam = homeTeam;
	}

	public int id() { return id; }

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

	public static class Outcome {

		private final Game game;
		private final Optional<Winner> winner;

		public Outcome(Game game, Optional<Winner> winner) {

			this.game = game;
			this.winner = winner;
		}

		public Game game() { return game; }

		public Optional<Winner> winner() { return winner; }
	}
}
