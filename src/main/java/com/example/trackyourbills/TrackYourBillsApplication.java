package com.example.trackyourbills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {
//		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
//)
@SpringBootApplication
public class TrackYourBillsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackYourBillsApplication.class, args);
	}

}
