package com.lastbubble.shliktr.domain;

import java.util.List;

public class Prediction {

	private final Player player;
	private final List<Object> outcomes;
	private final List<Team> mustWins;

	public Prediction(Player player) {

		this.player = player;
		this.outcomes = new java.util.ArrayList<Object>();
		this.mustWins = new java.util.ArrayList<Team>();
	}

	public Player player() { return player; }

	public List<Object> outcomes() { return outcomes; }

	public List<Team> mustWins() { return mustWins; }
}
