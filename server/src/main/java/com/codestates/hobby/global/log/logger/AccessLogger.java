package com.codestates.hobby.global.log.logger;

import static com.codestates.hobby.global.utils.LogUtil.*;
import static net.logstash.logback.argument.StructuredArguments.*;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import com.codestates.hobby.global.log.dto.LogType;
import com.codestates.hobby.global.log.dto.RequestLog;
import com.codestates.hobby.global.log.dto.ResponseLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class AccessLogger implements Filter {
	private final RequestMappingHandlerMapping mapper;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;

		setLogIdIfIsEmpty();

		if (request.getRequestURI().startsWith("/favicon")) {
			chain.doFilter(request, response);
			return;
		}

		RequestLog requestLog = createRequestLog(request);

		chain.doFilter(request, response);

		logAfterProcess(request, response, requestLog);
	}

	private RequestLog createRequestLog(HttpServletRequest request) {
		RequestLog requestLog = new RequestLog(request);

		if (request.getRequestURI().startsWith("/login"))
			return requestLog;

		request.setAttribute("requestTime", System.currentTimeMillis());
		return requestLog;
	}

	private void logAfterProcess(HttpServletRequest request, HttpServletResponse response, RequestLog requestLog) {
		ResponseLog responseLog = new ResponseLog(request, response);
		requestLog.update(request, getHandlerMethod(request));

		log.info("", LogType.ACCESS.getArgument(), kv("request", requestLog), kv("response", responseLog));
	}

	private HandlerMethod getHandlerMethod(HttpServletRequest request) {
		HandlerMethod method = null;
		try {
			if (!ServletRequestPathUtils.hasParsedRequestPath(request))
				ServletRequestPathUtils.parseAndCache(request);
			method = (HandlerMethod)mapper.getHandler(request).getHandler();
		} catch (Exception ignore) {

		}
		return method;
	}
}
