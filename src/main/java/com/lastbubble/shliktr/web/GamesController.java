package com.lastbubble.shliktr.web;

import com.lastbubble.shliktr.domain.Game;

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
	private final Function<Integer, List<Game>> gamesSupplier;

	@Autowired
	public GamesController(
		Supplier<Integer> currentWeekSupplier,
		Function<Integer, List<Game>> gamesSupplier
	) {

		this.currentWeekSupplier = currentWeekSupplier;
		this.gamesSupplier = gamesSupplier;
	}

	@RequestMapping("/games")
	public String games(Model model) {

		return games(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/games/{week}")
	public String games(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		model.addAttribute("games", gamesSupplier.apply(week));
		return "pages/games";
	}
}
