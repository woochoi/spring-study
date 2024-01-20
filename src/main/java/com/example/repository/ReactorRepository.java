package com.example.repository;

import com.example.common.entity.ImageEntity;
import com.example.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
//@RequiredArgsConstructor
public class ReactorRepository {
    private static final Map<String, String> tokenUserIdMap =  Map.of("abcd", "1234");
    public Mono<String> getNameByToken(String token) {
        return Mono.justOrEmpty(tokenUserIdMap.get(token));
    }



    private final Map<String, UserEntity> userMap;
    private final Map<String, ImageEntity> imageMap;


    public ReactorRepository() {
        var user = new UserEntity(
                "1234",
                "taewoo",
                32,
                "1",
                "1q2w3e4r!"
        );

        userMap = Map.of("1234", user);


        imageMap = Map.of(
                "1", new ImageEntity("1", "profileImage", "https://dailyone.com/images/1"),
                "2", new ImageEntity("2", "peter's image", "https://dailyone.com/images/2")
        );
    }

    @SneakyThrows
    public Mono<UserEntity> findById1(String userId) {
        return Mono.create(sink -> {
            log.info("UserRepository.findById: {}", userId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            UserEntity user = userMap.get(userId);
            if (user == null) {
                sink.success();
            } else {
                sink.success(user);
            }
        });
    }

    @SneakyThrows
    public Mono<ImageEntity> findById2(String id) {
        return Mono.create(sink -> {
            log.info("ImageRepository.findById: {}", id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            var image = imageMap.get(id);
            if (image == null) {
                sink.error(new RuntimeException("image not found"));
            } else {
                sink.success(image);
            }
        });
    }


}
