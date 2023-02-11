package com.codestates.hobby.global.utils;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;

public class LogUtil {
	public static final String LOG_ID = "logId";

	public static String getLogId() {
		String logId = RequestScopeUtil.getAttribute(LOG_ID);

		if (isBlank(logId)) {
			logId = UUID.randomUUID().toString();
			RequestScopeUtil.setAttribute("logId", logId);
		}

		return logId;
	}

	public static void setLogIdIfIsEmpty() {
		if (!ThreadContext.containsKey(LOG_ID))
			ThreadContext.put(LOG_ID, getLogId());
	}
}
