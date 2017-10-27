package com.lastbubble.shliktr.domain;

public class Entry {

	private final Player player;
	private final Iterable<Pick> picks;

	public Entry(Player player, Iterable<Pick> picks) {

		this.player = player;
		this.picks = picks;
	}

	public Player player() { return player; }

	public Iterable<Pick> picks() { return picks; }
}
