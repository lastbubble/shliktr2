package com.lastbubble.shliktr.dao;

import com.lastbubble.shliktr.domain.Entry;
import com.lastbubble.shliktr.domain.EntryRepository;
import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.Pick;
import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.Winner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcEntryRepository implements EntryRepository {

	private final Map<Integer, Game> gamesById;
	private final Map<Integer, Player> playersById;
	private final JdbcTemplate jdbc;

	public JdbcEntryRepository(
		Map<Integer, Game> gamesById, Map<Integer, Player> playersById, JdbcTemplate jdbc
	) {

		this.gamesById = gamesById;
		this.playersById = playersById;
		this.jdbc = jdbc;
	}

	@Override public List<Entry> findFor(int week) {

		Map<Integer, List<Pick>> picksByPlayer = new HashMap<>();

		jdbc.query(
			"SELECT e.player_id FROM week w JOIN entry e ON e.week_id = w.id WHERE w.id = ?",
			new Object[] { week },
			(rs) -> { picksByPlayer.put(rs.getInt("e.player_id"), new ArrayList<Pick>()); }
		);

		jdbc.query(
			String.join("",
				"SELECT ",
				"e.player_id, p.game_id, p.winner, p.ranking",
				" FROM ",
				"week w JOIN entry e ON e.week_id = w.id JOIN pick p ON p.entry_id = e.id",
				" WHERE ",
				"w.id = ?",
				" ORDER BY ",
				"e.player_id, p.game_id"
			),
			new Object[] { week },
			(rs) -> {
				picksByPlayer.get(rs.getInt("e.player_id")).add(
					new Pick(
						gamesById.get(rs.getInt("p.game_id")),
						"HOME".equals(rs.getString("p.winner")) ? Winner.HOME :
							"AWAY".equals(rs.getString("p.winner")) ? Winner.AWAY :
								null,
						rs.getInt("p.ranking")
					)
				);
			}
		);

		return picksByPlayer.entrySet()
			.stream()
			.map(e -> new Entry(playersById.get(e.getKey()), e.getValue()))
			.sorted(Comparator.comparing(e -> e.player().name()))
			.collect(Collectors.toList());
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
