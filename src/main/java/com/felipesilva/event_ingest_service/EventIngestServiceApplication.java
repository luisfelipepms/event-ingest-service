package com.felipesilva.event_ingest_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventIngestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventIngestServiceApplication.class, args);
	}

}
//mvnw.cmd spring-boot:run

//docker exec -it events-postgres psql -U postgres -d events_db