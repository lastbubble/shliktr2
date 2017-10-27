package com.lastbubble.shliktr.domain;

public interface PlayerRepository {

	Iterable<Player> findAll();

	Player findById(int id);
}
