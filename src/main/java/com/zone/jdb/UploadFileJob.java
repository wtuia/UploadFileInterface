package com.zone.jdb;

import com.zone.bean.FileProperty;
import com.zone.bean.FtpProperty;
import com.zone.common.PropertiesHandle;
import com.zone.util.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.core.config.Configurator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class UploadFileJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileJob.class);

	public static void main(String[] args) {
		Configurator.initialize("log4j2.xml",System.getProperty("user.dir") + File.separator + "log4j2.xml");
		new UploadFileJob().execute(null);
	}

	@Override
	public void execute(JobExecutionContext context) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		for (Map.Entry<FtpProperty, List<FileProperty>> entry : ReadFileConfig.FTP_FILE_MAP.entrySet()) {
			FtpProperty ftp = entry.getKey();
			for (FileProperty fileConfig : entry.getValue()) {
				fileConfig.setLocalPathAndName(calendar);
				FTPUtil.upload(ftp, fileConfig);
			}
		}
	}

	public static String expression() {
		return PropertiesHandle.getResource("cron");
	}
}
