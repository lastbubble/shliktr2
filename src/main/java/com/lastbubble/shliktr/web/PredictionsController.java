package com.lastbubble.shliktr.web;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PredictionsController {

	private final Supplier<Integer> currentWeekSupplier;

	@Autowired
	public PredictionsController(Supplier<Integer> currentWeekSupplier) { this.currentWeekSupplier = currentWeekSupplier; }

	@RequestMapping("/predictions")
	public String predictions(Model model) {

		return predictions(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/predictions/{week}")
	public String predictions(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		return "pages/predictions";
	}
}
