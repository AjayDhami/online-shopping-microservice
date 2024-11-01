package com.ajay.microservices.product;

import com.ajay.microservices.product.dto.ProductRequest;
import com.ajay.microservices.product.dto.ProductResponse;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

//@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
		System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
					{
						"name": "iPhone",
						"description": "Latest iPhone",
						"price": 1599.99
					}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iPhone"))
				.body("description", Matchers.equalTo("Latest iPhone"))
				.body("price", Matchers.equalTo(1599.99f));
	}

}
