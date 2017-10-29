package com.lastbubble.shliktr.dao;

import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.PlayerRepository;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcPlayerRepository implements PlayerRepository {

	private final JdbcTemplate jdbc;

	public JdbcPlayerRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

	@Override public Iterable<Player> findAll() {

		return jdbc.query(
			String.join("",
				"SELECT ",
				"id, name",
				" FROM ",
				"player",
				" ORDER BY ",
				"name"
			),
			(rs, rowNum) ->
				new Player(
					rs.getInt("id"),
					rs.getString("name")
				)
		);
	}

	@Override public Player findById(int id) {

		return jdbc.query(
			String.join("",
				"SELECT ",
				"id, name",
				" FROM ",
				"player",
				" WHERE ",
				"id = ?"
			),
			new Object[] { id },
			(rs, rowNum) ->
				new Player(
					rs.getInt("id"),
					rs.getString("name")
				)
		).stream().findFirst().orElse(null);
	}
}
