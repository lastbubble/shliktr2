package com.lastbubble.shliktr.web;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GamesController {

	private final Supplier<Integer> currentWeekSupplier;

	@Autowired
	public GamesController(Supplier<Integer> currentWeekSupplier) { this.currentWeekSupplier = currentWeekSupplier; }

	@RequestMapping("/games")
	public String games(Model model) {

		return games(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/gamees/{week}")
	public String games(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		return "pages/games";
	}
}
