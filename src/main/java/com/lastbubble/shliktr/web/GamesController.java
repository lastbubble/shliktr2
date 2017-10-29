package com.lastbubble.shliktr.web;

import static com.lastbubble.shliktr.domain.Game.Score;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GamesController {

	private final Supplier<Integer> currentWeekSupplier;
	private final Function<Integer, List<Score>> scoresSupplier;

	@Autowired
	public GamesController(
		Supplier<Integer> currentWeekSupplier,
		Function<Integer, List<Score>> scoresSupplier
	) {

		this.currentWeekSupplier = currentWeekSupplier;
		this.scoresSupplier = scoresSupplier;
	}

	@RequestMapping("/games")
	public String games(Model model) {

		return games(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/games/{week}")
	public String games(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		model.addAttribute("scores", scoresSupplier.apply(week));
		return "pages/games";
	}
}
