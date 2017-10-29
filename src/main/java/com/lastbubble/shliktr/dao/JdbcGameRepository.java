package com.lastbubble.shliktr.dao;

import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.GameRepository;
import com.lastbubble.shliktr.domain.Team;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcGameRepository implements GameRepository {

	private final JdbcTemplate jdbc;

	public JdbcGameRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

	@Override public List<Game> findForWeek(int week) {

		return jdbc.query(
			String.join("",
				"SELECT ",
				"a.abbr, a.location, g.away_score, h.abbr, h.location, g.home_score",
				" FROM ",
				"game g, team a, team h",
				" WHERE ",
				"g.week_id = ?",
				" AND ",
				"a.id = g.away_team_id",
				" AND ",
				"h.id = g.home_team_id"
			),
			new Object[] { week },
			(rs, rowNum) ->
				new Game(
					new Team(rs.getString("a.abbr").toUpperCase(), rs.getString("a.location")),
					rs.getInt("g.away_score"),
					new Team(rs.getString("h.abbr").toUpperCase(), rs.getString("h.location")),
					rs.getInt("g.home_score")
				)
		);
	}
}
