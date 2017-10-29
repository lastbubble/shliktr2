package com.lastbubble.shliktr.dao;

import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.Score;
import com.lastbubble.shliktr.domain.ScoreRepository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcScoreRepository implements ScoreRepository {

	private final JdbcTemplate jdbc;

	public JdbcScoreRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

	@Override public List<Score> findFor(int week) {

		return jdbc.query(
			String.join("",
				"SELECT ",
				"p.id, ",
				"p.name, ",
				"SUM(",
					"CASE",
					" WHEN (pk.winner = 'AWAY' AND g.away_score > g.home_score) OR (pk.winner = 'HOME' AND g.home_score > g.away_score) THEN pk.ranking",
					" ELSE 0",
					" END",
				") AS score, ",
				"SUM(",
					"CASE",
					" WHEN (pk.winner = 'AWAY' AND g.away_score > g.home_score) OR (pk.winner = 'HOME' AND g.home_score > g.away_score) THEN 1",
					" ELSE 0",
					" END",
				") AS won, ",
				"SUM(",
					"CASE",
					" WHEN (pk.winner = 'AWAY' AND g.away_score < g.home_score) OR (pk.winner = 'HOME' AND g.home_score < g.away_score) THEN 1",
					" ELSE 0",
					" END",
				") AS lost, ",
				"ABS(e.tiebreaker - w.tiebreaker_answer) AS tiebreaker_diff",
				" FROM ",
					"week w JOIN entry e ON e.week_id = w.id JOIN player p ON p.id = e.player_id, ",
					"pick pk JOIN game g ON g.id = pk.game_id",
				" WHERE ",
					"pk.entry_id = e.id",
						" AND ",
					"w.id = ?",
				" GROUP BY ",
					"p.id",
				" ORDER BY ",
					"score DESC, tiebreaker_diff ASC, p.name DESC"
			),
			new Object[] { week },
			(rs, rowNum) ->
				new Score(
					new Player(rs.getInt("p.id"), rs.getString("p.name")),
					rs.getInt("score"),
					rs.getInt("won"),
					rs.getInt("lost"),
					rs.getInt("tiebreaker_diff")
				)
		);
	}
}
