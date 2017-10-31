package com.lastbubble.shliktr.domain;

import static com.lastbubble.shliktr.domain.Game.Outcome;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Predictor implements Function<Integer, List<Prediction>> {

	private final GameRepository gameRepository;
	private final EntryRepository entryRepository;

	public Predictor(GameRepository gameRepository, EntryRepository entryRepository) {

		this.gameRepository = gameRepository;
		this.entryRepository = entryRepository;
	}

	@Override public List<Prediction> apply(Integer week) {

		List<Entry> entries = entryRepository.findFor(week);

		Map<Player, List<List<Outcome>>> winningOutcomesByPlayer = new HashMap<>();
		entries.stream()
			.forEach(e -> winningOutcomesByPlayer.put(e.player(), new ArrayList<List<Outcome>>()));

		List<Outcome> outcomes = gameRepository.findOutcomesForWeek(week);

		List<Outcome> fixedOutcomes = outcomes.stream()
			.filter(o -> o.winner().isPresent())
			.collect(Collectors.toList());

		List<Function<List<Outcome>, Score>> scoreGenerators = entries.stream()
			.map(e -> ScoreGenerator.forEntry(e, fixedOutcomes))
			.collect(Collectors.toList());

		List<Outcome> variableOutcomes = outcomes.stream()
			.filter(o -> !o.winner().isPresent())
			.collect(Collectors.toList());

		int incompleteCnt = variableOutcomes.size();

		BitSet bits = new BitSet(incompleteCnt);

		for (int value = 0, max = (int) Math.pow(2, incompleteCnt); value < max; value++) {

			setBits(bits, value);

			AtomicInteger i = new AtomicInteger(0);

			List<Outcome> possibleOutcomes = variableOutcomes.stream()
				.map(o ->
					new Outcome(
						o.game(),
						Optional.of(bits.get(i.getAndIncrement()) ? Winner.HOME : Winner.AWAY)
					)
				)
				.collect(Collectors.toList());

			List<Score> scores = scoreGenerators.stream()
				.map(g -> g.apply(possibleOutcomes))
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());

			if (scores.size() > 0) {

				Score winningScore = scores.get(0);

				scores.stream()
					.filter(s -> s.value() >= winningScore.value())
					.forEach(s -> winningOutcomesByPlayer.get(s.player()).add(possibleOutcomes));
			}
		}

		return winningOutcomesByPlayer.entrySet()
			.stream()
			.filter(e -> e.getValue().isEmpty() == false)
			.map(e -> Prediction.forPlayerAndOutcomes(e.getKey(), e.getValue()))
			.sorted()
			.collect(Collectors.toList());
	}

	private static void setBits(BitSet bits, int n) {

		bits.clear();

		int i = 0;

		while (n != 0) {

			if (n % 2 != 0) { bits.set(i); }

			i++;

			n = n >>> 1;
		}
	}

	private static class Score implements Comparable<Score> {

		private final Player player;
		private final int value;

		public Score(Player player, int value) {

			this.player = player;
			this.value = value;
		}

		public Player player() { return player; }

		public int value() { return value; }

		@Override public int compareTo(Score that) { return this.value - that.value; }
	}

	private static class ScoreGenerator implements Function<List<Outcome>, Score> {

		private static ScoreGenerator forEntry(Entry entry, List<Outcome> fixedOutcomes) {

			Map<Integer, Pick> picksByGame = new HashMap<>();

			entry.picks().forEach(p -> { picksByGame.put(p.game().id(), p); });
			
			return new ScoreGenerator(
				entry.player(),
				Collections.unmodifiableMap(picksByGame),
				computeScore(picksByGame, fixedOutcomes)
			);
		}

		private static int computeScore(Map<Integer, Pick> picksByGame, List<Outcome> outcomes) {

			AtomicInteger score = new AtomicInteger(0);

			outcomes.stream().forEach(o -> {

				Pick pick = picksByGame.get(o.game().id());

				if (pick != null && pick.winner() == o.winner().orElse(null)) {

					score.addAndGet(pick.ranking());
				}

			});

			return score.get();
		}

		private final Player player;
		private final Map<Integer, Pick> picksByGame;
		private final int initialScore;

		private ScoreGenerator(
			Player player, Map<Integer, Pick> picksByGame, int initialScore
		) {

			this.player = player;
			this.picksByGame = picksByGame;
			this.initialScore = initialScore;
		}

		@Override public Score apply(List<Outcome> outcomes) {

			return new Score(player, initialScore + computeScore(picksByGame, outcomes));
		}
	}
}
