package com.lastbubble.shliktr.web;

import com.lastbubble.shliktr.domain.Score;

import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScoresController {

	private final Supplier<Integer> currentWeekSupplier;
	private final Function<Integer, Iterable<Score>> scoresSupplier;

	@Autowired
	public ScoresController(
		Supplier<Integer> currentWeekSupplier,
		Function<Integer, Iterable<Score>> scoresSupplier
	) {

		this.currentWeekSupplier = currentWeekSupplier;
		this.scoresSupplier = scoresSupplier;
	}

	@RequestMapping("/scores")
	public String scores(Model model) {

		return scores(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/scores/{week}")
	public String scores(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		model.addAttribute("scores", scoresSupplier.apply(week));
		return "pages/scores";
	}
}
