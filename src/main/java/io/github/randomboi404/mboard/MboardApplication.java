package io.github.randomboi404.mboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MboardApplication.class, args);
	}

}
