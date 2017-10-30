package com.lastbubble.shliktr;

import com.lastbubble.shliktr.dao.JdbcEntryRepository;
import com.lastbubble.shliktr.dao.JdbcGameRepository;
import com.lastbubble.shliktr.dao.JdbcPlayerRepository;
import com.lastbubble.shliktr.dao.JdbcScoreRepository;
import com.lastbubble.shliktr.dao.JdbcStatsSupplier;
import com.lastbubble.shliktr.domain.EntryRepository;
import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.GameRepository;
import com.lastbubble.shliktr.domain.PlayerRepository;
import com.lastbubble.shliktr.domain.Score;
import com.lastbubble.shliktr.domain.ScoreRepository;
import com.lastbubble.shliktr.domain.Stat;

import java.util.List;
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
	public GameRepository gameRepository() { return new JdbcGameRepository(jdbcTemplate); }

	@Bean
	public Supplier<Integer> currentWeekSupplier() { return () -> { return 7; }; }

	@Bean
	public Function<Integer, List<Game.Score>> gamesSupplier() {

		return (Integer week) -> { return gameRepository().findScoresForWeek(week); };
	}

	@Bean
	public PlayerRepository playerRepository() { return new JdbcPlayerRepository(jdbcTemplate); }

	@Bean
	public EntryRepository entryRepository() { return new JdbcEntryRepository(jdbcTemplate); }

	@Bean
	public ScoreRepository scoreRepository() { return new JdbcScoreRepository(jdbcTemplate); }

	@Bean
	public Function<Integer, List<Score>> scoresSupplier() {

		return (Integer week) -> { return scoreRepository().findFor(week); };
	}

	@Bean
	public Function<Integer, List<Stat>> statsSupplier() { return new JdbcStatsSupplier(jdbcTemplate); }
}
