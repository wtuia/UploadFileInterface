package com.zone.jdb;

import com.zone.bean.FileProperty;
import com.zone.bean.FtpProperty;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadFileConfig {

	private static final Logger logger = LoggerFactory.getLogger(ReadFileConfig.class);
	public static final Map<FtpProperty, List<FileProperty>> FTP_FILE_MAP = new HashMap<>();

	static {
		read();
	}

	public static void main(String[] args) {
		System.out.println(FTP_FILE_MAP);
	}

	@SuppressWarnings("unchecked")
	private static void  read() {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File("uploadFileConfig.xml"));
			Element root = document.getRootElement();
			List<Element> childEles = root.elements();
			String server, user, pass, port;
			FtpProperty ftp;
			List<Element> fileEles, fileconfigs;
			for (Element e : childEles) {
				server = e.attributeValue("server");
				user = e.attributeValue("user");
				pass = e.attributeValue("pass");
				port = e.attributeValue("port");
				ftp = new FtpProperty(server, user, pass, port);
				fileEles = e.elements();
				List<FileProperty> fileConfigList = new ArrayList<>();
				for (Element fileEle : fileEles) {
					fileconfigs = fileEle.elements();
					FileProperty fileProperty = new FileProperty();
					for (Element fileConfig : fileconfigs) {
						setMethodValue(fileProperty.getClass(), fileProperty, fileConfig.getName(), fileConfig.getText());
					}
					fileConfigList.add(fileProperty);
				}
				FTP_FILE_MAP.put(ftp, fileConfigList);
			}
		}catch (Exception e) {
			logger.error("", e);
		}
	}

	private static void setMethodValue(Class<? extends FileProperty> clz, Object obj, String methodSuffix, String value) {
		String methodName = "set" + methodSuffix.substring(0, 1).toUpperCase() + methodSuffix.substring(1);
		try {
			Method method = clz.getDeclaredMethod(methodName, String.class);
			method.invoke(obj, value);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			logger.error("设置值失败",e);
		}

	}
}
