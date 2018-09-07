package com.indigo.sample.mmorpg.infrastructure;

import com.indigo.game.common.network.CRC16CheckSum;
import com.indigo.game.common.network.DefaultHandlerManager;
import com.indigo.game.common.network.IMessageHandlerManager;
import com.indigo.game.common.network.tcp.TcpServer;
import com.indigo.game.common.util.PropertiesLoader;
import com.indigo.game.common.util.thread.OrderedExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * @author lixun
 * created at 30/8/18 下午1:57
 */
@Configuration
public class ServerConfiguration {

	private final Properties properties;

	public ServerConfiguration() {
		properties = PropertiesLoader.load("server.properties");
	}

	@Bean("defaultThreadPool")
	public ExecutorService getDefaultThreadPool() {
		int corePoolSize = Integer.parseInt(properties.getProperty("corePoolSize"));
		int maxPoolSize = Integer.parseInt(properties.getProperty("maxPoolSize"));
		int keepAliveTime = Integer.parseInt(properties.getProperty("keepAliveTime"));
		String threadName = properties.getProperty("threadName");
		return new OrderedExecutorService(corePoolSize, maxPoolSize, keepAliveTime, threadName);
	}

	@Bean("sessionManager")
	public PlayerSessionManager getSessionManager() {
		return new PlayerSessionManager(getDefaultThreadPool(), getMessageHandlerManager());
	}

	@Bean("messageHandlerManager")
	public IMessageHandlerManager getMessageHandlerManager() {
		return new DefaultHandlerManager();
	}

	@Bean("mmoServer")
	public TcpServer getMMOServer() {
		String ip = properties.getProperty("ip");
		int port = Integer.parseInt(properties.getProperty("port"));
		int threads = Integer.parseInt(properties.getProperty("ioThreads"));
		TcpServer server = new TcpServer(ip, port, threads, new CRC16CheckSum());
		server.registerListener(getSessionManager());
		return server;
	}
}
