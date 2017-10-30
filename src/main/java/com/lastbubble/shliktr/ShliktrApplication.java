package com.lastbubble.shliktr;

import com.lastbubble.shliktr.dao.JdbcEntryRepository;
import com.lastbubble.shliktr.dao.JdbcGameRepository;
import com.lastbubble.shliktr.dao.JdbcScoreRepository;
import com.lastbubble.shliktr.dao.JdbcStatsSupplier;
import com.lastbubble.shliktr.domain.EntryRepository;
import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.GameRepository;
import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.Score;
import com.lastbubble.shliktr.domain.ScoreRepository;
import com.lastbubble.shliktr.domain.Stat;
import com.lastbubble.shliktr.domain.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ShliktrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShliktrApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Bean
	public Map<Integer, Team> teamsById() {

		Map<Integer, Team> teamsById = new HashMap<>();

		jdbcTemplate.query(
			"SELECT id, abbr, location FROM team",
			(rs) -> {
				teamsById.put(
					rs.getInt("id"),
					new Team(rs.getString("abbr").toUpperCase(), rs.getString("location"))
				);
			}
		);

		return Collections.unmodifiableMap(teamsById);
	}

	@Bean
	public Map<Integer, Game> gamesById() {

		Map<Integer, Game> gamesById = new HashMap<>();

		jdbcTemplate.query(
			"SELECT id, away_team_id, home_team_id FROM game",
			(rs) -> {
				gamesById.put(
					rs.getInt("id"),
					new Game(
						teamsById().get(rs.getInt("away_team_id")),
						teamsById().get(rs.getInt("home_team_id"))
					)
				);
			}
		);

		return Collections.unmodifiableMap(gamesById);
	}

	@Bean
	public Map<Integer, Player> playersById() {

		Map<Integer, Player> playersById = new HashMap<>();

		jdbcTemplate.query(
			"SELECT id, name FROM player",
			(rs) -> {
				playersById.put(
					rs.getInt("id"),
					new Player(rs.getInt("id"), rs.getString("name")));
			}
		);

		return Collections.unmodifiableMap(playersById);
	}

	@Bean
	public GameRepository gameRepository() { return new JdbcGameRepository(gamesById(), jdbcTemplate); }

	@Bean
	public Supplier<Integer> currentWeekSupplier() { return () -> { return 7; }; }

	@Bean
	public Function<Integer, List<Game.Score>> gamesSupplier() {

		return (Integer week) -> { return gameRepository().findScoresForWeek(week); };
	}

	@Bean
	public EntryRepository entryRepository() { return new JdbcEntryRepository(gamesById(), jdbcTemplate); }

	@Bean
	public ScoreRepository scoreRepository() { return new JdbcScoreRepository(playersById(), jdbcTemplate); }

	@Bean
	public Function<Integer, List<Score>> scoresSupplier() {

		return (Integer week) -> { return scoreRepository().findFor(week); };
	}

	@Bean
	public Function<Integer, List<Stat>> statsSupplier() {

		return new JdbcStatsSupplier(teamsById(), jdbcTemplate);
	}
}
