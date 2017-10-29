package com.lastbubble.shliktr;

import com.lastbubble.shliktr.dao.JdbcEntryRepository;
import com.lastbubble.shliktr.dao.JdbcGameRepository;
import com.lastbubble.shliktr.dao.JdbcPlayerRepository;
import com.lastbubble.shliktr.domain.EntryRepository;
import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.GameRepository;
import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.PlayerRepository;
import com.lastbubble.shliktr.domain.Score;
import com.lastbubble.shliktr.domain.Stat;
import com.lastbubble.shliktr.domain.Team;

import java.util.Arrays;
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
	public Function<Integer, List<Game>> gamesSupplier() {

		return (Integer week) -> { return gameRepository().findForWeek(week); };
	}

	@Bean
	public PlayerRepository playerRepository() { return new JdbcPlayerRepository(jdbcTemplate); }

	@Bean
	public EntryRepository entryRepository() { return new JdbcEntryRepository(jdbcTemplate); }

	@Bean
	public Function<Integer, Iterable<Score>> scoresSupplier() {

		return (Integer week) -> {
			return Arrays.asList(
				new Score( new Player(1, "Abcde"), 100, 15, 1),
				new Score( new Player(2, "Fghij"), 90, 14, 2),
				new Score( new Player(3, "Klmno"), 80, 13, 3),
				new Score( new Player(4, "Pqrst"), 70, 12, 4),
				new Score( new Player(5, "Uvwxy"), 60, 11, 5)
			);
		};
	}

	@Bean
	public Function<Integer, Iterable<Stat>> statsSupplier() {

		return (Integer week) -> {
			return Arrays.asList(
				new Stat( new Team("ARI", "Arizona"),
						Arrays.asList( new Stat.Pick(16), new Stat.Pick(15), new Stat.Pick(14))
					),
				new Stat( new Team("ATL", "Atlanta"),
					Arrays.asList( new Stat.Pick(16), new Stat.Pick(15), new Stat.Pick(14))
				),
				new Stat( new Team("BAL", "Baltimore"),
					Arrays.asList( new Stat.Pick(16), new Stat.Pick(15), new Stat.Pick(14))
				),
				new Stat( new Team("CAR", "Carolina"),
					Arrays.asList( new Stat.Pick(16), new Stat.Pick(15), new Stat.Pick(14))
				),
				new Stat( new Team("CHI", "Chicago"),
					Arrays.asList( new Stat.Pick(16), new Stat.Pick(15), new Stat.Pick(14))
				),
				new Stat( new Team("DAL", "Dallas"),
					Arrays.asList( new Stat.Pick(16), new Stat.Pick(15), new Stat.Pick(14))
				),
				new Stat( new Team("DEN", "Denver"),
					Arrays.asList( new Stat.Pick(16), new Stat.Pick(15), new Stat.Pick(14))
				),
				new Stat( new Team("DET", "Detroit"),
					Arrays.asList( new Stat.Pick(16), new Stat.Pick(15), new Stat.Pick(14))
				)
			);
		};
	}
}
