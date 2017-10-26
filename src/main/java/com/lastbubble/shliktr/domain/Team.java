package com.lastbubble.shliktr.domain;

public class Team {

	private final String abbr;
	private final String location;

	public Team(String abbr, String location) {

		this.abbr = abbr;
		this.location = location;
	}

	public String abbr() { return abbr; }

	public String location() { return location; }
}
