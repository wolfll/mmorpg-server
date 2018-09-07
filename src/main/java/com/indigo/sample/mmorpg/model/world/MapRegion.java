package com.indigo.sample.mmorpg.model.world;

import com.indigo.sample.mmorpg.model.object.VisibleObject;

import java.util.*;

/**
 * 地图中的分块，每个分块管理身处其中的可见对象
 *
 * @author lixun
 * created at 28/8/18 下午5:14
 */
public class MapRegion {

	/*
	 * 相邻的九个地图分块，九宫格
	 */
	private List<MapRegion> neighbours = new ArrayList<>(9);

	/*
	 * 当前地图分块中的可见对象
	 */
	private final Map<Long, VisibleObject> objects = new HashMap<>();

	private final WorldMapInstance parent;

	private final int id;

	public MapRegion(int id, WorldMapInstance parent) {
		this.id = id;
		this.parent = parent;
	}

	public int getId() {
		return id;
	}

	public List<MapRegion> getNeighbours() {
		return Collections.unmodifiableList(neighbours);
	}

	public Collection<VisibleObject> getObjects() {
		return Collections.unmodifiableCollection(objects.values());
	}

	public WorldMapInstance getParent() {
		return parent;
	}

	public void addVisibleObject(VisibleObject object) {
		objects.putIfAbsent(object.getId(), object);
	}

	public void removeVisibleObject(VisibleObject object) {
		objects.remove(object.getId(), object);
	}

	protected void addNeighbour(MapRegion neighbour) {
		neighbours.add(neighbour);
	}
}
