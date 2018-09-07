package com.indigo.sample.mmorpg.exception;

/**
 * @author lixun
 * created at 31/8/18 上午11:14
 */
public class DuplicateObjectException extends RuntimeException{

	public DuplicateObjectException(long id, String name) {
		super("duplicate object:" + id + "," + name);
	}
}
