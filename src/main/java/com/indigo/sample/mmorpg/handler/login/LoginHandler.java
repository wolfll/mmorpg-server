package com.indigo.sample.mmorpg.handler.login;

import com.indigo.game.common.network.annotation.HandlerMethod;
import com.indigo.sample.mmorpg.handler.AbstractMessageHandler;
import com.indigo.sample.mmorpg.infrastructure.DefaultScheduler;
import com.indigo.sample.mmorpg.infrastructure.PlayerSession;
import com.indigo.sample.mmorpg.model.player.Player;
import com.indigo.sample.mmorpg.model.player.PlayerEntity;
import com.indigo.sample.mmorpg.model.world.World;
import com.indigo.sample.mmorpg.protocol.ErrorCode;
import com.indigo.sample.mmorpg.protocol.LoginProto;
import com.indigo.sample.mmorpg.protocol.PlayerProto;
import com.indigo.sample.mmorpg.repository.PlayerRepository;
import com.indigo.sample.mmorpg.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lixun
 * created at 30/8/18 下午4:04
 */
@Service
public class LoginHandler extends AbstractMessageHandler {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerService playerService;

	@HandlerMethod
	public void login(PlayerSession session, LoginProto.LoginReq_101215001 req) {
		LoginProto.LoginResp_101215002.Builder resp = LoginProto.LoginResp_101215002.newBuilder();
		//服务器未启动完毕
		if (!World.getInstance().isReady()) {
			resp.setResult(false);
			resp.setErrorCode(ErrorCode.ID.SERVER_NOT_READY_VALUE);
			session.send(resp.build());
			return;
		}
		//TODO 鉴权
		String token = req.getToken();
		String accountId = null;

		//下发登陆结果
		if (accountId != null)	{
			resp.setResult(true);
			session.send(resp.build());
		} else {
			resp.setResult(false);
			resp.setErrorCode(ErrorCode.ID.AUTH_FAILED_VALUE);
			session.send(resp.build());
			return;
		}

		//下发角色列表
		List<PlayerEntity> list = playerRepository.list(accountId);
		LoginProto.CharacterList_101215003.Builder characterListBuilder = LoginProto.CharacterList_101215003.newBuilder();
		for (PlayerEntity entity : list) {
			LoginProto.CharacterInfo.Builder info = LoginProto.CharacterInfo.newBuilder();
			info.setId(entity.getId());
			info.setClazz(entity.getClazz());
			info.setGender(entity.getGender());
			info.setLevel(entity.getLevel());
			info.setName(entity.getName());
			characterListBuilder.addInfo(info);
		}
		session.setAccountId(accountId);
		session.send(characterListBuilder.build());
	}

	@HandlerMethod
	public void selectCharacter(PlayerSession session, LoginProto.SelectCharacterReq_101215004 req) {
		LoginProto.SelectCharacterResp_101215005.Builder resp = LoginProto.SelectCharacterResp_101215005.newBuilder();
		long id = req.getId();

		Player player = playerService.load(id);
		if (player == null || !session.getAccountId().equals(player.getAccountId())) {
			resp.setResult(false);
			resp.setErrorCode(ErrorCode.ID.CHARACTER_NOT_EXIST_VALUE);
			session.send(resp.build());
			return;
		}

		session.setIdentity(player.getId());
		resp.setResult(true);
		session.send(resp.build());

		//下发角色数据
		PlayerProto.PlayerInfo_1612001.Builder info = PlayerProto.PlayerInfo_1612001.newBuilder();
		info.setClazz(player.getPlayerEntity().getClazz());
		info.setId(player.getId());
		info.setGender(player.getPlayerEntity().getGender());
		info.setLevel(player.getLevel());
		info.setName(player.getName());
		session.send(info.build());

		//下发位置信息
		PlayerProto.PlayerSpawn_1612002.Builder spawn = PlayerProto.PlayerSpawn_1612002.newBuilder();
		spawn.setMapId(player.getPosition().getRegion().getParent().getParent().getId());
		spawn.setInstanceId(player.getPosition().getRegion().getParent().getId());
		spawn.setX(player.getPosition().getX());
		spawn.setY(player.getPosition().getY());
		session.send(spawn.build());
	}

	@HandlerMethod
	public void logout(PlayerSession session, LoginProto.LogoutReq_1215006 req) {
		Player player = World.getInstance().getPlayer(session.getIdentity());
		player.despawn();
		LoginProto.LogoutResp_1215007.Builder builder = LoginProto.LogoutResp_1215007.newBuilder();
		DefaultScheduler.getInstance().schedule(session::close, 1, TimeUnit.SECONDS);
	}
}
