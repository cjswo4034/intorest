package com.codestates.hobby.global.infra.mail;

public interface EmailService {
	void send(String to, String subject, String text);
}
