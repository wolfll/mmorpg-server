package com.indigo.sample.mmorpg.model.object;

import com.indigo.sample.mmorpg.controller.CreatureController;
import com.indigo.sample.mmorpg.model.world.WorldPosition;

/**
 * @author lixun
 * created at 28/8/18 下午5:28
 */
public class NPC extends Creature{

	private int typeId;

	public NPC(long id, CreatureController<? extends Creature> controller, WorldPosition position) {
		super(id, controller, position);
	}

	@Override
	public boolean canMove() {
		return false;
	}

	@Override
	public String getName() {
		return null;
	}

	public int getTypeId() {
		return typeId;
	}
}
