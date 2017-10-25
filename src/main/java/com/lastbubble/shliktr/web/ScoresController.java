package com.lastbubble.shliktr.web;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScoresController {

	private final Supplier<Integer> currentWeekSupplier;

	@Autowired
	public ScoresController(Supplier<Integer> currentWeekSupplier) { this.currentWeekSupplier = currentWeekSupplier; }

	@RequestMapping("/scores")
	public String scores(Model model) {

		return scores(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/scores/{week}")
	public String scores(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		return "pages/scores";
	}
}
