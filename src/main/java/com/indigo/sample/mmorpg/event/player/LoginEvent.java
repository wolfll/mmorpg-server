package com.indigo.sample.mmorpg.event.player;

import com.indigo.game.common.event.IEvent;
import com.indigo.sample.mmorpg.model.player.Player;

/**
 * 登陆事件
 *
 * @author lixun
 * created at 31/8/18 下午1:36
 */
public class LoginEvent implements IEvent {

	private final Player player;

	public LoginEvent(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
