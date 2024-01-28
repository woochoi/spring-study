package com.mongo;


import com.mongo.entity.ChatDocument;
import com.mongo.repository.ChatMongoRepository;
import com.mongodb.client.model.changestream.OperationType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonValue;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import java.util.Objects;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class ChatApplication implements CommandLineRunner { // implements CommandLineRunner

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class);
    }

    @Autowired
    private ChatMongoRepository chatMongoRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
       mongoTemplate.changeStream(ChatDocument.class)
                .listen()
                .doOnNext(item -> {
                    //ChatDocument  info = item.getBody();
                    //var info = item.getRaw();
                    Document info = Objects.requireNonNull(item.getRaw()).getFullDocument();
                    OperationType operationType = item.getOperationType();
                    BsonValue token = item.getResumeToken();

                    log.info("info: {}", info);
                    log.info("type: {}", operationType);
                    log.info("token: {}", token);
                })
                .subscribe();

        Thread.sleep(1000);

        var newChat = new ChatDocument("a", "b", "hello");

        chatMongoRepository.save(newChat)
                .doOnNext(saved -> log.info("saved document : {}", saved ))
                .subscribe();

    }
}