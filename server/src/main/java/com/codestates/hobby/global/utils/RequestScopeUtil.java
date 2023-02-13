package com.codestates.hobby.global.utils;

import java.util.Objects;

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

}
