package com.lastbubble.shliktr.domain;

import java.util.List;

public interface ScoreRepository {

	List<Score> findFor(int week);
}
