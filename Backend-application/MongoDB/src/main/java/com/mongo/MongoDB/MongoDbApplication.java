package com.mongo.MongoDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MongoDbApplication {

	/*
	V.V NOTE:
	@EnableMongoAuditing is a Spring Data MongoDB annotation that enables auditing functionality for your MongoDB entities. When you add this annotation to your configuration, it allows Spring to automatically populate certain fields in your documents, such as creation and modification timestamps, without you having to manually set these values.
	What It Does
	- Automatic Timestamp Management:
	- Automatically sets createdDate when an entity is first persisted
	- Automatically updates lastModifiedDate whenever the entity is updated
	Auditor Awareness:
	- Can automatically track who created or modified an entity (if you implement auditor awareness)
	Required Annotations
	For @EnableMongoAuditing to work, you need to use these annotations in your entity classes:
	- @CreatedDate: Marks a field to be populated with the creation timestamp
	- @LastModifiedDate: Marks a field to be populated with the last modification timestamp
	- @CreatedBy: Marks a field to be populated with the creator (requires auditor awareness)
	- @LastModifiedBy: Marks a field to be populated with the last modifier (requires auditor awareness)
	 */

	public static void main(String[] args) {
		SpringApplication.run(MongoDbApplication.class, args);
	}

}
