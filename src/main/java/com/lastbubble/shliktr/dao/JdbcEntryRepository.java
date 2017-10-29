package com.lastbubble.shliktr.dao;

import com.lastbubble.shliktr.domain.Entry;
import com.lastbubble.shliktr.domain.EntryRepository;
import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.Pick;
import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.Team;
import com.lastbubble.shliktr.domain.Winner;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcEntryRepository implements EntryRepository {

	private final JdbcTemplate jdbc;

	public JdbcEntryRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

	@Override public Entry findFor(int week, Player player) {

		Integer entryId = jdbc.query(
			"SELECT id FROM entry where week_id = ? AND player_id = ?",
			new Object[] { week, player.id() },
			(rs, rowNum) -> rs.getInt("id")
		).stream().findFirst().orElse(null);

		if (entryId == null) { return null; }

		return new Entry(
			player,
			jdbc.query(
				String.join("",
					"SELECT ",
					"a.abbr, a.location, g.away_score, h.abbr, h.location, g.home_score, ",
					"p.winner, p.ranking",
					" FROM ",
					"pick p JOIN game g ON p.game_id = g.id, team a, team h",
					" WHERE ",
					"p.entry_id = ?",
					" AND ",
					"a.id = g.away_team_id",
					" AND ",
					"h.id = g.home_team_id",
					" ORDER BY ",
					"g.id"
				),
				new Object[] { entryId },
				(rs, rowNum) ->
					new Pick(
						new Game(
							new Team(rs.getString("a.abbr").toUpperCase(), rs.getString("a.location")),
							new Team(rs.getString("h.abbr").toUpperCase(), rs.getString("h.location"))
						),
						"HOME".equals(rs.getString("p.winner")) ? Winner.HOME : Winner.AWAY,
						rs.getInt("p.ranking")
					)
			)
		);
	}
}
