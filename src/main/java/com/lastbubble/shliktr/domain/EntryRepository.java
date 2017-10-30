package com.lastbubble.shliktr.domain;

import java.util.List;

public interface EntryRepository {

	List<Entry> findFor(int week);

	Entry findFor(int week, Player player);
}
