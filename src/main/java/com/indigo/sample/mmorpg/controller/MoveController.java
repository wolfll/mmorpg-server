package com.indigo.sample.mmorpg.controller;

import com.indigo.sample.mmorpg.model.object.Creature;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lixun
 * created at 31/8/18 下午4:20
 */
public class MoveController{

	private final Creature owner;

	private boolean directionChanged;

	private float dx;

	private float dy;

	private Future<?> task;

	public MoveController(Creature owner) {
		this.owner = owner;
		dx = owner.getPosition().getX();
		dy = owner.getPosition().getY();
	}

	public void setDirection(float x, float y) {
		float ox = owner.getPosition().getX();
		float oy = owner.getPosition().getY();
		dx = x;
		dy = y;
		directionChanged = !((dx == ox && dx == x) || ((oy - dy) / (ox - dx) * x + (ox * dy - dx * oy) / (ox - dx) == y));
	}

	public void schedule(ScheduledExecutorService scheduler) {
		if (!owner.canMove())
			return;
		task = scheduler.scheduleAtFixedRate(this::move, 0, 200, TimeUnit.MILLISECONDS);
	}

	private void move() {

	}

	public void stop() {
		if (task == null)
			return;
		task.cancel(false);
		task = null;
	}
}
