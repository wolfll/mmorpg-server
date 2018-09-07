package com.indigo.sample.mmorpg.model.object;

import com.indigo.sample.mmorpg.infrastructure.MessageUtil;
import com.indigo.sample.mmorpg.controller.VisibleObjectController;
import com.indigo.sample.mmorpg.model.player.Player;
import com.indigo.sample.mmorpg.model.world.*;
import com.indigo.sample.mmorpg.protocol.PlayerProto;

/**
 * 可见对象
 *
 * @author lixun
 * created at 28/8/18 下午5:28
 */
public abstract class VisibleObject extends GameObject {

	/*
	 * 关注列表
	 */
	private final KnownList knownList;

	/*
	 * 世界坐标
	 */
	private final WorldPosition position;

	/*
	 * 控制器
	 */
	private final VisibleObjectController<? extends VisibleObject> controller;

	/*
	 * 已创建并加入世界
	 */
	private boolean spawned;

	protected VisibleObject(long id, VisibleObjectController<? extends VisibleObject> controller, WorldPosition position) {
		super(id);
		this.controller = controller;
		this.position = position;
		this.knownList = new KnownList(this);
	}

	public VisibleObjectController<? extends VisibleObject> getController() {
		return controller;
	}

	public WorldPosition getPosition() {
		return position;
	}

	public KnownList getKnownList() {
		return knownList;
	}

	public boolean isSpawned() {
		return spawned;
	}

	public void spawn() {
		spawned = true;
		position.getRegion().addVisibleObject(this);
		knownList.update();
	}

	public void despawn() {
		spawned = false;
		position.getRegion().removeVisibleObject(this);
		knownList.clear();
	}

	/**
	 * 在当前地图上移动
	 *
	 * @param destX             x坐标
	 * @param destY             y坐标
	 * @param isUpdateKnownList 是否更新关注列表
	 */
	public void move(float destX, float destY, boolean isUpdateKnownList) {
		if (!isSpawned())
			return;
		position.setXY(destX, destY);
		MapRegion oldRegion = position.getRegion();
		MapRegion newRegion = position.getRegion().getParent().findLocateRegion(this);
		if (oldRegion != newRegion) {
			oldRegion.removeVisibleObject(this);
			newRegion.addVisibleObject(this);
			position.setRegion(newRegion);
		}
		if (isUpdateKnownList)
			knownList.update();
	}

	/**
	 * 进入地图
	 *
	 * @param mapId      地图id
	 * @param instanceId 地图实例id
	 * @param x          x坐标
	 * @param y          y坐标
	 */
	public void enter(int mapId, long instanceId, float x, float y) {
		WorldMap worldMap = World.getInstance().getWorldMap(mapId);
		if (worldMap == null)
			return;
		WorldMapInstance instance = worldMap.getWorldMapInstance(instanceId);
		if (instance == null)
			return;
		MapRegion mapRegion = instance.findLocateRegion(x, y);
		if (mapRegion == null)
			return;

		despawn();
		position.setRegion(mapRegion);
		position.setXY(x, y);

		if (this instanceof Player) {
			PlayerProto.PlayerSpawn_1612002.Builder spawn = PlayerProto.PlayerSpawn_1612002.newBuilder();
			spawn.setMapId(getPosition().getRegion().getParent().getParent().getId());
			spawn.setInstanceId(getPosition().getRegion().getParent().getId());
			spawn.setX(getPosition().getX());
			spawn.setY(getPosition().getY());
			MessageUtil.send(this.getId(), spawn.build());
		}
		spawn();
	}
}
