package io.pivotal.benchmarks.cq.client;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:sdg-cq-context.xml")
public class GemfireBenchmarksCqClientApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(GemfireBenchmarksCqClientApplication.class, args);
		System.out.println("Press <ENTER> to quit");
		System.in.read();
		System.exit(0);
	}
}
