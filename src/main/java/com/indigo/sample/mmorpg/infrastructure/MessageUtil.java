package com.indigo.sample.mmorpg.infrastructure;

import com.google.protobuf.Message;
import com.indigo.game.common.network.ISession;
import com.indigo.sample.mmorpg.model.player.Player;
import com.indigo.sample.mmorpg.model.object.VisibleObject;

/**
 * @author lixun
 * created at 31/8/18 下午5:00
 */
public class MessageUtil {

	public static void broadcast(Player player, Message message, boolean toSelf) {
		for (VisibleObject obj : player.getKnownList().getAllKnownObjects()) {
			if (obj instanceof Player)
				send((Player) obj, message);
		}
		if (toSelf)
			send(player, message);
	}

	public static void send(Player player, Message message) {
		send(player.getId(), message);
	}

	public static void send(long playerId, Message message) {
		PlayerSessionManager sessionManager = SpringUtil.getBean("sessionManager", PlayerSessionManager.class);
		ISession<Long> session = sessionManager.getSession(playerId);
		session.send(message);
	}
}
