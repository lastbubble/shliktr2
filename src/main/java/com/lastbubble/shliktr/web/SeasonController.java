package com.lastbubble.shliktr.web;

import com.lastbubble.shliktr.domain.Score;
import com.lastbubble.shliktr.domain.SeasonScore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeasonController {

	private final Supplier<List<SeasonScore>> scoresSupplier;

	public SeasonController(Supplier<List<SeasonScore>> scoresSupplier) {

		this.scoresSupplier = scoresSupplier;
	}

	@RequestMapping("/season")
	public String season() { return "pages/season"; }

	@RequestMapping("/season.json")
	public @ResponseBody List<Map<String, Object>> seasonJson() {

		List<Map<String, Object>> listOfScoreMaps = new ArrayList<>();

		for (SeasonScore score : scoresSupplier.get()) { listOfScoreMaps.add(scoreToMap(score)); }

		return listOfScoreMaps;
	}

	private static Map<String, Object> scoreToMap(SeasonScore score) {

		Map<String, Object> m = new HashMap<>();

		m.put("name", score.player().name());

		m.put("score", score.value());

		m.put("total", score.total());

		for (int i = 0; i < 17; i++) {

			Score weeklyScore = score.scoreAt(i);

			if (weeklyScore != null) { m.put("score" + (i + 1), weeklyScore.value()); }
		}

		return m;
	}
}
