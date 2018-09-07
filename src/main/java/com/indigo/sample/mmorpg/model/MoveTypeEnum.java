package com.indigo.sample.mmorpg.model;

/**
 * @author lixun
 * created at 31/8/18 下午4:53
 */
public enum MoveTypeEnum{

	START(0), MOVING(1), STOP(2);

	private int value;

	public int getValue() {
		return value;
	}

	MoveTypeEnum(int value) {
		this.value = value;
	}

	public static MoveTypeEnum valueOf(int value) {
		for (MoveTypeEnum type : values()) {
			if (type.value == value)
				return type;
		}
		return null;
	}
}
