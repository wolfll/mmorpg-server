package com.indigo.sample.mmorpg.infrastructure;

import com.indigo.game.common.network.*;
import com.indigo.game.common.network.exception.PacketHandleException;
import com.indigo.sample.mmorpg.protocol.LoginProto;

import java.util.concurrent.ExecutorService;

/**
 * @author lixun
 * created at 30/8/18 下午4:09
 */
public class PlayerSession extends KeepAliveSession<Long> {

	private ExecutorService defaultThreadPool;

	private String accountId;

	public PlayerSession(Connection connection, ExecutorService executor, IMessageHandlerManager messageHandlerManager) {
		super(connection, executor, messageHandlerManager);
		this.defaultThreadPool = executor;
	}

	@Override
	protected void handle(MessageHandler handler, int protocolId, Object message) throws Exception {
		//登陆请求永远使用默认的线程池处理
		if (message.getClass() == LoginProto.LoginReq_101215001.class) {
			handler.invoke(this, message);
		} else {
			//id分段
			if (protocolId > 100000000) {
				execute(defaultThreadPool, handler, protocolId, message);
			} else {
				//executor为地图绑定的线程池，保证同一地图上的所有事件排队处理，避免业务加锁
				execute(executor, handler, protocolId, message);
			}
		}
	}

	private void execute(ExecutorService executorService, MessageHandler handler, int protocolId, Object message) {
		final PlayerSession session = this;
		executorService.execute(() -> {
			try {
				handler.invoke(session, message);
			} catch (Exception e) {
				onException(new PacketHandleException(protocolId, e));
			}
		});
	}

	public void setAccountId(String accountId) {
		if (this.accountId == null)
			this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
