package com.lastbubble.shliktr.web;

import com.lastbubble.shliktr.domain.Stat;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticsController {

	private final Supplier<Integer> currentWeekSupplier;
	private final Function<Integer, List<Stat>> statsSupplier;

	@Autowired
	public StatisticsController(
		Supplier<Integer> currentWeekSupplier,
		Function<Integer, List<Stat>> statsSupplier
	) {

		this.currentWeekSupplier = currentWeekSupplier;
		this.statsSupplier = statsSupplier;
	}

	@RequestMapping("/stats")
	public String stats(Model model) {

		return stats(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/stats/{week}")
	public String stats(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		model.addAttribute("stats", statsSupplier.apply(week));
		return "pages/stats";
	}
}
