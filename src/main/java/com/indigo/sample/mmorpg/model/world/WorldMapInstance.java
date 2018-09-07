package com.indigo.sample.mmorpg.model.world;

import com.indigo.sample.mmorpg.model.object.VisibleObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏地图的实例
 *
 * @author lixun
 * created at 28/8/18 下午5:14
 */
public class WorldMapInstance {

	private static final int REGION_SIZE = 500;

	private static final int MAX_WORLD_SIZE = 10000;

	private long id;

	private final WorldMap parent;

	/*
	 * 本地图的所有分块
	 */
	private final Map<Integer, MapRegion> regions = new HashMap<>();

	public WorldMapInstance(long id, WorldMap parent) {
		this.id = id;
		this.parent = parent;
		createRegion();
	}

	/**
	 * 找出可见对象所在的地图分块
	 *
	 * @param object 可见对象
	 * @return 地图分块
	 */
	public MapRegion findLocateRegion(VisibleObject object) {
		return findLocateRegion(object.getPosition().getX(), object.getPosition().getY());
	}

	public MapRegion findLocateRegion(float x, float y) {
		return regions.get(getRegionId(x, y));
	}

	private void createRegion() {
		int rx = parent.getWidth() % REGION_SIZE == 0 ?
				parent.getWidth() / REGION_SIZE :
				(parent.getWidth() / REGION_SIZE + 1);
		int ry = parent.getHeight() % REGION_SIZE == 0 ?
				parent.getHeight() / REGION_SIZE :
				(parent.getHeight() / REGION_SIZE + 1);
		for (int x = 1; x <= rx; x++) {
			for (int y = 1; y <= ry; y++) {
				MapRegion region = new MapRegion(x * MAX_WORLD_SIZE + y, this);
				regions.put(region.getId(), region);
			}
		}
		for (int x = 1; x <= rx; x++) {
			for (int y = 1; y <= ry; y++) {
				MapRegion region = regions.get(x * MAX_WORLD_SIZE + y);
				for (int i=-1; i<=1; i++) {
					for (int j=-1; j<=1; j++) {
						int regionId = (x + j) *MAX_WORLD_SIZE + (y + i);
						if (regions.containsKey(regionId))
							region.addNeighbour(regions.get(regionId));
					}
				}
			}
		}
	}

	private int getRegionId(double x, double y) {
		return ((int) x) / REGION_SIZE * MAX_WORLD_SIZE + ((int) y) / REGION_SIZE;
	}

	public long getId() {
		return id;
	}

	public WorldMap getParent() {
		return parent;
	}
}
