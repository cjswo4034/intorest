package com.codestates.hobby.global.log.dto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;

@Getter
public class ResponseLog {
	private final int status;
	private final long latency;

	public ResponseLog(HttpServletRequest request, HttpServletResponse response) {
		long reqTime = (long)request.getAttribute("requestTime");

		this.latency = System.currentTimeMillis() - reqTime;
		this.status = response.getStatus();
	}
}
