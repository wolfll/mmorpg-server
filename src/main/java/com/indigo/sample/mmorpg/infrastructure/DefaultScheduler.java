package com.indigo.sample.mmorpg.infrastructure;

import com.indigo.game.common.scheduler.Scheduler;

/**
 * @author lixun
 * created at 1/9/18 下午3:08
 */
public class DefaultScheduler {

	private static Scheduler singleton;

	private static final int THREADS = 8;

	public static Scheduler getInstance() {
		if (singleton != null)
			return singleton;
		synchronized (DefaultScheduler.class) {
			if (singleton != null)
				return singleton;
			singleton = new Scheduler("default", THREADS);
			return singleton;
		}
	}
}
