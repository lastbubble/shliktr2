package com.lastbubble.shliktr.domain;

import java.util.List;

public interface ScoreRepository {

	List<SeasonScore> findAll();

	List<Score> findFor(int week);
}
