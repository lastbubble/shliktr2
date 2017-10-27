package com.lastbubble.shliktr.domain;

public interface EntryRepository {

	Entry findFor(int week, Player player);
}
