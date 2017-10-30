package com.lastbubble.shliktr.dao;

import static com.lastbubble.shliktr.domain.Stat.Pick;

import com.lastbubble.shliktr.domain.Stat;
import com.lastbubble.shliktr.domain.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

public class JdbcStatsSupplier implements Function<Integer, List<Stat>> {

	private final JdbcTemplate jdbc;

	public JdbcStatsSupplier(JdbcTemplate jdbc) { this.jdbc = jdbc; }

	@Override public List<Stat> apply(Integer week) {

		Map<Integer, Team> teamsById = new HashMap<>();

		jdbc.query(
			"SELECT id, abbr, location FROM team",
			(RowCallbackHandler) (rs) -> {
				teamsById.put(rs.getInt("id"), new Team(rs.getString("abbr"), rs.getString("location")));
			}
		);

		Map<Integer, List<Pick>> picksByTeam = new HashMap<>();
		teamsById.forEach((k, v) -> picksByTeam.put(k, new ArrayList<Pick>()));

		jdbc.query(
			String.join("",
				"SELECT ",
				"t.id, p.ranking",
				" FROM ",
				"week w JOIN game g ON w.id = g.week_id JOIN pick p ON g.id = p.game_id, team t",
				" WHERE ",
				"w.id = ?",
				" AND ",
				"((p.winner = 'HOME' AND t.id = g.home_team_id) OR (p.winner = 'AWAY' AND t.id = g.away_team_id))",
				" ORDER BY ",
				"t.id, p.ranking DESC"
			),
			new Object[] { week },
			(RowCallbackHandler) (rs) -> {
				picksByTeam.get(rs.getInt("t.id")).add( new Pick(rs.getInt("p.ranking")));
			}
		);

		return picksByTeam.entrySet()
			.stream()
			.filter(e -> e.getValue().size() > 0)
			.sorted(Comparator.comparing(e -> e.getValue().size()))
			.map(e -> new Stat(teamsById.get(e.getKey()), e.getValue()))
			.collect(Collectors.toList());
	}
}
