package com.lastbubble.shliktr;

import com.lastbubble.shliktr.domain.Game;
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
}
