package com.lastbubble.shliktr.web;

import com.lastbubble.shliktr.domain.Entry;
import com.lastbubble.shliktr.domain.EntryRepository;
import com.lastbubble.shliktr.domain.Player;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EntryController {

	private final Map<Integer, Player> playersById;
	private final Supplier<Integer> currentWeekSupplier;
	private final EntryRepository entryRepository;

	@Autowired
	public EntryController(
		Map<Integer, Player> playersById,
		Supplier<Integer> currentWeekSupplier,
		EntryRepository entryRepository
	) {

		this.playersById = playersById;
		this.currentWeekSupplier = currentWeekSupplier;
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

		Player player = (playerId != null) ? playersById.get(playerId) : null;

		Entry entry = (player != null) ? entryRepository.findFor(week, player) : null;

		model.addAttribute("week", week);
		model.addAttribute("players",
			playersById.values()
				.stream()
				.sorted(Comparator.comparing(p -> p.name()))
				.collect(Collectors.toList())
		);
		model.addAttribute("player", player);
		model.addAttribute("entry", entry);
		return "pages/view-entry";
	}
}
