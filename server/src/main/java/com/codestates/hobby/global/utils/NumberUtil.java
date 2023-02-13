package com.codestates.hobby.global.utils;

public class NumberUtil {
	public static int parseIntOrDefault(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception ignore) {
			return defaultValue;
		}
	}
}
