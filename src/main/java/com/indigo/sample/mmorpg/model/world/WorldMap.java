package com.indigo.sample.mmorpg.model.world;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixun
 * created at 28/8/18 下午5:13
 */
public class WorldMap {

	public enum Type {
		NORMAL(0), DUNGEON(1);

		private int value;

		Type(int value) {
			this.value = value;
		}

		public static Type valueOf(int value) {
			for (Type type : values()) {
				if (type.value == value)
					return type;
			}
			return null;
		}
	}

	private int id;

	private Type type;

	private Map<Long, WorldMapInstance> instances = new ConcurrentHashMap<>();

	public int getWidth() {
		//TODO
		return 10000;
	}

	public int getHeight() {
		//TODO
		return 10000;
	}

	public int getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	public WorldMapInstance getWorldMapInstance(long id) {
		return instances.get(id);
	}

	public WorldMapInstance getWorldMapInstance() {
		return instances.values().iterator().next();
	}
}
