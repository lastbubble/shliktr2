package com.lastbubble.shliktr.domain;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SeasonScore {

	private final Player player;
	private final List<Score> weeklyScores;
	private final int value;
	private final int total;

	public SeasonScore(Player player, List<Score> weeklyScores) {

		this.player = player;
		this.weeklyScores = weeklyScores.stream()
			.sorted(Comparator.comparing(Score::value).reversed())
			.collect(Collectors.toList());

		int runningValue = 0;
		int runningTotal = 0;

		for (int i = 0; i < weeklyScores.size(); i++) {

			Score score = weeklyScores.get(i);

			if (i < 14) { runningValue += score.value(); }

			runningTotal += score.value();
		}

		this.value = runningValue;
		this.total = runningTotal;
	}

	public Player player() { return player; }

	public int value() { return value; }

	public int total() { return total; }

	public Score scoreAt(int i) { return (i < weeklyScores.size()) ? weeklyScores.get(i) : null; }
}
