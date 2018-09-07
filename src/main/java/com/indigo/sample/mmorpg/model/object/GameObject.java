package com.indigo.sample.mmorpg.model.object;

/**
 * @author lixun
 * created at 28/8/18 下午5:27
 */
public abstract class GameObject {

	private final long id;

	protected GameObject(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public abstract String getName();
}
