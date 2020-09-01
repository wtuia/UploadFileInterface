package com.zone.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
	private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

	public static void closeOutPutStream(OutputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}

	public static void closeInPutStream(InputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}
}
