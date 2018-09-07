package com.indigo.sample.mmorpg.model.world;


import com.indigo.sample.mmorpg.exception.DuplicateObjectException;
import com.indigo.sample.mmorpg.model.object.GameObject;
import com.indigo.sample.mmorpg.model.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixun
 * created at 28/8/18 下午5:13
 */
public class World {

	private static final World singleton = new World();

	/**
	 * 加载所有地图
	 */
	public static void init() {}

	public static World getInstance() {
		return singleton;
	}

	private final Map<Integer, WorldMap> worldMaps = new HashMap<>();

	private final Map<Long, GameObject> allObjects = new ConcurrentHashMap<>();

	private final Map<Long, Player> allPlayers = new ConcurrentHashMap<>();

	private boolean ready = false;

	public WorldMap getWorldMap(int mapId) {
		return worldMaps.get(mapId);
	}

	public boolean isReady() {
		return ready;
	}

	public void storeObject(GameObject object) {
		if (allObjects.putIfAbsent(object.getId(), object) != null)
			throw new DuplicateObjectException(object.getId(), object.getName());
		if (object instanceof Player)
			allPlayers.put(object.getId(), (Player) object);
	}

	public void removeObject(GameObject object) {
		allObjects.remove(object.getId(), object);
		if (object instanceof Player)
			allPlayers.remove(object.getId());
	}

	public GameObject getObject(long id) {
		return allObjects.get(id);
	}

	public Player getPlayer(long id) {
		return allPlayers.get(id);
	}
}
