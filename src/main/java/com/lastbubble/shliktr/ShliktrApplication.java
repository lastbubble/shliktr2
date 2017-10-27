package com.lastbubble.shliktr;

import com.lastbubble.shliktr.domain.Entry;
import com.lastbubble.shliktr.domain.Game;
import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.Stat;
import com.lastbubble.shliktr.domain.Team;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShliktrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShliktrApplication.class, args);
	}

	@Bean
	public Supplier<Integer> currentWeekSupplier() { return () -> { return 7; }; }

	@Bean
	public Function<Integer, Iterable<Game>> gamesSupplier() {

		return (Integer week) -> {
			return Arrays.asList(
				new Game( new Team("ARI", "Arizona"), 24, new Team("WAS", "Washington"), 17),
				new Game( new Team("ATL", "Atlanta"), 24, new Team("TEN", "Tennessee"), 17),
				new Game( new Team("BAL", "Baltimore"), 24, new Team("SF", "San Francisco"), 17),
				new Game( new Team("CAR", "Carolina"), 24, new Team("SEA", "Seattle"), 17),
				new Game( new Team("CHI", "Chicago"), 24, new Team("PIT", "Pittsburgh"), 17),
				new Game( new Team("DAL", "Dallas"), 24, new Team("PHI", "Philadelphia"), 17),
				new Game( new Team("DEN", "Denver"), 24, new Team("NYJ", "N.Y. Jets"), 17),
				new Game( new Team("DET", "Detroit"), 24, new Team("NYG", "N.Y. Giants"), 17)
			);
		};
	}

	@Bean
	public Function<Integer, Iterable<Entry>> entriesSupplier() {

		return (Integer week) -> {
			return Arrays.asList(
				new Entry( new Player("Abcde"), 100, 15, 1),
				new Entry( new Player("Fghij"), 90, 14, 2),
				new Entry( new Player("Klmno"), 80, 13, 3),
				new Entry( new Player("Pqrst"), 70, 12, 4),
				new Entry( new Player("Uvwxy"), 60, 11, 5)
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
