package com.lastbubble.shliktr.web;

import com.lastbubble.shliktr.domain.Entry;
import com.lastbubble.shliktr.domain.EntryRepository;
import com.lastbubble.shliktr.domain.Player;
import com.lastbubble.shliktr.domain.PlayerRepository;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EntryController {

	private final Supplier<Integer> currentWeekSupplier;
	private final PlayerRepository playerRepository;
	private final EntryRepository entryRepository;

	@Autowired
	public EntryController(
		Supplier<Integer> currentWeekSupplier,
		PlayerRepository playerRepository,
		EntryRepository entryRepository
	) {

		this.currentWeekSupplier = currentWeekSupplier;
		this.playerRepository = playerRepository;
		this.entryRepository = entryRepository;
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
	public String entry(@PathVariable Integer week, @PathVariable("player") Integer playerId, Model model) {

		Player player = (playerId != null) ? playerRepository.findById(playerId) : null;

		Entry entry = (player != null) ? entryRepository.findFor(week, player) : null;

		model.addAttribute("week", week);
		model.addAttribute("players", playerRepository.findAll());
		model.addAttribute("player", player);
		model.addAttribute("entry", entry);
		return "pages/view-entry";
	}
}
