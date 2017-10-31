package com.lastbubble.shliktr.dao;

import static com.lastbubble.shliktr.domain.Game.Outcome;
import static com.lastbubble.shliktr.domain.Game.Score;

import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.GameRepository;
import com.lastbubble.shliktr.domain.Winner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	@Override public List<Outcome> findOutcomesForWeek(int week) {

		return jdbc.query(
			String.join("",
				"SELECT ",
				"g.id,",
				"CASE",
				" WHEN complete AND away_score > home_score THEN 'AWAY'",
				" WHEN complete AND home_score > away_score THEN 'HOME'",
				" WHEN complete AND away_score = home_score THEN 'TIED'",
				" ELSE NULL",
				" END AS winner",
				" FROM ",
				"week w JOIN game g ON g.week_id = w.id",
				" WHERE ",
				" w.id = ?",
				" ORDER BY ",
				"g.id"
			),
			new Object[] { week },
			(rs, rowNum) ->
				new Outcome(
					gamesById.get(rs.getInt("g.id")),
					Optional
						.ofNullable(rs.getString("winner"))
						.map(s -> Enum.valueOf(Winner.class, s))
				)
		);
	}
}
