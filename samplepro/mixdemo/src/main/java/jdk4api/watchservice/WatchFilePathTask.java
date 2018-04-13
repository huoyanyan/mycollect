package jdk4api.watchservice;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

public class WatchFilePathTask extends Thread {
	private Log log = LogFactory.getLog(WatchFilePathTask.class);

	private static final String filePath = "e://1.txt";

	private WatchService watchService;

	@Override
	public void run() {
		try {
			// 获取监控服务
			watchService = FileSystems.getDefault().newWatchService();
			log.debug("获取监控服务" + watchService);
			Path path = FileSystems.getDefault().getPath(filePath);
			log.debug("@@@:Path:" + path);

			final String todayFormat = DateTime.now().toString("yyyyMMdd");

			File existFiles = new File(filePath);
			// 启动时检查是否有未解析的符合要求的文件
			if (existFiles.isDirectory()) {
				File[] matchFile = existFiles.listFiles(new FileFilter() {

					@Override
					public boolean accept(File pathname) {
						if ((todayFormat + ".txt").equals(pathname.getName())) {
							return true;
						} else {
							return false;
						}
					}
				});

				if (null != matchFile) {
					for (File file : matchFile) {
						// 找到符合要求的文件，开始解析
						log.info("import filePath:" + file.getAbsolutePath());
					}
				}
			}

			// 注册监控服务，监控新增事件
			WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			while (true) {
				key = watchService.take();
				for (WatchEvent<?> event : key.pollEvents()) {

					// 获取目录下新增的文件名
					String fileName = event.context().toString();

					// 检查文件名是否符合要求
					if ((todayFormat + ".txt").equals(fileName)) {
						String filePath = path.toFile().getAbsolutePath() + File.separator + fileName;
						log.info("import filePath:" + filePath);

						log.debug("启动线程导入用户数据");
					}
				}
				key.reset();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
}