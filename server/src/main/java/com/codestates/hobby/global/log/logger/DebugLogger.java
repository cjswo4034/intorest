package com.codestates.hobby.global.log.logger;

import static com.codestates.hobby.global.utils.LogUtil.*;
import static net.logstash.logback.argument.StructuredArguments.*;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import net.logstash.logback.argument.StructuredArgument;

import com.codestates.hobby.global.log.dto.LogType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class DebugLogger {
	private static final String[] LAYERS = {"Controller", "Service", "Repository", "EventHandler"};

	@Pointcut("within(com.codestates.hobby.domain.*.controller..*)")
	public void controller() {}

	@Pointcut("within(com.codestates.hobby.domain.*.service..*)")
	public void service() {}

	@Pointcut("execution(public !void org.springframework.data.repository.Repository+.*(..)))")
	public void repository() {}

	@Around("controller() || service() || repository()")
	public Object logWithinServiceLayer(ProceedingJoinPoint joinPoint) throws Throwable {
		long tic = System.currentTimeMillis();
		try {
			return joinPoint.proceed();
		} finally {
			log(joinPoint, tic);
		}
	}

	private void log(JoinPoint joinPoint, long tic) {
		Signature signature = joinPoint.getSignature();
		String targetName = signature.getDeclaringType().getSimpleName();

		StructuredArgument layer = kv("layer", getLayer(targetName));
		StructuredArgument target = kv("target", targetName);
		StructuredArgument method = kv("method", signature.getName());
		StructuredArgument latency = kv("latency", System.currentTimeMillis() - tic);
		StructuredArgument args = a("args", joinPoint.getArgs());

		setLogIdIfIsEmpty();

		log.info("", LogType.DEBUG.getArgument(), layer, target, method, latency, args);
	}

	private static String getLayer(String str) {
		return Arrays.stream(LAYERS)
			.filter(str::endsWith)
			.findAny()
			.orElse("NotFound");
	}
}
