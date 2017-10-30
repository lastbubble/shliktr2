package com.lastbubble.shliktr.dao;

import com.lastbubble.shliktr.domain.Entry;
import com.lastbubble.shliktr.domain.EntryRepository;
import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.Pick;
import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.Winner;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcEntryRepository implements EntryRepository {

	private final Map<Integer, Game> gamesById;
	private final JdbcTemplate jdbc;

	public JdbcEntryRepository(Map<Integer, Game> gamesById, JdbcTemplate jdbc) {

		this.gamesById = gamesById;
		this.jdbc = jdbc;
	}

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
				"SELECT game_id, winner, ranking FROM pick WHERE entry_id = ? ORDER BY game_id",
				new Object[] { entryId },
				(rs, rowNum) ->
					new Pick(
						gamesById.get(rs.getInt("game_id")),
						"HOME".equals(rs.getString("winner")) ? Winner.HOME :
							"AWAY".equals(rs.getString("winner")) ? Winner.AWAY :
								null,
						rs.getInt("ranking")
					)
			)
		);
	}
}
