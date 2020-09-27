package com.hbe.wordFilter;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hbe.wordFilter.service.impl.FileServiceImpl;

public class App {

	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		FileServiceImpl fileService = context.getBean("fileService", FileServiceImpl.class);
		try {
			HashMap<String, String> params = fileService.initFileParams(args);
			fileService.processFile(params);
			logger.info("Processing terminated with success !");
		} catch (IllegalArgumentException | IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			context.close();
		}
	}
}
