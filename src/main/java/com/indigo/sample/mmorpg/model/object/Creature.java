package com.indigo.sample.mmorpg.model.object;

import com.indigo.sample.mmorpg.controller.CreatureController;
import com.indigo.sample.mmorpg.controller.MoveController;
import com.indigo.sample.mmorpg.model.world.WorldPosition;

/**
 * @author lixun
 * created at 28/8/18 下午5:28
 */
public abstract class Creature extends VisibleObject{

	private final MoveController moveController;

	protected Creature(long id, CreatureController<? extends Creature> controller, WorldPosition position) {
		super(id, controller, position);
		moveController = new MoveController(this);
	}

	public abstract boolean canMove();

	public MoveController getMoveController() {
		return moveController;
	}
}
