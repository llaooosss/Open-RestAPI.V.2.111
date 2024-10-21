package com.example.demo321;


import com.example.demo321.Service.FilmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;



// модульные тесты на мокито
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class Demo321ApplicationTests {

	private FilmService filmService;

	@Test
	void contextLoads() {
		assertNotNull(filmService);
	}

}
