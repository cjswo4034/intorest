package com.codestates.hobby.global.support.interceptor;

import static com.codestates.hobby.global.utils.RequestScopeUtil.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

/*
- 악의적인 요청이 아닌 사용자의 실수에 의해 발생하는 요청이라면 프론트에서 막는게 효과적으로 보임
	-> 모든 요청에 대해서 검증하기 때문.
	-> 동기화를 사용하고, Redis에 저장해서 특정 메서드에 대한 요청 처리가 늦어짐.
	-> Redis가 아닌 ConcurrentHashMap을 사용하더라도 동일한 문제가 발생함
- 악의적인 요청일 경우. 굳이 중복호출이 아니더라도 요청이 끝나자마자 지속적으로 호출하는 것은 방지할 수 없음
    -> 요청당 제한시간을 두는 것이 더 나아보임
*/

public class ApiDuplicatePreventInterceptorV1 implements HandlerInterceptor {
	private static final List<HttpMethod> METHODS = List.of(HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH);
	private final SetOperations<String, String> operations;

	public ApiDuplicatePreventInterceptorV1(RedisTemplate<String, String> template) {
		this.operations = template.opsForSet();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String method = request.getMethod();
		String remoteUser = request.getRemoteUser();

		if (!isCommandMethod(method)) {
			return true;
		}

		if (isBlank(remoteUser)) {
			setErrorResponse(response, HttpStatus.UNAUTHORIZED);
			return false;
		}

		synchronized (this) {
			if (operations.isMember(method, remoteUser)) {
				setErrorResponse(response, HttpStatus.TOO_MANY_REQUESTS);
				return false;
			}
			operations.add(method, remoteUser);
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String method = request.getMethod();
		String remoteUser = request.getRemoteUser();

		if (isCommandMethod(method)) {
			operations.remove(method, remoteUser);
		}
	}

	private boolean isCommandMethod(String reqMethod) {
		return METHODS.stream().anyMatch(method -> method.matches(reqMethod));
	}
}