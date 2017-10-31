package com.lastbubble.shliktr.domain;

import static com.lastbubble.shliktr.domain.Game.Outcome;
import static com.lastbubble.shliktr.domain.Game.Score;

import java.util.List;

public interface GameRepository {

	List<Score> findScoresForWeek(int week);

	List<Outcome> findOutcomesForWeek(int week);
}
