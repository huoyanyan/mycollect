package com.ping.test.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class Config {
	private static PropertiesConfiguration propConfig;

	private static final Config CONFIG = new Config();

	/**
	 * 自动保存
	 */
	private static boolean autoSave = true;

	private Config() {
	}

	public static Config getInstance(String propertiesFile) {
		// 执行初始化
		init(propertiesFile);
		return CONFIG;
	}

	/**
	 * 初始化
	 *
	 * @param propertiesFile
	 * @see
	 */
	private static void init(String propertiesFile) {
		try {
			propConfig = new PropertiesConfiguration(propertiesFile);
			propConfig.setEncoding("UTF-8");
//			propConfig.load(propertiesFile);
			// 自动重新加载
			propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
			// 自动保存
			propConfig.setAutoSave(autoSave);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据Key获得对应的value
	 *
	 * @param key
	 * @return
	 * @see
	 */
	public Object getValue(String key) {
		return propConfig.getProperty(key);
	}

	/**
	 * 设置属性
	 *
	 * @param key
	 * @param value
	 * @see
	 */
	public void setProperty(String key, String value) {
		propConfig.setProperty(key, value);
	}

	public static void main(String[] args) throws InterruptedException {
		String filepath = CONFIG.getClass().getResource("").getPath() + "test.properties";
		System.out.println(filepath);
		Config config = getInstance(filepath);
		System.out.println(config.getValue("aa"));
		config.setProperty("bb", "波");
		System.out.println(config.getValue("bb"));

//		while (config.getValue("cc") == null) {
//			Thread.currentThread().sleep(5000L);
//		}
//		System.out.println(config.getValue("cc"));
	}
}
