package com.indigo.sample.mmorpg.service.world;

import com.indigo.sample.mmorpg.model.object.GameObject;
import com.indigo.sample.mmorpg.model.object.NPC;
import com.indigo.sample.mmorpg.model.player.Player;
import com.indigo.sample.mmorpg.model.world.World;
import com.indigo.sample.mmorpg.template.LocationConfig;
import com.indigo.sample.mmorpg.template.PortalConfig;
import com.indigo.sample.mmorpg.util.MathUtil;
import org.springframework.stereotype.Service;

/**
 * @author lixun
 * created at 1/9/18 下午1:54
 */
@Service
public class PortalService {

	/**
	 * 通过npc传送
	 *
	 * @param player   玩家
	 * @param npcId    npc
	 * @param locateId 目的地
	 */
	public void portal(Player player, long npcId, int locateId) {
		GameObject obj = World.getInstance().getObject(npcId);
		if (obj == null)
			return;
		if (!(obj instanceof NPC))
			return;

		NPC npc = (NPC) obj;
		//距离判断
		if (!MathUtil.isInRange(player, npc, 2))
			return;

		//读取配置
		PortalConfig portalConfig = PortalConfig.get(npc.getTypeId());
		if (portalConfig == null)
			return;
		if (!portalConfig.getLocateId().contains(locateId))
			return;
		LocationConfig locationConfig = LocationConfig.get(locateId);
		if (locationConfig == null)
			return;
		//是否同一地图内传送
		long instanceId = player.getPosition().getRegion().getParent().getId();
		if (locationConfig.getMapId() != player.getPosition().getRegion().getParent().getParent().getId()) {
			instanceId = player.getPosition().getRegion().getParent().getParent().getWorldMapInstance().getId();
		}

		player.enter(locationConfig.getMapId(), instanceId, locationConfig.getX(), locationConfig.getY());
	}
}
