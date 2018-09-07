package com.indigo.sample.mmorpg.model.world;

/**
 * 地图坐标
 *
 * @author lixun
 * created at 29/8/18 下午2:15
 */
public class WorldPosition {

	private MapRegion region;

	private float x;

	private float y;

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public MapRegion getRegion() {
		return region;
	}

	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setRegion(MapRegion region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "WorldPosition [mapRegion=" + region + ", x=" + x + ", y=" + y + "]";
	}
}
