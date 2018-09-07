package com.indigo.sample.mmorpg.handler.world;

import com.indigo.game.common.network.annotation.HandlerMethod;
import com.indigo.sample.mmorpg.handler.AbstractMessageHandler;
import com.indigo.sample.mmorpg.infrastructure.MessageUtil;
import com.indigo.sample.mmorpg.util.MathUtil;
import com.indigo.sample.mmorpg.infrastructure.PlayerSession;
import com.indigo.sample.mmorpg.model.MoveTypeEnum;
import com.indigo.sample.mmorpg.model.player.Player;
import com.indigo.sample.mmorpg.model.world.World;
import com.indigo.sample.mmorpg.protocol.WorldProto;
import com.indigo.sample.mmorpg.service.world.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lixun
 * created at 31/8/18 下午3:57
 */
@Service
public class WorldHandler extends AbstractMessageHandler {

	@Autowired
	private PortalService portalService;

	@HandlerMethod
	public void move(PlayerSession session, WorldProto.Move_2315001 req) {
		MoveTypeEnum type = MoveTypeEnum.valueOf(req.getType());
		if (type == null)
			return;

		long id = session.getIdentity();
		Player player = World.getInstance().getPlayer(id);
		float sx = req.getSx();
		float sy = req.getSy();
		float dx = req.getDx();
		float dy = req.getDy();

		//检查
		float ox = player.getPosition().getX();
		float oy = player.getPosition().getY();
		if (MathUtil.isNeedFix(ox, oy, sx, sy)) {
			WorldProto.FixPosition_2315002.Builder fix = WorldProto.FixPosition_2315002.newBuilder();
			fix.setX(ox);
			fix.setY(oy);
			session.send(fix.build());
			return;
		}

		//TODO 速度检查

		switch (type) {
			case START: {
				player.getMoveController().setDirection(dx, dy);
				MessageUtil.broadcast(player, req, false);
				break;
			}
			case MOVING: {
				player.move(dx, dy, true);
				MessageUtil.broadcast(player, req, false);
				break;
			}
			case STOP: {
				player.move(dx, dy, true);
				MessageUtil.broadcast(player, req, false);
				break;
			}
			default:
				return;
		}
	}

	@HandlerMethod
	public void portal(PlayerSession session, WorldProto.PortalReq_2315003 req) {
		Player player = World.getInstance().getPlayer(session.getIdentity());
		portalService.portal(player, req.getNpcId(), req.getLocateId());
	}
}
