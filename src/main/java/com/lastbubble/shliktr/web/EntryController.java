package com.lastbubble.shliktr.web;

import com.lastbubble.shliktr.domain.Entry;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EntryController {

	private final Supplier<Integer> currentWeekSupplier;

	@Autowired
	public EntryController(Supplier<Integer> currentWeekSupplier) {

		this.currentWeekSupplier = currentWeekSupplier;
	}

	@RequestMapping("/entries")
	public String entry(Model model) {

		return entry(currentWeekSupplier.get(), model);
	}

	@RequestMapping("/entries/{week}")
	public String entry(@PathVariable Integer week, Model model) {

		return entry(week, null, model);
	}

	@RequestMapping("/entries/{week}/player/{player}")
	public String entry(@PathVariable Integer week, @PathVariable Integer playerId, Model model) {

		model.addAttribute("week", week);
		return "pages/entry";
	}
}
