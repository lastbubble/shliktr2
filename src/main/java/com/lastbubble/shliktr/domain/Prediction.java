package com.lastbubble.shliktr.domain;

import static com.lastbubble.shliktr.domain.Game.Outcome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Prediction implements Comparable<Prediction> {

	public static Prediction forPlayerAndOutcomes(
		Player player, List<List<Outcome>> outcomes
	) {

		List<Team> mustWins = new ArrayList<Team>();

		if (outcomes.size() > 0) {

			Outcome[] mustOutcomes = outcomes.get(0).toArray( new Outcome[0]);

			for (List<Outcome> o : outcomes) {

				boolean keepChecking = false;

				for (int i = 0; i < mustOutcomes.length; i++) {

					if (mustOutcomes[i] != null) {

						keepChecking = true;

						if (Objects.equals(mustOutcomes[i].winner(), o.get(i).winner()) == false) {

							mustOutcomes[i] = null;
						}
					}
				}

				if (!keepChecking) { break; }
			}

			for (Outcome o : mustOutcomes) {

				if (o != null) {

					mustWins.add(o.winner().get() == Winner.HOME ?
						o.game().homeTeam() : o.game().awayTeam()
					);
				}
			}
		}

		return new Prediction(player, outcomes, mustWins);
	}

	private final Player player;
	private final List<List<Outcome>> outcomes;
	private final List<Team> mustWins;

	private Prediction(
		Player player, List<List<Outcome>> outcomes, List<Team> mustWins
	) {

		this.player = player;
		this.outcomes = Collections.unmodifiableList(outcomes);
		this.mustWins = Collections.unmodifiableList(mustWins);
	}

	public Player player() { return player; }

	public List<List<Outcome>> outcomes() { return outcomes; }

	public List<Team> mustWins() { return mustWins; }

	@Override public int compareTo(Prediction that) {

		return that.outcomes.size() - this.outcomes.size();
	}
}
