package com.indigo.sample.mmorpg.controller;

import com.indigo.sample.mmorpg.infrastructure.MessageUtil;
import com.indigo.sample.mmorpg.model.object.NPC;
import com.indigo.sample.mmorpg.model.object.VisibleObject;
import com.indigo.sample.mmorpg.model.player.Player;
import com.indigo.sample.mmorpg.protocol.PlayerProto;
import com.indigo.sample.mmorpg.protocol.WorldProto;

/**
 * @author lixun
 * created at 30/8/18 下午7:49
 */
public class PlayerController extends CreatureController<Player>{

	@Override
	public void see(VisibleObject obj) {
		if (obj instanceof Player) {
			Player player = (Player) obj;
			PlayerProto.PlayerInfo_1612001.Builder info = PlayerProto.PlayerInfo_1612001.newBuilder();
			info.setName(player.getName());
			info.setLevel(player.getLevel());
			info.setGender(player.getGender());
			info.setId(player.getId());
			info.setClazz(player.getClazz());

			WorldProto.KnownPlayer_2315005.Builder builder = WorldProto.KnownPlayer_2315005.newBuilder();
			builder.setInfo(info);
			builder.setX(player.getPosition().getX());
			builder.setY(player.getPosition().getY());

			MessageUtil.send(owner, builder.build());
		} else if (obj instanceof NPC) {
			//TODO
		}
	}

	@Override
	public void notSee(VisibleObject obj) {
		WorldProto.Forget_2315004.Builder builder = WorldProto.Forget_2315004.newBuilder();
		builder.setId(obj.getId());
		MessageUtil.send(owner, builder.build());
	}
}
