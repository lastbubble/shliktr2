package com.lastbubble.shliktr.web;

import com.lastbubble.shliktr.domain.Prediction;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PredictionsController {

	private final Supplier<Integer> currentWeekSupplier;
	private final Function<Integer, List<Prediction>> predictionsSupplier;

	@Autowired
	public PredictionsController(
		Supplier<Integer> currentWeekSupplier,
		Function<Integer, List<Prediction>> predictionsSupplier
	) {

		this.currentWeekSupplier = currentWeekSupplier;
		this.predictionsSupplier = predictionsSupplier;
	}

	@RequestMapping("/predictions")
	public String predictions(Model model) {

		return predictions(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/predictions/{week}")
	public String predictions(@PathVariable Integer week, Model model) {

		model.addAttribute("week", week);
		model.addAttribute("predictions", predictionsSupplier.apply(week));
		return "pages/predictions";
	}
}
