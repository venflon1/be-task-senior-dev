package com.amaris.task.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TaskIntegrationTest {
	private String baseUri= "http://localhost:";
	
	@LocalServerPort
	private int port;
	@Autowired
	private RestTemplate restTemplate;

	@BeforeEach
	void setupUri() {
		baseUri = baseUri.concat(String.valueOf(port));
	}
	
	@Test
	void test() {	}
}