package com.lastbubble.shliktr.domain;

import java.util.List;

public class Entry {

	private final Player player;
	private final List<Pick> picks;

	public Entry(Player player, List<Pick> picks) {

		this.player = player;
		this.picks = picks;
	}

	public Player player() { return player; }

	public List<Pick> picks() { return picks; }
}
