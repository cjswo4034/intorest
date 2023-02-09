package com.codestates.hobby.global.log.dto;

import static org.apache.commons.lang3.StringUtils.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;

import lombok.Getter;

@Getter
public class RequestLog {
	private final String userAgent;
	private final String contentType;
	private final String remoteAddr;
	private final String requestUri;
	private final String queryParams;
	private final String requestMethod;

	private String remoteUser;
	private String resource;
	private String command;

	public RequestLog(HttpServletRequest request) {
		this.userAgent = request.getHeader(HttpHeaders.USER_AGENT);
		this.contentType = defaultString(request.getContentType(), "null");
		this.queryParams = isBlank(request.getQueryString()) ? "" : ("?" + request.getQueryString());
		this.remoteAddr = defaultString(request.getRemoteAddr(), request.getHeader("X-Forwarded-For"));
		this.requestUri = request.getRequestURI();
		this.requestMethod = request.getMethod();

		if (request.getRequestURI().startsWith("/login")) {
			this.resource = "auth";
			this.command = "login";
		}
	}

	public void update(HttpServletRequest request, Object handler) {
		if (!(handler instanceof HandlerMethod)) return;

		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Class<?> beanType = handlerMethod.getBeanType();

		this.remoteUser = defaultString(request.getRemoteUser(), "anonymous");
		this.resource = beanType.getSimpleName().replaceAll("Controller", "");
		this.command = handlerMethod.getMethod().getName();
	}
}
