package com.zone.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandle {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesHandle.class);

    private static PropertiesHandle PROP_HANDLE;
    private static String DEFAULT_FILENAME = "ziyuan.properties";
    private Properties props;

    private PropertiesHandle(String filename) {
        this.load(filename);

    }

    public static String getResource(String fileName) {
    	return getResourceByFileName(null, fileName);
	}

	public static String getResourceAndSetDefault(String fileName, String defaultValue) {
    	String value = getResourceByFileName(fileName);
		return value == null ? defaultValue : value;
    }

    public static String getFilePath(String key, String defaultFileValue) {
		String path = getResourceByFileName(key);
		if (path == null) {
			path = defaultFileValue;
		}
		if (!path.endsWith("/")) {
			path += "/";
		}
		return path;
	}

	private static String getResourceByFileName(String key) {
		if ( PROP_HANDLE == null) {
			PROP_HANDLE = new PropertiesHandle(DEFAULT_FILENAME);
		}
		return PROP_HANDLE.getProperties(key);
	}

	public static String getResourceByFileName(String filename, String key) {
        if (filename == null || filename.equals("")){
            filename = DEFAULT_FILENAME;
        }
        if ( PROP_HANDLE == null) {
            PROP_HANDLE = new PropertiesHandle(filename);
        }
        String properties = PROP_HANDLE.getProperties(key);
        if (properties == null || "".equals(properties)) {
        	throw new NullPointerException( key + "为必要参数，值未配置");
		}
        return properties;
    }


    static PropertiesHandle getPropertiesHandle(String filename) {
        if (DEFAULT_FILENAME.equalsIgnoreCase(filename)) {
            return PROP_HANDLE == null ? new PropertiesHandle(filename) : PROP_HANDLE;
        }else {
            return new PropertiesHandle(filename);
        }
    }

	public static String getProperty(String key) {
		return PROP_HANDLE.getProperties(key);
	}

    String getProperties(String key) {
        return props.getProperty(key);
    }


    private void load(String filename) {
        props = new Properties();
        // 项目路径
        String currentDir = System.getProperty("user.dir");
        filename = currentDir + File.separator  + filename;
            try {
                InputStream in = new FileInputStream(filename);
                props.load(in);
                in.close();
            } catch (IOException e) {
                logger.error("初始化文件失败", e);
            }
    }
}
