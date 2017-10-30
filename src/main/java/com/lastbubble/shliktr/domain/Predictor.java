package com.lastbubble.shliktr.domain;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Predictor implements Function<Integer, List<Prediction>> {

	private final EntryRepository entryRepository;

	public Predictor(EntryRepository entryRepository) {

		this.entryRepository = entryRepository;
	}

	@Override public List<Prediction> apply(Integer week) {

		return entryRepository.findFor(week)
			.stream()
			.map(e -> new Prediction(e.player()))
			.collect(Collectors.toList());
	}
}
