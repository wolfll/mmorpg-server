package com.indigo.sample.mmorpg.model.player;

import com.indigo.sample.mmorpg.model.object.Creature;
import com.indigo.sample.mmorpg.controller.PlayerController;
import com.indigo.sample.mmorpg.model.world.WorldPosition;

/**
 * @author lixun
 * created at 28/8/18 下午5:33
 */
public final class Player extends Creature {

	private final PlayerEntity playerEntity;

	public Player(PlayerEntity playerEntity, PlayerController controller, WorldPosition position) {
		super(playerEntity.getId(), controller, position);
		this.playerEntity = playerEntity;
		controller.setOwner(this);
	}

	public PlayerEntity getPlayerEntity() {
		return playerEntity;
	}

	@Override
	public String getName() {
		return playerEntity.getName();
	}

	public String getAccountId() {
		return playerEntity.getAccountId();
	}

	public int getLevel() {
		return playerEntity.getLevel();
	}

	public long getExp() {
		return playerEntity.getExp();
	}

	public int getGender() {
		return playerEntity.getGender();
	}

	public int getClazz() {
		return playerEntity.getClazz();
	}

	@Override
	public boolean canMove() {
		return false;
	}
}
