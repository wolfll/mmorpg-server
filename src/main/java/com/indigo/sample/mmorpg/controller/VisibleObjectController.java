package com.indigo.sample.mmorpg.controller;

import com.indigo.sample.mmorpg.model.object.VisibleObject;

/**
 * @author lixun
 * created at 29/8/18 下午2:16
 */
public abstract class VisibleObjectController<T extends VisibleObject> {

	protected T owner;

	public void setOwner(T owner) {
		if (this.owner == null)
			this.owner = owner;
	}

	public abstract void see(VisibleObject obj);

	public abstract void notSee(VisibleObject obj);
}
