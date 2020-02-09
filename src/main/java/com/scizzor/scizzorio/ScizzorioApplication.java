package com.scizzor.scizzorio;

import com.scizzor.scizzorio.enums.Gender;
import com.scizzor.scizzorio.enums.UserRole;
import com.scizzor.scizzorio.model.UserAccount;
import com.scizzor.scizzorio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScizzorioApplication {
	private static final Logger log = LoggerFactory.getLogger(ScizzorioApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ScizzorioApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(UserRepository userRepository) {
		return (args) -> {
			//Save a few users
			userRepository.save(
					new UserAccount(
							"bname",
							"fname",
							"lname",
							"pwd",
							"brand@email.com",
							"07494230896",
							UserRole.BRAND
						)
			);
			
			userRepository.save(
					new UserAccount(
							"fname",
							"lname",
							"pwd",
							"customer@email.com",
							"07494230896",
							UserRole.CUSTOMER,
							Gender.FEMALE
						)
			);
			
			//fetch all users
			log.info("-- All users --");
			for(UserAccount user : userRepository.findAll()) {
				log.info(user.toString());
			}
			log.info("---------------");
			
			//fetch a user by email
			log.info("-- Brand by email -- ");
			log.info(userRepository.findByEmail("brand@email.com").toString());
			log.info("---------------");
		};
	}
}
