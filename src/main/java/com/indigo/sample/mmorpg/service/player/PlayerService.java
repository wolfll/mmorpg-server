package com.indigo.sample.mmorpg.service.player;

import com.indigo.game.common.event.EventBusImpl;
import com.indigo.sample.mmorpg.event.player.LoginEvent;
import com.indigo.sample.mmorpg.model.player.Player;
import com.indigo.sample.mmorpg.model.player.PlayerEntity;
import com.indigo.sample.mmorpg.model.world.World;
import com.indigo.sample.mmorpg.model.world.WorldMap;
import com.indigo.sample.mmorpg.model.world.WorldMapInstance;
import com.indigo.sample.mmorpg.model.world.WorldPosition;
import com.indigo.sample.mmorpg.repository.PlayerRepository;
import com.indigo.sample.mmorpg.controller.PlayerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lixun
 * created at 30/8/18 下午4:04
 */
@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * 加载角色，加载过的角色会存储在{@link World}中，角色下线后超过一定的时长会将其从{@link World}中移除
	 *
	 * @param id
	 * @return
	 */
	public Player load(long id) {
		Player player = World.getInstance().getPlayer(id);
		if (player != null) {
			player.spawn();
			EventBusImpl.getInstance().post(new LoginEvent(player));
			return player;
		}

		PlayerEntity entity = playerRepository.get(id);
		if (entity == null)
			return null;
		WorldMap worldMap = World.getInstance().getWorldMap(entity.getMapId());
		WorldMapInstance worldMapInstance = worldMap.getWorldMapInstance();

		//TODO 地图不存在时的容错处理

		WorldPosition position = new WorldPosition();
		position.setRegion(worldMapInstance.findLocateRegion(entity.getX(), entity.getY()));
		position.setXY(entity.getX(), entity.getY());

		player = new Player(entity, new PlayerController(), position);

		//TODO 属性

		//TODO 外观数据
		//TODO 装备数据
		//TODO 技能数据
		//TODO 背包数据
		//TODO 其他数据

		//进入地图
		player.spawn();
		EventBusImpl.getInstance().post(new LoginEvent(player));
		return player;
	}
}
