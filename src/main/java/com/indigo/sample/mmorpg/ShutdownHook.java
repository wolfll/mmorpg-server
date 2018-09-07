package com.indigo.sample.mmorpg;

import com.indigo.game.common.network.ISocketServer;
import com.indigo.game.common.network.tcp.TcpServer;
import com.indigo.game.common.persistence.db.DBManager;
import com.indigo.sample.mmorpg.infrastructure.SpringUtil;

import java.util.concurrent.ExecutorService;

/**
 * @author lixun
 * created at 1/9/18 下午4:04
 */
public class ShutdownHook extends Thread{

	@Override
	public void run() {

		final ISocketServer server = SpringUtil.getBean("mmoServer", TcpServer.class);
		server.shutdown();

		ExecutorService defaultThreadPool = SpringUtil.getBean("defaultThreadPool", ExecutorService.class);
		defaultThreadPool.shutdownNow();

		DBManager.getInstance().shutdown();
	}
}
