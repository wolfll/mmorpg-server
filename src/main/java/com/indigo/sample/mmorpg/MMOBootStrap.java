package com.indigo.sample.mmorpg;

import com.indigo.game.common.network.ISocketServer;
import com.indigo.game.common.network.tcp.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author lixun
 * created at 1/9/18 下午3:58
 */
@SpringBootApplication
public class MMOBootStrap {

	public static void main(String[] args) {
		final ApplicationContext ctx = SpringApplication.run(MMOBootStrap.class, args);

		final ISocketServer server = ctx.getBean("mmoServer", TcpServer.class);

		try {
			server.start();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(-1);
		}

		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
	}
}
