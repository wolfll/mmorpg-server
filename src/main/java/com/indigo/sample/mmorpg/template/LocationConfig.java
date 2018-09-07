package com.indigo.sample.mmorpg.template;

import com.indigo.game.common.template.LoadTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 位置信息
 *
 * @author lixun
 * created at 1/9/18 下午2:05
 */
public class LocationConfig {

	private int id;

	private int mapId;

	private float x;

	private float y;

	private String name;

	public static Map<Integer, LocationConfig> cache;

	@LoadTemplate
	public static void load(List<LocationConfig> list) {
		Map<Integer, LocationConfig> map = new HashMap<>();
		list.forEach(n -> map.put(n.id, n));
		cache = map;
	}

	public static LocationConfig get(int id) {
		return cache.get(id);
	}

	public int getId() {
		return id;
	}

	public int getMapId() {
		return mapId;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public String getName() {
		return name;
	}
}
