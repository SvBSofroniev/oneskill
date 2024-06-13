package com.platform.OneSkill.mongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.platform.OneSkill.persistance.repository.reactive")
public class ReactiveMongoConfig {
}
