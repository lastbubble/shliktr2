package com.lastbubble.shliktr.domain;

public class Player {

	private final Integer id;
	private final String name;

	public Player(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	public Integer id() { return id; }

	public String name() { return name; }
}
