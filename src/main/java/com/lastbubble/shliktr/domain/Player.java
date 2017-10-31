package com.lastbubble.shliktr.domain;

import java.util.Objects;

public class Player {

	private final Integer id;
	private final String name;

	public Player(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	public Integer id() { return id; }

	public String name() { return name; }

	@Override public int hashCode() { return Objects.hash(id, name); }

	@Override public boolean equals(Object o) {

		if (o == this) { return true; }

		if (o instanceof Player) {

			Player that = (Player) o;

			return (
				Objects.equals(this.id, that.id) &&
				Objects.equals(this.name, that.name)
			);
		}

		return false;
	}
}
