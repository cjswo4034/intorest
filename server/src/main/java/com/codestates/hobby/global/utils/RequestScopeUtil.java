package com.codestates.hobby.global.utils;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestScopeUtil {
	public static <T> T getAttribute(String key) {
		Object value = Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
			.getAttribute(key, RequestAttributes.SCOPE_REQUEST);
		return (T)value;
	}

	public static void setAttribute(String key, Object value) {
		Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
			.setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
	}

	public static void removeAttribute(String key) {
		RequestContextHolder.getRequestAttributes().removeAttribute(key, RequestAttributes.SCOPE_REQUEST);
	}

	public static void setErrorResponse(HttpServletResponse res, HttpStatus status) {
		try {
			res.sendError(status.value(), status.getReasonPhrase());
		} catch (IOException ex) {
			res.setStatus(status.value());
		}
	}
}
