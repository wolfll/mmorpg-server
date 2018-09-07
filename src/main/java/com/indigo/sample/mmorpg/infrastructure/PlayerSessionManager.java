package com.indigo.sample.mmorpg.infrastructure;

import com.indigo.game.common.network.*;

import java.util.concurrent.ExecutorService;

/**
 * @author lixun
 * created at 30/8/18 下午4:35
 */
public class PlayerSessionManager extends KeepAliveSessionManager<Long> {

	public PlayerSessionManager(ExecutorService executorService, IMessageHandlerManager messageHandlerManager) {
		super(executorService, messageHandlerManager);
	}

	@Override
	protected PlayerSession build(Connection connection) {
		return new PlayerSession(connection, executorService, messageHandlerManager);
	}
}
