package com.indigo.sample.mmorpg.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author lixun
 * created at 1/9/18 下午4:05
 */
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static <T> T getBean(String name, Class<T> type) {
		return applicationContext.getBean(name ,type);
	}
}
