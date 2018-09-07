package com.indigo.sample.mmorpg.util;

import com.indigo.sample.mmorpg.model.object.VisibleObject;

/**
 * @author lixun
 * created at 29/8/18 下午3:34
 */
public class MathUtil {

	public static boolean isInRange(VisibleObject o1, VisibleObject o2, double distance) {
		return Math.sqrt(Math.pow(o1.getPosition().getX() - o2.getPosition().getX(), 2) +
				Math.pow(o1.getPosition().getY() - o2.getPosition().getY(), 2)) < distance;
	}

	private static final int FIX_DISTANCE = 2;
	public static boolean isNeedFix(float ox, float oy, float x, float y) {
		return Math.sqrt(Math.pow(x - ox, 2) + Math.pow(y - oy, 2))	>= FIX_DISTANCE;
	}
}
