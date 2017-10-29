package com.lastbubble.shliktr.domain;

import java.util.List;

public interface GameRepository {

	List<Game> findForWeek(int week);
}
