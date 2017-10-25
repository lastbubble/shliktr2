package com.lastbubble.shliktr.web;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticsController {

	private final Supplier<Integer> currentWeekSupplier;

	@Autowired
	public StatisticsController(Supplier<Integer> currentWeekSupplier) { this.currentWeekSupplier = currentWeekSupplier; }

	@RequestMapping("/stats")
	public String stats(Model model) {

		return stats(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/stats/{week}")
	public String stats(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		return "pages/stats";
	}
}
