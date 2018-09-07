package com.indigo.sample.mmorpg.repository;

import com.indigo.game.common.persistence.db.DelayedJDBCRepository;
import com.indigo.sample.mmorpg.model.player.PlayerEntity;

import java.util.List;

/**
 * @author lixun
 * created at 30/8/18 下午4:52
 */
public class PlayerRepository extends DelayedJDBCRepository<PlayerEntity> {

	public List<PlayerEntity> list(String accountId) {
		return super.list("accountId", accountId);
	}
}
