package com.codestates.hobby.global.log.logger;

import static com.codestates.hobby.global.utils.LogUtil.*;
import static net.logstash.logback.argument.StructuredArguments.*;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.codestates.hobby.global.log.dto.LogType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RequestBodyLogger extends RequestBodyAdviceAdapter {
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		setLogIdIfIsEmpty();

		log.info("", LogType.DEBUG.getArgument(), kv("requestBody", body));

		return body;
	}
}
