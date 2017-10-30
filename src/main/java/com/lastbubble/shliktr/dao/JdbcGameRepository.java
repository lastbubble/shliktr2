package com.lastbubble.shliktr.dao;

import static com.lastbubble.shliktr.domain.Game.Score;

import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.GameRepository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcGameRepository implements GameRepository {

	private final Map<Integer, Game> gamesById;
	private final JdbcTemplate jdbc;

	public JdbcGameRepository(Map<Integer, Game> gamesById, JdbcTemplate jdbc) {

		this.gamesById = gamesById;
		this.jdbc = jdbc;
	}

	@Override public List<Score> findScoresForWeek(int week) {

		return jdbc.query(
			"SELECT id, away_score, home_score FROM game WHERE week_id = ?",
			new Object[] { week },
			(rs, rowNum) ->
				gamesById
					.get(rs.getInt("id"))
					.score(rs.getInt("away_score"), rs.getInt("home_score"))
		);
	}
}
