package com.indigo.sample.mmorpg.template;

import com.indigo.game.common.template.LoadTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 传送点
 *
 * @author lixun
 * created at 1/9/18 下午1:28
 */
public class PortalConfig {

	private int npcId;

	private List<Integer> locateId;

	private static Map<Integer, PortalConfig> cache;

	@LoadTemplate
	public static void load(List<PortalConfig> list) {
		Map<Integer, PortalConfig> map = new HashMap<>();
		for (PortalConfig config : list) {
			map.put(config.npcId, config);
		}
		cache = map;
	}

	public static PortalConfig get(int id) {
		return cache.get(id);
	}

	public int getNpcId() {
		return npcId;
	}

	public List<Integer> getLocateId() {
		return Collections.unmodifiableList(locateId);
	}
}
