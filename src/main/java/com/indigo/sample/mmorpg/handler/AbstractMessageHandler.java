package com.indigo.sample.mmorpg.handler;

import com.indigo.game.common.network.IMessageHandlerManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author lixun
 * created at 30/8/18 下午4:02
 */
public abstract class AbstractMessageHandler {

	@Autowired
	private IMessageHandlerManager messageHandlerManager;

	@PostConstruct
	public void register() {
		messageHandlerManager.register(this);
	}
}
