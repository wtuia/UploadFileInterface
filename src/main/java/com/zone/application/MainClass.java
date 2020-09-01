package com.zone.application;

import com.zone.jdb.UploadFileJob;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;

public class MainClass {

	public static void main(String[] args) {
		Configurator.initialize("log4j2.xml",System.getProperty("user.dir") + File.separator + "log4j2.xml");
		TaskScheduler.getInstance().addJob(UploadFileJob.class, UploadFileJob.expression()).startTask();
	}
}
