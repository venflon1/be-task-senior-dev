package com.amaris.task.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amaris.task.model.Task;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TaskIntegrationTest {
	private String baseUri= "http://localhost:";
	private static final String CONTEXT_PATH = "/tasks";
	
	@LocalServerPort
	private int port;
	@Autowired
	private RestTemplate restTemplate;

	@BeforeEach
	void setupUri() {
		baseUri = baseUri.concat(String.valueOf(port)).concat(CONTEXT_PATH);
	}
	
	@Test
	@DisplayName(value = "SholdReturnResponseOKWithListOfTasks")
	void getAllTasksTest() {	
		final ResponseEntity<List<Task>> response = this.restTemplate.exchange(
				 baseUri,
				 HttpMethod.GET, 
				 null, 
				 new ParameterizedTypeReference<List<Task>>() {}
		 );
		
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isNotNull();
	}
	
}