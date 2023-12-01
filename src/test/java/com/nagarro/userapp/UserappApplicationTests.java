package com.nagarro.userapp;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

class UserappApplicationTests {

	@BeforeEach
	public void before() {
		System.out.println("Before");
	}

	@AfterEach
	public void after() {
		System.out.println("After");
	}

	@BeforeAll
	public static void beforeClass() {
		System.out.println("Before Class");
	}

	@AfterAll
	public static void afterClass() {
		System.out.println("After Class");
	}

	@Test
	void contextLoads() {
	}

}
