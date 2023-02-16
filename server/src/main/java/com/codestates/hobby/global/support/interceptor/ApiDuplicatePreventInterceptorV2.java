package com.codestates.hobby.global.support.interceptor;

import static com.codestates.hobby.global.utils.RequestScopeUtil.*;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

/*
1. 프론트에 추가적인 작업을 요구함
2. In-memory 기반이라 분산 환경에 대한 고려가 필요함
*/

public class ApiDuplicatePreventInterceptorV2 implements HandlerInterceptor {
	private static final String NONCE_ATTRIBUTE = "X-Nonce";
	private final Set<String> nonces = ConcurrentHashMap.newKeySet();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (isDuplicateRequest(request)) {
			setErrorResponse(response, HttpStatus.TOO_MANY_REQUESTS);
			return false;
		}
		addNonce(request, response);
		return true;
	}

	private boolean isDuplicateRequest(HttpServletRequest request) {
		String nonce = request.getHeader(NONCE_ATTRIBUTE);
		return nonce != null && !nonces.add(nonce);
	}

	private void addNonce(HttpServletRequest request, HttpServletResponse response) {
		String nonce = UUID.randomUUID().toString();
		request.setAttribute(NONCE_ATTRIBUTE, nonce);
		response.setHeader(NONCE_ATTRIBUTE, nonce);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String nonce = (String) request.getAttribute(NONCE_ATTRIBUTE);
		if (nonce != null) {
			nonces.remove(nonce);
		}
	}
}