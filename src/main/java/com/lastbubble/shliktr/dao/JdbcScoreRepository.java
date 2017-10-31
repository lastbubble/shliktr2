package com.lastbubble.shliktr.dao;

import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.Score;
import com.lastbubble.shliktr.domain.ScoreRepository;
import com.lastbubble.shliktr.domain.SeasonScore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcScoreRepository implements ScoreRepository {

	private final Map<Integer, Player> playersById;
	private final JdbcTemplate jdbc;

	public JdbcScoreRepository(Map<Integer, Player> playersById, JdbcTemplate jdbc) {

		this.playersById = playersById;
		this.jdbc = jdbc;
	}

	@Override public List<SeasonScore> findAll() {

		Map<Integer, List<Score>> scoresByPlayer = new HashMap<>();
		playersById.forEach((k, v) -> scoresByPlayer.put(k, new ArrayList<Score>()));

		jdbc.query(
			String.join("",
				"SELECT ",
				"w.id, ",
				"e.player_id, ",
				"e.score, ",
				"e.games_won, ",
				"e.games_lost, ",
				"ABS(e.tiebreaker - w.tiebreaker_answer) AS tiebreaker_diff",
				" FROM ",
				"week w JOIN entry e ON e.week_id = w.id"
			),
			(rs) -> {
				scoresByPlayer.get(rs.getInt("e.player_id")).add(
					new Score(
						rs.getInt("w.id"),
						playersById.get(rs.getInt("e.player_id")),
						rs.getInt("e.score"),
						rs.getInt("e.games_won"),
						rs.getInt("e.games_lost"),
						rs.getInt("tiebreaker_diff")
					)
				);
			}
		);

		return scoresByPlayer.entrySet()
			.stream()
			.filter(e -> e.getValue().size() > 0)
			.map(e -> new SeasonScore(playersById.get(e.getKey()), e.getValue()))
			.sorted(Comparator.comparing(SeasonScore::value).reversed())
			.collect(Collectors.toList());
	}

	@Override public List<Score> findFor(int week) {

		return jdbc.query(
			String.join("",
				"SELECT ",
				"w.id, ",
				"e.player_id, ",
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
					"week w JOIN entry e ON e.week_id = w.id JOIN player p ON e.player_id = p.id, ",
					"pick pk JOIN game g ON g.id = pk.game_id",
				" WHERE ",
					"pk.entry_id = e.id",
						" AND ",
					"w.id = ?",
				" GROUP BY ",
					"e.player_id",
				" ORDER BY ",
					"score DESC, tiebreaker_diff ASC, p.name DESC"
			),
			new Object[] { week },
			(rs, rowNum) ->
				new Score(
					rs.getInt("w.id"),
					playersById.get(rs.getInt("player_id")),
					rs.getInt("score"),
					rs.getInt("won"),
					rs.getInt("lost"),
					rs.getInt("tiebreaker_diff")
				)
		);
	}
}
