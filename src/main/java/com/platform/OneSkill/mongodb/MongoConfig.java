package com.platform.OneSkill.mongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.platform.OneSkill.persistance.repository.nonreactive")
public class MongoConfig {
}