package com.codestates.hobby.global.log.dto;

import static net.logstash.logback.argument.StructuredArguments.*;

import net.logstash.logback.argument.StructuredArgument;

import lombok.Getter;

@Getter
public enum LogType {
	ACCESS, DEBUG, ERROR;

	private final StructuredArgument argument;

	LogType() {
		this.argument = kv("type", name());
	}
}
