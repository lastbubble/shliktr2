package com.lastbubble.shliktr.domain;

public class Stat {

	private final Team team;
	private final Iterable<Pick> picks;

	public Stat(Team team, Iterable<Pick> picks) {

		this.team = team;
		this.picks = picks;
	}

	public Team team() { return team; }

	public Iterable<Pick> picks() { return picks; }

	public static class Pick {

		private final int ranking;

		public Pick(int ranking) { this.ranking = ranking; }

		public int ranking() { return ranking; }
	}
}
