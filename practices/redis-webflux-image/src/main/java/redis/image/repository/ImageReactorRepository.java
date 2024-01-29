package redis.image.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import redis.image.common.repository.ImageEntity;
import redis.image.common.repository.UserEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class ImageReactorRepository {
    private final ReactiveHashOperations<String, String, String> hashOperations;

    public ImageReactorRepository(
            ReactiveStringRedisTemplate redisTemplate
    ) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @SneakyThrows
    public Mono<ImageEntity> findById(String id) {
        return Mono.create(sink -> {
            log.info("ImageRepository.findById: {}", id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            hashOperations.multiGet(id, List.of("id", "name", "url"))
                    .subscribe(strings -> {
                        log.info("strings: {}", strings);
                        if (strings.stream().allMatch(item -> item == null)) {
                            sink.error(new RuntimeException("image not found"));
                            return;
                        }
                        var image = new ImageEntity(
                                strings.get(0),
                                strings.get(1),
                                strings.get(2)
                        );

                        sink.success(image);
                    });
        });
    }

    public Mono<ImageEntity> create(String id, String name, String url) {
        var map = Map.of("id", id, "name", name, "url", url);

        return hashOperations.putAll(id, map)
                .then(findById(id));
    }

    public Mono<ImageEntity> findWithContext() {
        return Mono.deferContextual(context -> {
            Optional<UserEntity> userOptional = context.getOrEmpty("user");
            if (userOptional.isEmpty()) throw new RuntimeException("user not found");

            return Mono.just(userOptional.get().getProfileImageId());
        }).flatMap(this::findById);
    }
}
