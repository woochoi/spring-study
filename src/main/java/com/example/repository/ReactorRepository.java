package com.example.repository;

import com.example.common.entity.ArticleEntity;
import com.example.common.entity.ImageEntity;
import com.example.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
//@RequiredArgsConstructor
public class ReactorRepository {
    private static final Map<String, String> tokenUserIdMap =  Map.of("abcd", "1234");
    private final Map<String, UserEntity> userMap;
    private final Map<String, ImageEntity> imageMap;
    private static List<ArticleEntity> articleEntities;
    private Map<String, Long> userFollowCountMap;

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

        articleEntities = List.of(
                new ArticleEntity("1", "소식1", "내용1", "1234"),
                new ArticleEntity("2", "소식2", "내용2", "1234"),
                new ArticleEntity("3", "소식3", "내용3", "10000"),
                new ArticleEntity("4", "소식4", "내용4", "1234"),
                new ArticleEntity("5", "소식5", "내용5", "1234"),
                new ArticleEntity("6", "소식6", "내용6", "1234"),
                new ArticleEntity("7", "소식7", "내용7", "1234"),
                new ArticleEntity("8", "소식8", "내용8", "1234"),
                new ArticleEntity("9", "소식9", "내용9", "1234"),
                new ArticleEntity("10", "소식10", "내용10", "1234"),
                new ArticleEntity("11", "소식11", "내용11", "1234")
        );

        userFollowCountMap = Map.of("1234", 1000L);
    }

    public Mono<String> getNameByToken(String token) {
        return Mono.justOrEmpty(tokenUserIdMap.get(token));
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
