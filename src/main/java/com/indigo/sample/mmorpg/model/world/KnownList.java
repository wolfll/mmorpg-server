package com.indigo.sample.mmorpg.model.world;

import com.indigo.sample.mmorpg.model.object.VisibleObject;
import com.indigo.sample.mmorpg.util.MathUtil;

import java.util.*;

/**
 * 关注列表
 * 存放着视野范围内的可视对象，设定所有对象的视野范围一样，因此该列表中的对象即是关注的对象，也是被关注的对象
 *
 * @author lixun
 * created at 28/8/18 下午5:19
 */
public class KnownList{

	/*
	 * 视野距离
	 */
	private static final double VISUAL_DISTANCE = 100;

	/*
	 * 关注列表的拥有者
	 */
	private final VisibleObject owner;

	/*
	 * 关注的可见对象
	 */
	private Map<Long, VisibleObject> objects = new HashMap<>();

	public KnownList(VisibleObject owner) {
		this.owner = owner;
	}

	/**
	 * 自身的位置状态发生改变时调用
	 */
	public void update() {
		forgetObjects();
		findVisibleObjects();
	}

	private void add(VisibleObject object) {
		if (objects.putIfAbsent(object.getId(), object) == null)
			owner.getController().see(object);
	}

	private void remove(VisibleObject object) {
		if (objects.remove(object.getId(), object))
			owner.getController().notSee(object);
	}

	private void forgetObjects() {
		Iterator<VisibleObject> it = objects.values().iterator();
		while(it.hasNext()) {
			VisibleObject obj = it.next();
			if (!MathUtil.isInRange(owner, obj, VISUAL_DISTANCE)) {
				it.remove();
				owner.getController().notSee(obj);
				obj.getKnownList().remove(owner);
			}
		}
	}

	private void findVisibleObjects() {
		if (owner.isSpawned())
			return;
		for (MapRegion region : owner.getPosition().getRegion().getNeighbours()) {
			for (VisibleObject obj : region.getObjects()) {
				if (obj == owner)
					continue;
				if (!MathUtil.isInRange(owner, obj, VISUAL_DISTANCE))
					continue;
				add(obj);
				obj.getKnownList().add(owner);
			}
		}
	}

	public Collection<VisibleObject> getAllKnownObjects() {
		return Collections.unmodifiableCollection(objects.values());
	}

	/**
	 * 清空并取消关注者对自己的关注
	 */
	public void clear() {
		Iterator<VisibleObject> it = objects.values().iterator();
		while(it.hasNext()) {
			VisibleObject obj = it.next();
			obj.getKnownList().remove(owner);
			it.remove();
		}
	}
}
